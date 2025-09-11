package biblioteca;

import java.util.Scanner;

/**
 * Clase principal que contiene el menú interactivo en consola.
 * Desde aquí el usuario puede:
 * - Registrar libros
 * - Registrar usuarios
 * - Prestar/devolver libros
 * - Listar libros con filtros
 * - Guardar la información antes de salir
 *
 * Comentarios "super humanos":
 * Este Main es el director de orquesta 🎼. Llama a la Biblioteca (el corazón)
 * y con el menú guía al usuario para que todo fluya. Es un bucle infinito
 * hasta que digas "salir".
 */
public class Main {
    public static void main(String[] args) {
        // Intentar cargar datos previos
        Biblioteca biblioteca = Biblioteca.cargarDatos("biblioteca.dat");
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            // Menú de opciones
            System.out.println("\n Menú - Sistema de Biblioteca");
            System.out.println("1. Registrar libro");
            System.out.println("2. Registrar usuario");
            System.out.println("3. Prestar libro");
            System.out.println("4. Devolver libro");
            System.out.println("5. Listar libros");
            System.out.println("6. Guardar y salir");
            System.out.print("👉 Opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Título: ");
                    String titulo = sc.nextLine();
                    System.out.print("Autor: ");
                    String autor = sc.nextLine();
                    System.out.print("Año: ");
                    int anio = sc.nextInt(); sc.nextLine();
                    System.out.print("Género: ");
                    String genero = sc.nextLine();
                    biblioteca.registrarLibro(new Libro(titulo, autor, anio, genero));
                    System.out.println("✅ Libro registrado con éxito.");
                    break;

                case 2:
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("ID: ");
                    String id = sc.nextLine();
                    biblioteca.registrarUsuario(new Usuario(nombre, id));
                    System.out.println("✅ Usuario registrado con éxito.");
                    break;

                case 3:
                    System.out.print("Título del libro: ");
                    String tituloPrestamo = sc.nextLine();
                    System.out.print("ID del usuario: ");
                    String idPrestamo = sc.nextLine();
                    biblioteca.prestarLibro(tituloPrestamo, idPrestamo);
                    break;

                case 4:
                    System.out.print("Título del libro: ");
                    String tituloDevolucion = sc.nextLine();
                    System.out.print("ID del usuario: ");
                    String idDevolucion = sc.nextLine();
                    biblioteca.devolverLibro(tituloDevolucion, idDevolucion);
                    break;

                case 5:
                    //  Nuevo: bucle de filtros
                    boolean repetir = true;
                    while (repetir) {
                        System.out.print("Filtro (genero/autor/disponibilidad/todo): ");
                        String filtro = sc.nextLine();
                        String valor = "";
                        if (!filtro.equalsIgnoreCase("todo") && !filtro.equalsIgnoreCase("disponibilidad")) {
                            System.out.print("Valor del filtro: ");
                            valor = sc.nextLine();
                        }

                        biblioteca.listarLibros(filtro, valor);

                        // Pausa para leer resultados
                        System.out.print("\n¿Quieres aplicar otro filtro? (s/n): ");
                        String respuesta = sc.nextLine();
                        if (!respuesta.equalsIgnoreCase("s")) {
                            repetir = false; // salir y volver al menú principal
                        }
                    }
                    break;

                case 6:
                    biblioteca.guardarDatos("biblioteca.dat");
                    System.out.println("👋 Saliendo del sistema...");
                    break;

                default:
                    System.out.println("❌ Opción inválida.");
            }
        } while (opcion != 6);
    }
}

