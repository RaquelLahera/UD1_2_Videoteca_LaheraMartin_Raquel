package org.example.Videoteca;

import java.util.Scanner;

/**
 * Clase Main encargada de gestionar el menú y sus métodos.
 *
 * @author Raquel Lahera
 */

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GestorVideoteca gv = new GestorVideoteca();

        // Creamos tabla
        gv.crearTabla();

        int opcion = -1;

        while (opcion != 5) {
            // Creamos el menú
            System.out.println("-".repeat(55));
            System.out.println("== Bienvenido/a a la Videoteca ==");
            System.out.println("-".repeat(55));
            System.out.println(
                    "Menú \n 1. Consultar películas. \n 2. Insertar datos de ejemplo. \n 3. Importar películas de XML a la Videoteca. \n 4. Exportar películas de la Videoteca a XML. \n 5. Salir.");
            System.out.println("-".repeat(55));
            System.out.print("Introduce la opción deseada: ");
            opcion = sc.nextInt();

            if (opcion == 1) { // primera opción: consultar
                gv.consultarDatos();

            } else if (opcion == 2) { // Segunda opción: insertar datos de ejemplo
                gv.insertarDatosEjemplo();

            } else if (opcion == 3) { // Tercera opción: importar
                gv.importarXML();

            } else if (opcion == 4) { // Tercera opción: exportar
                System.out.print("Introduce el nombre del archivo (sin extensión) donde desea exportar la videoteca: ");
                String nombreArchivo = sc.next().toLowerCase().trim() + ".xml";
                gv.exportarXML(nombreArchivo);

            } else if (opcion == 5) {
                System.out.println("Gracias por usar la Videoteca.");

            } else {
                System.out.println("Opción equivocada. Vuelve a introducir el número deseado.");
            }
        }
        sc.close(); // Cerramos
    }
}
