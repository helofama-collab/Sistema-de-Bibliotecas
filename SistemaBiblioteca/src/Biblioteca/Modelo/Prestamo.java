package Biblioteca.Modelo;
import java.time.LocalDate; 


public class Prestamo {
    
    private Libro libro;
    private Usuario usuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaVencimiento;
    private LocalDate fechaDevolucionReal;
    private boolean activo;

   
    public Prestamo(Libro libro, Usuario usuario) {
       
        if (libro == null || usuario == null) {
            throw new NullPointerException("El libro y el usuario son obligatorios para crear un préstamo."); 
        }
        
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = LocalDate.now(); 
        
    
        this.fechaVencimiento = this.fechaPrestamo.plusDays(30);
        
        this.activo = true; 
        this.fechaDevolucionReal = null;
    }

   
    public Libro getLibro() { return libro; }

    public Usuario getUsuario() { return usuario; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }

    public boolean isActivo() { return activo; }

    public void setActivo(boolean activo) { this.activo = activo; }

    public LocalDate getFechaDevolucionReal() { return fechaDevolucionReal; }

    
    
    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) {
        if (fechaDevolucionReal != null && fechaDevolucionReal.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de devolución no puede ser futura.");
        }
        this.fechaDevolucionReal = fechaDevolucionReal;
    }
}