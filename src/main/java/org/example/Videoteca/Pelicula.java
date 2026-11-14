package org.example.Videoteca;

/**
 * Clase encargada de designar los atributos de la clase Película, junto a sus getter y setter.
 *
 * @author Raquel Lahera
 */

public class Pelicula {
    private int id;
    private String titulo;
    private String director;
    private String genero;
    private int anio;
    private int duracion;
    private String sinopsis;
    private String formato;
    private String estado;
    private String fecha_adquisicion;

    // Generamos un constructor vacío
    public Pelicula() {
    }

    // Generamos un constructor sin el id y sin la fecha de adquisición
    public Pelicula(String titulo, String director, String genero, int anio, int duracion, String sinopsis, String formato, String estado) {
        this.titulo = titulo;
        this.director = director;
        this.genero = genero;
        this.anio = anio;
        this.duracion = duracion;
        this.sinopsis = sinopsis;
        this.formato = formato;
        this.estado = estado;
    }

    // Generamos un constructor lleno
    public Pelicula(int id, String titulo, String director, String genero, int anio, int duracion, String sinopsis, String formato, String estado, String fecha_adquisicion) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.genero = genero;
        this.anio = anio;
        this.duracion = duracion;
        this.sinopsis = sinopsis;
        this.formato = formato;
        this.estado = estado;
        this.fecha_adquisicion = fecha_adquisicion;
    }

    // Generamos getters y setters
    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha_adquisicion() {
        return fecha_adquisicion;
    }

    public void setFecha_adquisicion(String fecha_adquisicion) {
        this.fecha_adquisicion = fecha_adquisicion;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
