Reparto de Tareas:

Modelo: Yeison Bedoy 
Controlador, Vista y Main: Ahmed El Founti Amakhtari



Como ejecutar el programa:

## 'C:\JAVA\jdk-25_windows-x64_bin\jdk-25.0.1\bin\java.exe' '--enable-preview' '-XX:+ShowCodeDetailsInExceptionMessages' '-cp' 'E:\javagit\SistemaBiblioteca\bin' 'Biblioteca.Main' 





Descripción breve:

1. Capa de Modelo (Lógica de Negocio y Datos)
Contiene la estructura de la información y las reglas que rigen el sistema.

Libro: Gestiona el inventario físico y la transición de estados de las copias.
Usuario: Valida requisitos de préstamo (límites de cantidad y sanciones).
Prestamo: Registra la vinculación temporal entre libros y usuarios, gestionando vencimientos.
Genero / EstadoLibro: Tipos enumerados que garantizan la integridad de datos.
Excepciones Custom: Controlan flujos de error específicos de la lógica de negocio.

2. Capa de Controlador (Mediador)
GestorBiblioteca: Orquesta la interacción entre el Modelo y la Vista. Procesa las solicitudes de la interfaz, realiza búsquedas en las colecciones y ejecuta las operaciones lógicas complejas.

3. Capa de Vista (Interfaz)
Console: Clase responsable exclusivamente de la interacción con el usuario. Captura entradas mediante teclado y presenta los datos procesados, sin conocer las reglas internas de negocio.

4. Punto de Entrada
Main: Inicializa el sistema, realiza la inyección de dependencias manual y arranca el ciclo de ejecución de la aplicación.


Conclusión:
La implementación destaca por una alta cohesión y un bajo acoplamiento. Cada clase posee una responsabilidad única (Single Responsibility Principle), facilitando la escalabilidad del sistema. El uso de excepciones personalizadas y tipos enumerados garantiza una robustez técnica alineada con los estándares de desarrollo de software profesional en Java. 