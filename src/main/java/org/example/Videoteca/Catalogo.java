package org.example.Videoteca;

import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import tools.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;

/**
 * Clase encargada de gestionar las etiquetas del archivo XML.
 *
 * @author Raquel Lahera
 */

@JacksonXmlRootElement(localName = "peliculas")
public class Catalogo {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "pelicula")

    private ArrayList<Pelicula> peliculas = new ArrayList<>();

    // Constructor vacío
    public Catalogo() {
    }

    // Constructor lleno
    public Catalogo(ArrayList<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    // Getter y setter
    public ArrayList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(ArrayList<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }
}
