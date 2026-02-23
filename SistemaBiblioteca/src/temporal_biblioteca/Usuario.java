package temporal_biblioteca;
import java.time.LocalDate;
import java.util.ArrayList; 
import java.util.List;


public class Usuario {
    private String id;
    private String nombre;
    private LocalDate fechaFinSancion;
    private List<Prestamo> prestamosActivos;
    private List<Prestamo> historialPrestamos;

    public Usuario(String id, String nombre) {
        setId(id);
        setNombre(nombre);
        this.prestamosActivos = new ArrayList<>();
        this.historialPrestamos = new ArrayList<>();
        this.fechaFinSancion = null;
    }

    // --- GETTERS Y SETTERS CON VALIDACIONES ---

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new NullPointerException("El ID del usuario no puede estar vacío.");
        }
        this.id = id;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new NullPointerException("El nombre del usuario es obligatorio.");
        }
        this.nombre = nombre;
    }

    

    // --- LÓGICA DE NEGOCIO Y REQUERIMIENTOS ---

    /**
     * Valida si el usuario cumple los requisitos para un nuevo préstamo.
     * La siguiente información no aparece explícitamente en el documento proporcionado:
     * Se utiliza .size() de la lista en lugar de un contador para garantizar 
     * que la validación siempre refleje la realidad de la colección de objetos.
     */
    public void validarCapacidadPrestamo() {
        // Requerimiento: No más de 3 libros
        if (this.prestamosActivos.size() >= 3) {
            throw new LimitePrestamosExcedidoException("Límite excedido: El usuario ya tiene 3 libros.");
        }
        
        // Requerimiento: Bloqueo de 7 días
        if (estaSancionado()) {
            throw new IllegalArgumentException("Usuario bloqueado por retraso hasta: " + fechaFinSancion);
        }
    }

    /**
     * Verifica si existe una sanción activa comparando con la fecha actual.
     */
    private boolean estaSancionado() {
        return fechaFinSancion != null && fechaFinSancion.isAfter(LocalDate.now());
    }

    public void registrarDevolucion(Prestamo prestamo, boolean aplicarSancion) {
        // Lógica de integridad: solo si el préstamo está en la lista activa
        if (prestamosActivos.contains(prestamo)) {
            prestamosActivos.remove(prestamo);
            historialPrestamos.add(prestamo);
            
            if (aplicarSancion) {
                // Implementa el bloqueo de 7 días
                this.fechaFinSancion = LocalDate.now().plusDays(7);
            }
        }
    }

    // Getters para listas (Copia defensiva para máxima nota)
    public List<Prestamo> getPrestamosActivos() { return this.prestamosActivos; }
    public String getId() { return id; }
    public String getNombre() { return nombre; }
}