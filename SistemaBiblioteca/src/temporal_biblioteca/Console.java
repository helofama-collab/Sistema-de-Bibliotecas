package temporal_biblioteca;

import java.util.Scanner;
import java.util.List;

public class Console {
    private GestorBiblioteca controlador;
    private Scanner sc = new Scanner(System.in);

    public Console(GestorBiblioteca controlador) {
        this.controlador = controlador;
    }

    public void mostrarMenu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- GESTIÓN BIBLIOTECA MVC ---");
            System.out.println("1. Prestar | 2. Devolver | 3. Reservar | 4. Búsquedas | 5. Resúmenes | 0. Salir");
            System.out.print("Selecciona: ");
            opcion = sc.nextInt(); sc.nextLine();

            try {
                switch (opcion) {
                    case 1 -> menuPrestar();
                    case 2 -> menuDevolver();
                    case 3 -> menuReservar();
                    case 4 -> menuBusquedas();
                    case 5 -> menuResumenes();
                }
            } catch (Exception e) {
                System.out.println("AVISO: " + e.getMessage());
            }
        }
    }

    private void menuResumenes() {
        System.out.println("\n--- RESUMEN DE LIBROS (Estado Actual) ---");
        for (Libro l : controlador.getInventario()) {
            System.out.println("[" + l.getIsbn() + "] " + l.getTitulo() + " - Estado: " + l.getEstado() + " (Copias: " + l.getCopiasDisponibles() + ")");
        }

        System.out.println("\n--- RESUMEN DE USUARIOS (Libros Prestados) ---");
        for (Usuario u : controlador.getUsuarios()) {
            System.out.print("Usuario: " + u.getNombre() + " (ID: " + u.getId() + ") -> ");
            List<Prestamo> activos = u.getPrestamosActivos();
            if (activos.isEmpty()) {
                System.out.println("Sin préstamos.");
            } else {
                for (Prestamo p : activos) {
                    System.out.print("[" + p.getLibro().getTitulo() + " (Vence: " + p.getFechaVencimiento() + ")] ");
                }
                System.out.println();
            }
        }
    }

    private void menuBusquedas() {
        System.out.println("1. Por Título | 2. Por Género | 3. ¿Quién tiene el libro?");
        int sub = sc.nextInt(); sc.nextLine();
        if (sub == 1) {
            System.out.print("Título: ");
            for(Libro l : controlador.buscarPorTitulo(sc.nextLine())){
                System.out.println("Encontrado: " + l.getTitulo());
                System.out.println("Título: " + l.getTitulo());
                System.out.println("Estado actual: " + l.getEstado()); // Aquí aparecerá RESERVADO correctamente
                System.out.println("Copias físicas en estante: " + l.getCopiasDisponibles()); 
            }
            
        } else if (sub == 3) {
            System.out.print("ISBN: ");
            Usuario u = controlador.obtenerPoseedorDeLibro(sc.nextLine());
            System.out.println(u != null ? "Poseedor actual: " + u.getNombre() : "Nadie tiene este libro.");
            
        }
    }

    private void menuPrestar() throws Exception {
        System.out.print("ID Usuario: "); String id = sc.nextLine();
        System.out.print("ISBN Libro: "); String isbn = sc.nextLine();
        controlador.realizarPrestamo(id, isbn);
        System.out.println(">> Operación completada.");
    }

    private void menuDevolver() {
        System.out.print("ID Usuario: "); String id = sc.nextLine();
        System.out.print("ISBN: "); String isbn = sc.nextLine();
        controlador.realizarDevolucion(id, isbn);
        System.out.println(">> Devolución procesada.");
    }

    private void menuReservar() {
        System.out.print("ISBN: ");
        controlador.realizarReserva(sc.nextLine());
        System.out.println(">> Estado cambiado a RESERVADO.");
    }
}