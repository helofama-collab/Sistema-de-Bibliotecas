package sysbiblio.exception;
/**
 * Se lanza cuando un libro no puede ser prestado por su estado actual.
 * Es una Checked Exception: obliga al controlador a gestionarla.
 */
public class LibroNoDisponibleException extends Exception {
    public LibroNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}