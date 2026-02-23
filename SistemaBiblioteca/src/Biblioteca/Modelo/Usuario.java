package Biblioteca.Modelo;
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

    

   
    public void validarCapacidadPrestamo() {
        
        if (this.prestamosActivos.size() >= 3) {
            throw new LimitePrestamosExcedidoException("Límite excedido: El usuario ya tiene 3 libros.");
        }
        
     
        if (estaSancionado()) {
            throw new IllegalArgumentException("Usuario bloqueado por retraso hasta: " + fechaFinSancion);
        }
    }

   
    private boolean estaSancionado() {
        return fechaFinSancion != null && fechaFinSancion.isAfter(LocalDate.now());
    }

    public void registrarDevolucion(Prestamo prestamo, boolean aplicarSancion) {
      
        if (prestamosActivos.contains(prestamo)) {
            prestamosActivos.remove(prestamo);
            historialPrestamos.add(prestamo);
            
            if (aplicarSancion) {
               
                this.fechaFinSancion = LocalDate.now().plusDays(7);
            }
        }
    }

    
    public List<Prestamo> getPrestamosActivos() { return this.prestamosActivos; }
    public String getId() { return id; }
    public String getNombre() { return nombre; }
}