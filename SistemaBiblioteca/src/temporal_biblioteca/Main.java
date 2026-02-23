package temporal_biblioteca;

//package sysbiblio.app;



public class Main {
    public static void main(String[] args) {
        GestorBiblioteca controlador = new GestorBiblioteca();
        
        // Creamos algunos datos de prueba
        controlador.agregarLibro(new Libro("111", "El Quijote", "Cervantes", 1605, "Planeta", Genero.NOVELA, 2));
     
        controlador.agregarUsuario(new Usuario("U01", "TuNombre"));

        Console vista = new Console(controlador);
        vista.mostrarMenu();
    }
}
