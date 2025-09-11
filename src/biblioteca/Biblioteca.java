package biblioteca;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase principal que gestiona todo el sistema:
 *
 * - Registro de libros y usuarios
 * - Préstamos y devoluciones
 * - Listados y filtros
 * - Persistencia en archivo
 */
public class Biblioteca implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Libro> libros;
    private List<Usuario> usuarios;
    private List<Prestamo> prestamos;

    public Biblioteca() {
        this.libros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.prestamos = new ArrayList<>();
    }

    /* ================== LIBROS ================== */
    public void registrarLibro(Libro libro) { libros.add(libro); }
    public List<Libro> listarLibros() { return libros; }
    public List<Libro> filtrarPorGenero(String genero) {
        return libros.stream().filter(l -> l.getGenero().equalsIgnoreCase(genero)).collect(Collectors.toList());
    }
    public List<Libro> filtrarPorAutor(String autor) {
        return libros.stream().filter(l -> l.getAutor().equalsIgnoreCase(autor)).collect(Collectors.toList());
    }
    public List<Libro> listarDisponibles() {
        return libros.stream().filter(Libro::isDisponible).collect(Collectors.toList());
    }

    /* ================== USUARIOS ================== */
    public void registrarUsuario(Usuario usuario) { usuarios.add(usuario); }
    public Usuario buscarUsuarioPorId(String id) {
        return usuarios.stream().filter(u -> u.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    /* ================== PRÉSTAMOS ================== */
    public boolean prestarLibro(String titulo, String idUsuario) {
        Libro libro = libros.stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(titulo) && l.isDisponible())
                .findFirst().orElse(null);
        Usuario usuario = buscarUsuarioPorId(idUsuario);

        if (libro != null && usuario != null) {
            Prestamo p = new Prestamo(libro, usuario);
            prestamos.add(p);
            libro.prestar();
            usuario.prestarLibro(libro);
            System.out.println("✅ Préstamo realizado: " + p);
            return true;
        }
        System.out.println("❌ No se pudo realizar el préstamo (libro no disponible o usuario no existe).");
        return false;
    }

    public boolean devolverLibro(String titulo, String idUsuario) {
        Usuario usuario = buscarUsuarioPorId(idUsuario);
        if (usuario == null) return false;

        Prestamo prestamo = prestamos.stream()
                .filter(p -> p.getLibro().getTitulo().equalsIgnoreCase(titulo)
                        && p.getUsuario().equals(usuario)
                        && !p.isDevuelto())
                .findFirst().orElse(null);

        if (prestamo != null) {
            prestamo.marcarDevuelto();
            prestamo.getLibro().devolver();
            usuario.devolverLibro(prestamo.getLibro());
            System.out.println("📚 Devolución realizada: " + prestamo.getLibro().getTitulo());
            return true;
        }
        System.out.println("⚠️ No se encontró préstamo activo para ese libro/usuario.");
        return false;
    }

    /* ================== LISTADOS ================== */
    public void listarLibros(String filtro, String valor) {
        List<Libro> resultado;
        switch (filtro.toLowerCase()) {
            case "genero":
                resultado = filtrarPorGenero(valor);
                break;
            case "autor":
                resultado = filtrarPorAutor(valor);
                break;
            case "disponibilidad":
                resultado = listarDisponibles();
                break;
            default:
                resultado = listarLibros();
        }
        resultado.forEach(System.out::println);
    }

    /* ================== PERSISTENCIA ================== */
    public void guardarDatos(String rutaArchivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(this);
            System.out.println("💾 Datos guardados en " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("❌ Error al guardar datos: " + e.getMessage());
        }
    }

    public static Biblioteca cargarDatos(String rutaArchivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return (Biblioteca) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("⚠️ No se pudieron cargar datos previos. Se usará una biblioteca vacía.");
            return new Biblioteca();
        }
    }
}