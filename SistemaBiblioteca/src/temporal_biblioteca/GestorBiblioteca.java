package temporal_biblioteca;

import java.util.ArrayList;
import java.util.List;

public class GestorBiblioteca {
    private List<Libro> inventario = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();

    // Métodos para inicializar datos
    public void agregarLibro(Libro l) { inventario.add(l); }
    public void agregarUsuario(Usuario u) { usuarios.add(u); }

    // --- BÚSQUEDAS (Punto 2) ---
    public Libro buscarLibroPorIsbn(String isbn) {
        for (Libro l : inventario) {
            if (l.getIsbn().equals(isbn)) return l;
        }
        return null;
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        List<Libro> resultados = new ArrayList<>();
        for (Libro l : inventario) {
            if (l.getTitulo().equalsIgnoreCase(titulo)) resultados.add(l);
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

    // IDENTIFICAR QUÉ USUARIO TIENE UN LIBRO (Punto 2)
    public Usuario obtenerPoseedorDeLibro(String isbn) {
        for (Usuario u : usuarios) {
            for (Prestamo p : u.getPrestamosActivos()) {
                if (p.getLibro().getIsbn().equals(isbn)) return u;
            }
        }
        return null;
    }

    // --- MÉTODOS DE ACCIÓN ---
    public void realizarPrestamo(String idUser, String isbn) throws Exception {
        Usuario user = buscarUsuario(idUser);
        Libro libro = buscarLibroPorIsbn(isbn);

        if (user == null || libro == null) throw new IllegalArgumentException("Usuario o Libro no encontrado.");

        if (libro.getEstado() == EstadoLibro.RESERVADO) {
        throw new LibroNoDisponibleException("El libro está RESERVADO y no se puede prestar.");
        }

        user.validarCapacidadPrestamo(); // Lógica de tu compañero (máx 3 libros)
        libro.intentarPrestar();         // Lógica de tu compañero (stock y estado)

        // Punto 3: Usamos la tercera clase
        Prestamo p = new Prestamo(libro, user);
        user.getPrestamosActivos().add(p); 
    }

    public void realizarDevolucion(String idUser, String isbn) {
        Usuario user = buscarUsuario(idUser);
        if (user == null) throw new IllegalArgumentException("Usuario no existe.");

        Prestamo encontrado = null;
        for (Prestamo p : user.getPrestamosActivos()) {
            if (p.getLibro().getIsbn().equals(isbn)) {
                encontrado = p;
                break;
            }
        }

        if (encontrado != null) {
            user.registrarDevolucion(encontrado, false); // false = sin sanción por defecto
            encontrado.getLibro().devolverCopia();
        } else {
            throw new IllegalArgumentException("El usuario no tiene ese libro prestado.");
        }
    }

    public void realizarReserva(String isbn) {
        Libro l = buscarLibroPorIsbn(isbn);
        if (l == null) throw new IllegalArgumentException("Libro no existe.");
        l.setEstado(EstadoLibro.RESERVADO);
    }

    

    public Usuario buscarUsuario(String id) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) return u;
        }
        return null;
    }

    public List<Libro> getInventario() { return inventario; }
    public List<Usuario> getUsuarios() { return usuarios; }
}