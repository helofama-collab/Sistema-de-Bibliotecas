package Biblioteca.Modelo;


import java.util.Arrays;


public class Libro {
    private String isbn;
    private String titulo;
    private String autor;
    private int anioPublicacion;
    private String editorial;
    private Genero genero;
    private int copiasTotales;
    private int copiasDisponibles;
    private EstadoLibro[] estadosCopias; 

    public Libro(String isbn, String titulo, String autor, int anioPublicacion, 
                 String editorial, Genero genero, int copiasTotales) {
        setIsbn(isbn);
        setTitulo(titulo);
        setAutor(autor);
        setAnioPublicacion(anioPublicacion);
        setEditorial(editorial);
        setGenero(genero);
        setCopiasTotales(copiasTotales);
        
        this.copiasDisponibles = copiasTotales;
        
        
        this.estadosCopias = new EstadoLibro[copiasTotales];
        
        Arrays.fill(this.estadosCopias, EstadoLibro.DISPONIBLE);
    }

 

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) throw new IllegalArgumentException("ISBN obligatorio.");
        this.isbn = isbn;
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) throw new IllegalArgumentException("Título obligatorio.");
        this.titulo = titulo;
    }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public int getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(int anioPublicacion) { this.anioPublicacion = anioPublicacion; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public Genero getGenero() { return genero; }
    public void setGenero(Genero genero) { this.genero = genero; }

    public int getCopiasTotales() { return copiasTotales; }
    public void setCopiasTotales(int copiasTotales) {
        if (copiasTotales < 0) throw new IllegalArgumentException("Copias no pueden ser negativas.");
        this.copiasTotales = copiasTotales;
    }

    public int getCopiasDisponibles() { return copiasDisponibles; }

    public EstadoLibro[] getEstadosCopias() {
        return estadosCopias;
    }

  

    public boolean tieneCopiasLibres() {
        return copiasDisponibles > 0;
    }

 
    public void prestar() {
        for (int i = 0; i < estadosCopias.length; i++) {
            if (estadosCopias[i] == EstadoLibro.DISPONIBLE) {
                estadosCopias[i] = EstadoLibro.PRESTADO;
                copiasDisponibles--;
                return;
            }
        }
        throw new IllegalStateException("No hay copias disponibles para prestar.");
    }

    
    public void devolver() {
        for (int i = 0; i < estadosCopias.length; i++) {
            if (estadosCopias[i] == EstadoLibro.PRESTADO) {
                estadosCopias[i] = EstadoLibro.DISPONIBLE;
                copiasDisponibles++;
                return;
            }
        }
        throw new IllegalStateException("No hay copias prestadas para devolver.");
    }

   
    public void reservar() {
        for (int i = 0; i < estadosCopias.length; i++) {
            if (estadosCopias[i] == EstadoLibro.DISPONIBLE) {
                estadosCopias[i] = EstadoLibro.RESERVADO;
                copiasDisponibles--; 
                return;
            }
        }
        throw new IllegalStateException("No hay copias disponibles para reservar.");
    }

   
    public void cancelarReserva() {
        for (int i = 0; i < estadosCopias.length; i++) {
            if (estadosCopias[i] == EstadoLibro.RESERVADO) {
                estadosCopias[i] = EstadoLibro.DISPONIBLE;
                copiasDisponibles++;
                return;
            }
        }
        throw new IllegalStateException("No hay ninguna reserva para este libro.");
    }

    @Override
    public String toString() {
        return String.format(
            "Libro[ISBN=%s, título='%s', disponibles=%d/%d, estados=%s]",
            isbn, titulo, copiasDisponibles, copiasTotales, Arrays.toString(estadosCopias)
        );
    }
}