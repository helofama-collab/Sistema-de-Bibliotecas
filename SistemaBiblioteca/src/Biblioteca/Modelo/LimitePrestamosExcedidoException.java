package Biblioteca.Modelo;

public class LimitePrestamosExcedidoException extends RuntimeException {
    public LimitePrestamosExcedidoException(String mensaje) {
        super(mensaje);
    }
}