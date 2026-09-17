`README.md` del Módulo Core

**Ubicación:** `/libreria-core/READMEcore.md`


# Módulo: Librería Core (`libreria-core`)

Este módulo encapsula la lógica de negocio, modelo y persistencia del sistema de gestión de librería.

## Componentes Principales

* **`Libro.java`:** Modelo entidad con los atributos de negocio (`id`, `titulo`, `autor`, `categoria`, `precio`, `existencias`, `anioPublicacion`).
* **`ConexionDB.java`:** Maneja la conexión JDBC con la base de datos MySQL.
* **`LibroDAO.java`:** Implementa las operaciones CRUD mediante `PreparedStatement`:
  * `crear(Libro libro)`
  * `listarTodos()`
  * `actualizar(Libro libro)`
  * `eliminar(int id)`

## Uso como Dependencia Maven

```xml
<dependency>
    <groupId>edu.umg.programacion2</groupId>
    <artifactId>libreria-core</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>