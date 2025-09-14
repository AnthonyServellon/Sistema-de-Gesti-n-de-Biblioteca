package biblioteca;

import java.io.Serializable;

/**
 * Clase que representa un libro dentro de la biblioteca.
 *
 * Cada libro tiene:
 * - Título
 * - Autor
 * - Año de publicación
 * - Género
 * - Estado de disponibilidad (prestado o no)
 *
 * Se implementa Serializable para poder guardar objetos en archivos.
 */
public class Libro implements Serializable {
    private String titulo;
    private String autor;
    private int anio;
    private String genero;
    private boolean disponible; // true = disponible, false = prestado

    // Constructor: por defecto el libro se crea como disponible
    public Libro(String titulo, String autor, int anio, String genero) {
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.genero = genero;
        this.disponible = true;
    }

    // Getters (encapsulación: acceso seguro a atributos privados)
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAnio() { return anio; }
    public String getGenero() { return genero; }
    public boolean isDisponible() { return disponible; }

    // Cambiar estado del libro
    public void prestar() { this.disponible = false; }
    public void devolver() { this.disponible = true; }

    // Representación bonita para imprimir en consola
    @Override
    public String toString() {
        return titulo + " - " + autor + " (" + anio + ") [" + genero + "] " +
                (disponible ? "Disponible ✅" : "Prestado ❌");
    }
}
