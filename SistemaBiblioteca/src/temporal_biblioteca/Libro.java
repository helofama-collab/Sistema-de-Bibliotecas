package temporal_biblioteca;

/**
 * Entidad principal que representa un Libro en el sistema.
 * Responsabilidad: Definir la estructura y validar la integridad de los datos.
 */
public class Libro {
    // Atributos privados para cumplir con la encapsulación 
    private String isbn;
    private String titulo;
    private String autor;
    private int anioPublicacion;
    private String editorial;
    private Genero genero;
    private int copiasTotales;
    private int copiasDisponibles;
    private EstadoLibro estado;

    // Constructor completo para inicializar el objeto 
    public Libro(String isbn, String titulo, String autor, int anioPublicacion, 
                 String editorial, Genero genero, int copiasTotales) {
        setIsbn(isbn);
        setTitulo(titulo);
        setAutor(autor);
        setAnioPublicacion(anioPublicacion);
        setEditorial(editorial);
        setGenero(genero);
        setCopiasTotales(copiasTotales);
        // Al crear un libro nuevo, las copias disponibles coinciden con las totales
        this.copiasDisponibles = copiasTotales;
        actualizarEstado();
    }

    //  GETTERS Y SETTERS CON VALIDACIONES 

    public String getIsbn() { return isbn; }

    public void setIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN es obligatorio y no puede estar vacío.");
        }
        this.isbn = isbn;
    }

    public String getTitulo() { 
        return titulo; 
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        this.titulo = titulo;
    }

    public int getAnioPublicacion() { 
        return anioPublicacion; 
    }

    public void setAnioPublicacion(int anioPublicacion) {
        
        // Validamos que el año no sea negativo por lógica
        if (anioPublicacion < 0) {
            throw new IllegalArgumentException("El año de publicación no puede ser negativo.");
        }
        this.anioPublicacion = anioPublicacion;
    }

    public int getCopiasTotales() { 
        return copiasTotales; 
    }

    public void setCopiasTotales(int copiasTotales) {
        if (copiasTotales < 0) {
            throw new IllegalArgumentException("La cantidad total de copias no puede ser negativa.");
        }
        this.copiasTotales = copiasTotales;
    }

    public int getCopiasDisponibles() { 
        return copiasDisponibles; 
    }

    public void setCopiasDisponibles(int copiasDisponibles) {
        if (copiasDisponibles < 0 || copiasDisponibles > this.copiasTotales) {
            throw new IllegalArgumentException("Las copias disponibles no pueden ser negativas ni superar al total.");
        }
        this.copiasDisponibles = copiasDisponibles;
        actualizarEstado();
    }

    public EstadoLibro getEstado() { 
        return estado; 
    }

    public void setEstado(EstadoLibro nuevoEstado) {
        // La siguiente información no aparece explícitamente en el documento proporcionado.
        // Justificación: Es necesario validar que el nuevo valor no sea nulo antes de operar.
        if (nuevoEstado == null) {
            throw new NullPointerException("El nuevo estado no puede ser nulo.");
        }

        // VALIDACIÓN DE LÓGICA DE NEGOCIO
        if (this.estado == EstadoLibro.RESERVADO && nuevoEstado == EstadoLibro.DISPONIBLE) {
            // Regla: Un libro reservado no puede pasar a disponible sin gestión previa.
            throw new IllegalStateException("Un libro reservado no puede quedar libre sin cancelar la reserva.");
        }

        // ASIGNACIÓN: El valor del parámetro pasa al atributo
        this.estado = nuevoEstado; 
    }

    // Métodos internos de soporte

    public void intentarPrestar() throws LibroNoDisponibleException {
        if (this.estado != EstadoLibro.DISPONIBLE) {
            // Evita prestar libros ya prestados o reservados
            throw new LibroNoDisponibleException("Operación no permitida. El libro está: " + this.estado);
        }
        
        if (this.copiasDisponibles <= 0) {
            throw new LibroNoDisponibleException("No quedan copias físicas disponibles.");
        }
        
        this.copiasDisponibles--;
        actualizarEstado();
    }

    public void devolverCopia() {
        // Evitar cambios de estado incorrectos
        if (this.copiasDisponibles >= this.copiasTotales) {
            throw new IllegalArgumentException("No se pueden devolver más copias de las que existen.");
        }
        this.copiasDisponibles++;
        if (this.estado != EstadoLibro.RESERVADO) {
        actualizarEstado();
        }
    }

    private void actualizarEstado() {
        if (this.estado == EstadoLibro.RESERVADO) {
        return; 
        }
        if (this.copiasDisponibles > 0) {
            this.estado = EstadoLibro.DISPONIBLE;
        } else {
            this.estado = EstadoLibro.PRESTADO;
        }
    }

    // Getters y Setters restantes (Autor, Editorial, Genero) siguen la misma lógica de validación de nulos/vacíos.
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }
    public Genero getGenero() { return genero; }
    public void setGenero(Genero genero) { this.genero = genero; }
}