package Biblioteca;

import Biblioteca.Vista.Console;
import Biblioteca.Controlador.GestorBiblioteca;
import Biblioteca.Modelo.Genero;
import Biblioteca.Modelo.Libro;
import Biblioteca.Modelo.Usuario;





public class Main {
    public static void main(String[] args) {
        GestorBiblioteca controlador = new GestorBiblioteca();
        
      
        controlador.agregarLibro(new Libro("111", "El Quijote", "Cervantes", 1605, "Planeta", Genero.NOVELA, 2));
     
        controlador.agregarUsuario(new Usuario("U01", "TuNombre"));

        Console vista = new Console(controlador);
        vista.mostrarMenu();
    }
}
