package Biblioteca.Controlador;

import java.util.ArrayList;
import java.util.List;

import Biblioteca.Modelo.Genero;
import Biblioteca.Modelo.Libro;
import Biblioteca.Modelo.Prestamo;
import Biblioteca.Modelo.Usuario;

public class GestorBiblioteca {
    private List<Libro> inventario = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();

    
    public void agregarLibro(Libro l) { 
        inventario.add(l); 
    }
    
    public void agregarUsuario(Usuario u) { 
        usuarios.add(u); 
    }

    
    public Libro buscarLibroPorIsbn(String isbn) {
        for (Libro l : inventario) {
            if (l.getIsbn().equals(isbn)) return l;
        }
        return null;
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        List<Libro> resultados = new ArrayList<>();
        for (Libro l : inventario) {
            
            if (l.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                resultados.add(l);
            }
        }
        return resultados;
    }

    public List<Libro> buscarPorGenero(Genero genero) {
        List<Libro> resultados = new ArrayList<>();
        for (Libro l : inventario) {
            if (l.getGenero() == genero) resultados.add(l);
        }
        return resultados;
    }

   
    public List<Usuario> buscarUsuarios(String nombre) {
        List<Usuario> resultados = new ArrayList<>();
        for (Usuario u : usuarios) {
           
            if (u.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultados.add(u);
            }
        }
        return resultados;
    }

    public Usuario buscarUsuario(String id) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) return u;
        }
        return null;
    }

  
    public Usuario obtenerPoseedorDeLibro(String isbn) {
        for (Usuario u : usuarios) {
            for (Prestamo p : u.getPrestamosActivos()) {
                if (p.getLibro().getIsbn().equals(isbn)) {
                    return u;
                }
            }
        }
        return null;
    }

    
    public void realizarPrestamo(String idUsuario, String isbn) {
        Usuario usuario = buscarUsuario(idUsuario);
        Libro libro = buscarLibroPorIsbn(isbn);

        if (usuario == null) throw new IllegalArgumentException("Usuario no encontrado.");
        if (libro == null) throw new IllegalArgumentException("Libro no encontrado.");

        
        if (!libro.tieneCopiasLibres()) {
            throw new IllegalStateException("No hay copias disponibles para este libro.");
        }

        usuario.validarCapacidadPrestamo();
        libro.prestar();

        Prestamo prestamo = new Prestamo(libro, usuario);
        usuario.getPrestamosActivos().add(prestamo);
        
        System.out.println("Préstamo realizado: " + libro.getTitulo());
    }

    public void realizarDevolucion(String idUsuario, String isbn) {
        Usuario usuario = buscarUsuario(idUsuario);
        if (usuario == null) throw new IllegalArgumentException("Usuario no encontrado.");

        Prestamo prestamoEncontrado = null;
        for (Prestamo p : usuario.getPrestamosActivos()) {
            if (p.getLibro().getIsbn().equals(isbn)) {
                prestamoEncontrado = p;
                break;
            }
        }

        if (prestamoEncontrado == null) {
            throw new IllegalArgumentException("El usuario no tiene este libro.");
        }

        
        prestamoEncontrado.getLibro().devolver();
        
     
        usuario.registrarDevolucion(prestamoEncontrado, false);

        System.out.println("Devolución procesada: " + prestamoEncontrado.getLibro().getTitulo());
    }

    
    public void realizarReserva(String isbn) {
        Libro libro = buscarLibroPorIsbn(isbn);
        if (libro == null) throw new IllegalArgumentException("Libro no encontrado.");

       
        if (!libro.tieneCopiasLibres()) {
            throw new IllegalStateException("No hay copias para reservar.");
        }

        libro.reservar();
        System.out.println("Reserva realizada.");
    }

    
    public void cancelarReserva(String isbn) {
        Libro libro = buscarLibroPorIsbn(isbn);
        if (libro == null) throw new IllegalArgumentException("Libro no encontrado.");

       
        libro.cancelarReserva();
        System.out.println("Reserva cancelada.");
    }

  
    public List<Libro> getInventario() { 
        return inventario; 
    }
    
    public List<Usuario> getUsuarios() { 
        return usuarios; 
    }
}