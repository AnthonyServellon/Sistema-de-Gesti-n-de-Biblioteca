package biblioteca;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase que representa a un lector de la biblioteca.
 *
 * Cada usuario tiene:
 * - Nombre
 * - ID único
 * - Libros prestados (lista)
 */
public class Usuario implements Serializable {
    private String nombre;
    private String id;
    private ArrayList<Libro> librosPrestados;

    public Usuario(String nombre, String id) {
        this.nombre = nombre;
        this.id = id;
        this.librosPrestados = new ArrayList<>();
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getId() { return id; }
    public ArrayList<Libro> getLibrosPrestados() { return librosPrestados; }

    // Registrar préstamo en el usuario
    public void prestarLibro(Libro libro) {
        librosPrestados.add(libro);
    }

    // Registrar devolución en el usuario
    public void devolverLibro(Libro libro) {
        librosPrestados.remove(libro);
    }

    @Override
    public String toString() {
        return nombre + " (ID: " + id + ") - Libros prestados: " + librosPrestados.size();
    }
}
