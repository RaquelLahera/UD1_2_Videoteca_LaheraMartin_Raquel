package org.example.Videoteca;

import tools.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de gestionar las operaciones de la base de datos y ficheros XML de la videoteca.
 *
 * @author Raquel Lahera
 */

public class GestorVideoteca {

    // Instanciamos el mapeador
    XmlMapper xmlMapper = new XmlMapper();

    /**
     * Crea la tabla "peliculas" en la base de datos PostgreSQL si no existe previamente.
     */
    public void crearTabla() {
        // Intentamos la conexión
        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement()) {

            // Código SQL para la creación de la tabla y sus columnas
            String creacion = "CREATE TABLE IF NOT EXISTS peliculas (" +
                    "id SERIAL PRIMARY KEY, " +
                    "titulo VARCHAR(200) NOT NULL, " +
                    "director VARCHAR(100), " +
                    "genero VARCHAR(50), " +
                    "anio INTEGER, " +
                    "duracion INTEGER, " +
                    "sinopsis TEXT, " +
                    "formato VARCHAR(50), " +
                    "estado VARCHAR(30), " +
                    "fecha_adquisicion DATE" +
                    ");";

            // Creamos la tabla
            st.executeUpdate(creacion);
            System.out.println("Tabla 'peliculas' creada con éxito.");

        } catch (SQLException e) {
            System.out.println("Error al crear la tabla: " + e.getMessage());
        }
    }

    /**
     * Inserta los datos de ejemplo en la base de datos PostgreSQL, en la tabla 'películas'.
     */
    public void insertarDatosEjemplo() {

        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement()) {

            // Código SQL para la consulta de datos
            String consulta = "SELECT * FROM peliculas WHERE titulo = 'El Padrino'";

            ResultSet rs = conn.createStatement().executeQuery(consulta);

            if (rs.next() == false) {
                // Código SQL para la inserción los datos
                String insertarEjemplos = "INSERT INTO peliculas (id, titulo, director, genero, anio, duracion, formato, estado, fecha_adquisicion) VALUES" +
                        "(DEFAULT, 'El Padrino', 'Francis Ford Coppola', 'Drama', 1972, 175, 'Blu-ray', 'Edición coleccionista', DEFAULT)," +
                        "(DEFAULT, 'La lista de Schindler', 'Steven Spielberg', 'Drama histórico', 1993, 195, 'DVD', 'Nuevo', DEFAULT)," +
                        "(DEFAULT, 'Pulp Fiction', 'Quentin Tarantino', 'Crimen', 1994, 154, 'Blu-ray', 'Usado', DEFAULT)," +
                        "(DEFAULT, 'El caballero oscuro', 'Christopher Nolan', 'Acción', 2008, 152, '4K', 'Nuevo', DEFAULT)," +
                        "(DEFAULT, 'Origen', 'Christopher Nolan', 'Ciencia ficción', 2010, 148, 'Blu-ray', 'Nuevo', DEFAULT);";

                // Insertamos los datos
                st.executeUpdate(insertarEjemplos);
                System.out.println("Se han insertado los datos correctamente. Películas insertadas: 5");

            } else {
                System.out.println("No hay más ejemplos que insertar.");
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar los datos: " + e.getMessage());
        }
    }

    /**
     * Consulta los datos de la tabla 'películas', en la base de datos PostgreSQL.
     */
    public void consultarDatos() {
        try (Connection conn = ConexionBD.getConnection()) {

            // Código SQL para la consulta de datos
            String consulta = "SELECT * FROM peliculas";

            // Ejecutamos la consulta
            ResultSet rs = conn.createStatement().executeQuery(consulta);

            // Comprobamos si hay películas que consultar
            if (rs.next() == false) {
                System.out.println("No hay películas en la base de datos de la Videoteca.");

            } else {
                System.out.println("ID | TÍTULO | DIRECTOR | GÉNERO | AÑO | DURACIÓN | SINOPSIS | FORMATO | ESTADO | FECHA ADQUISICIÓN");
                System.out.println("-".repeat(110));

                do {
                    System.out.println(rs.getInt("id") + " - " +
                            rs.getString("titulo") + " - " +
                            rs.getString("director") + " - " +
                            rs.getString("genero") + " - " +
                            rs.getInt("anio") + " - " +
                            rs.getInt("duracion") + " - " +
                            rs.getString("sinopsis") + " - " +
                            rs.getString("formato") + " - " +
                            rs.getString("estado") + " - " +
                            rs.getString("fecha_adquisicion"));
                } while (rs.next());

                System.out.println("Consulta realizada con éxito.");
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }

    /**
     * Importa los datos de un archivo XML a la base de datos PostgreSQL, a la tabla 'películas'.
     */
    public void importarXML() {
        try {
            // Declaramos la ruta
            String path = "./src/main/resources/peliculas.xml";

            // Leemos el archivo y lo convertimos en una lista de objetos
            ArrayList<Pelicula> peliculas = xmlMapper.readValue(new File(path),
                    xmlMapper.getTypeFactory().constructCollectionType(List.class, Pelicula.class));

            // Declaramos la condición para filtrar por título y formato
            String consulta = "SELECT * FROM peliculas WHERE titulo = ? AND formato = ?;";

            // Definimos la plantilla para la inserción en la base de datos
            String insertar = "INSERT INTO peliculas (titulo, director, genero, anio, duracion, sinopsis, formato, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

            // Creamos un contador
            int contador = 0;

            // Abrimos conexiones. Hay dos ps porque debemos comprobar si existe antes de insertar
            try (Connection conn = ConexionBD.getConnection();
                 PreparedStatement ps = conn.prepareStatement(insertar);
                 PreparedStatement psConsulta = conn.prepareStatement(consulta)) {


                for (Pelicula peli : peliculas) {

                    // Cogemos los datos necesarios para comprobar si existe la película
                    psConsulta.setString(1, peli.getTitulo());
                    psConsulta.setString(2, peli.getFormato());

                    // Y realizamos la consulta
                    ResultSet rs = psConsulta.executeQuery();

                    // Si no hay datos, rellenamos
                    if (rs.next() == false) {
                        ps.setString(1, peli.getTitulo());
                        ps.setString(2, peli.getDirector());
                        ps.setString(3, peli.getGenero());
                        ps.setInt(4, peli.getAnio());
                        ps.setInt(5, peli.getDuracion());
                        ps.setString(6, peli.getSinopsis());
                        ps.setString(7, peli.getFormato());
                        ps.setString(8, peli.getEstado());

                        // Ejecutamos
                        ps.executeUpdate();

                        // Añadimos iteraciones al contador para saber las vueltas totales
                        contador++;
                    }
                }
                System.out.println("Total películas importadas: " + contador);

            } catch (SQLException e) {
                System.out.println("Error al importar los datos: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error al importar el archivo XML: " + e.getMessage());
        }
    }

    /**
     * Exporta los datos de la base de datos PostgreSQL, de la tabla 'películas', a un archivo XML deseado.
     *
     * @param nombreArchivo Nombre del archivo XML donde se exportarán los datos.
     */
    public void exportarXML(String nombreArchivo) {
        try {
            // Declaramos la ruta y creamos el objeto
            String path = "./src/main/resources/" + nombreArchivo;
            File archivo = new File(path);

            // Instanciamos el ArrayList
            ArrayList<Pelicula> peliculas = new ArrayList<>();

            // Declaramos una condición para saber si el archivo existe y tiene longitud suficiente
            if (archivo.exists() && archivo.length() > 0) {
                // Leemos el archivo y lo convertimos en una lista de objetos
                peliculas = xmlMapper.readValue(new File(path),
                        xmlMapper.getTypeFactory().constructCollectionType(ArrayList.class, Pelicula.class));
            }

            // Definimos la plantilla para la base de datos
            String consulta = "SELECT titulo, director, genero, anio, duracion, sinopsis, formato, estado FROM peliculas;";

            // Creamos un contador
            int contador = 0;

            // Abrimos conexiones
            try (Connection conn = ConexionBD.getConnection();
                 PreparedStatement ps = conn.prepareStatement(consulta);
                 ResultSet rs = ps.executeQuery()) {

                // Rellenamos los valores
                while (rs.next()) {
                    String tituloGV = rs.getString("titulo");
                    String directorGV = rs.getString("director");
                    String generoGV = rs.getString("genero");
                    int anioGV = rs.getInt("anio");
                    int duracionGV = rs.getInt("duracion");
                    String sinopsisGV = rs.getString("sinopsis");
                    String formatoGV = rs.getString("formato");
                    String estadoGV = rs.getString("estado");

                    // Comprobamos si la película existe en el XML
                    boolean existeEnXML = false;
                    int i = 0;
                    while (i < peliculas.size() && !existeEnXML) {
                        Pelicula peli = peliculas.get(i);
                        if (peli.getTitulo().equalsIgnoreCase(tituloGV) && peli.getFormato().equalsIgnoreCase(formatoGV)) {
                            existeEnXML = true;
                        }
                        i++;
                    }

                    // Si la película no existe, la instanciamos y le añadimos los parámetros
                    if (!existeEnXML) {
                        Pelicula pelicula = new Pelicula(tituloGV, directorGV, generoGV, anioGV, duracionGV, sinopsisGV, formatoGV, estadoGV);
                        peliculas.addLast(pelicula);

                        // Añadimos iteraciones al contador
                        contador++;
                    }
                }

                // Si se ha añadido alguna película nueva, generamos el objeto catálogo para no pisar el XML con la lista 'peliculas'
                if (contador > 0) {
                    Catalogo catalogo = new Catalogo(peliculas);

                    // Una vez terminado el bucle, escribimos el archivo con la ruta y la lista
                    xmlMapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), catalogo);

                    System.out.println("Se han exportado las películas correctamente a " + nombreArchivo + ".\nTotal exportadas: " + contador);

                } else {
                    System.out.println("No hay más películas para exportar.");
                }

            } catch (SQLException e) {
                System.out.println("Error al exportar los datos: " + e.getMessage());
            }
        } catch (
                Exception e) {
            System.out.println("Error al exportar el archivo XML: " + e.getMessage());
        }
    }
}