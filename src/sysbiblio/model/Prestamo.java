package sysbiblio.model;
import java.time.LocalDate; 


public class Prestamo {
    // Atributos privados: Encapsulación total 
    private Libro libro;
    private Usuario usuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaVencimiento;
    private LocalDate fechaDevolucionReal;
    private boolean activo;

    /**
     * Constructor: Inicializa el préstamo y calcula el vencimiento.
     */
    public Prestamo(Libro libro, Usuario usuario) {
        // Validación de nulidad: Evita NullPointerException
        if (libro == null || usuario == null) {
            throw new NullPointerException("El libro y el usuario son obligatorios para crear un préstamo."); 
        }
        
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = LocalDate.now(); // Fecha actual del sistema
        
        // Regla de negocio: 30 días de préstamo
        this.fechaVencimiento = this.fechaPrestamo.plusDays(30);
        
        this.activo = true; // El préstamo nace activo
        this.fechaDevolucionReal = null;
    }

    //  GETTERS Y SETTERS 
    public Libro getLibro() { return libro; }

    public Usuario getUsuario() { return usuario; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }

    public boolean isActivo() { return activo; }

    public void setActivo(boolean activo) { this.activo = activo; }

    public LocalDate getFechaDevolucionReal() { return fechaDevolucionReal; }

    /**
     * Registra la fecha de devolución y valida que no sea futura.
     */
    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) {
        if (fechaDevolucionReal != null && fechaDevolucionReal.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de devolución no puede ser futura.");
        }
        this.fechaDevolucionReal = fechaDevolucionReal;
    }
}