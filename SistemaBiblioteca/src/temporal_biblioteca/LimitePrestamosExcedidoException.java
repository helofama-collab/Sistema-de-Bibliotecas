package temporal_biblioteca;
/**
 * Se lanza cuando un usuario intenta superar el límite de 3 libros.
 */
public class LimitePrestamosExcedidoException extends RuntimeException {
    public LimitePrestamosExcedidoException(String mensaje) {
        super(mensaje);
    }
}