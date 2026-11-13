Examen Práctico - Sistema de Biblioteca (POO)

Estudiante: Alfredo Josué Quiroz Aguilar
mero de Cuenta: 202610110396

Descripción del Proyecto

Sistema desarrollado en Java con arquitectura en capas (Modelo, DAO, Servicios) y persistencia en SQLite mediante JDBC, aplicando reglas de negocio estrictas y excepciones personalizadas.

Instrucciones de Ejecución

1. Clonar o descargar el repositorio del proyecto.
2. Abrir el proyecto en NetBeans IDE como un proyecto Maven existente.
3. Asegurarse de tener configurada la versión de Java correspondiente en el pom.xml.
4. Ejecutar la clase principal Main ubicada en el paquete hn.uth.poo.biblioteca.app.

Capturas de Evidencia de Ejecución

1. Operaciones CRUD (Autores y Libros)
Registro de Autores y Libros:
  ![CRUD Exitoso](evidencias/crud.png)

2. Validación de Reglas de Negocio Rechazadas
* **Intento de eliminar un autor con libros asociados (Lanza excepción controlada):**
  ![Regla Rechazada](evidencias/error.png)