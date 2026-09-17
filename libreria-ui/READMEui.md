`README.md` del Módulo UI

**Ubicación:** `/libreria-ui/READMEui.md`


# Módulo: Librería UI (`libreria-ui`)

Este módulo gestiona la interfaz gráfica de usuario (GUI) desarrollada de manera manual con Java Swing (`extends JFrame`).

## Componentes de la Interfaz

* **`VentanaPrincipal.java`**:
  * **Formulario Superior:** Captura e ingreso de datos.
  * **Tabla Central (`JTable`):** Despliegue interactivo de los libros registrados.
  * **Eventos de Mouse:** Selección de filas para cargar datos directamente al formulario.
  * **Panel de Acciones:** Botones para Guardar, Actualizar, Limpiar y Eliminar.

## Validaciones Integradas

* Detección de duplicados (combinación Título + Autor).
* Restricción para evitar años de publicación posteriores al año actual.
* Control de valores positivos para precio y stock.
* Diálogo modal de confirmación antes de la eliminación de registros.