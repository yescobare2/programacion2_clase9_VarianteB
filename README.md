# Sistema de Gestión de Librería (Multimódulo Java \& Swing)

Este proyecto es una aplicación Java modularizada construida con **Apache Maven**, que implementa una arquitectura en capas para gestionar el catálogo de una librería mediante una interfaz gráfica desarrollada en **Java Swing** y persistencia en **MySQL**.

==================================================================================
Estructura del Proyecto:

libreria-core: Contiene la lógica de negocio, el modelo (Libro), la conexión JDBC (ConexionBD) y las operaciones CRUD (LibroDAO).

libreria-ui: Contiene la interfaz gráfica (VentanaPrincipal). Depende de libreria-core para gestionar y mostrar los datos.

==================================================================================
Requisitos y Configuración:

Requisitos: JDK 17+, Apache Maven 3.8+, MySQL 8.0+ y Eclipse IDE.

Base de Datos: Crear la base de datos libreria\_db ejecutando el script schema.sql en MySQL Workbench.

Credenciales: Verificar que el usuario y la contraseña en ConexionBD.java coincidan con el servidor MySQL local..

==================================================================================
Ejecucion:
Importar el proyecto en Eclipse como Existing Maven Projects..
Ejecutar un Maven Update..
Ejecutar la clase VentanaPrincipal.java desde el módulo libreria-ui (Run As -> Java Application).



==================================================================================
Funcionalidades Principales:
Tabla interactiva (JTable) para visualizar los registros..
Formulario para agregar y editar libros con validación de campos..
Prevención de registros duplicados (Título + Autor)..
Confirmación modal para la eliminación de registros.
==================================================================================.

