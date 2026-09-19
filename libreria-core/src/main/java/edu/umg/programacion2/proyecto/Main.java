package edu.umg.programacion2.proyecto;

import edu.umg.programacion2.proyecto.dao.LibroDAO;
import edu.umg.programacion2.proyecto.modelo.Libro;

import java.sql.SQLException;
import java.util.List;
import java.sql.Date;

public class Main {

	public static void main(String[] args) {
		LibroDAO dao = new LibroDAO();
		
	try {
		System.out.println("====PRUEBA DEL CRUD EN CONSOLA====");
		
		//1. Create: crar un libro de prueba
		System.out.println("1. Creando libro de prueba");		
        Libro libroNuevo = new Libro ("Libro de prueba", "Sin autor", "Ninguno", 145.00, 7, 2000, Date.valueOf("2015-07-12") );
        Libro libroCreado = dao.crear(libroNuevo);
        int idGenerado = libroCreado.getId();
        System.out.println("Libro creado exitosamente con ID: " + idGenerado + "\n");
        
        //2. Read: listar los libros 
        System.out.println("2. Listando todos los libros existentes actuales: ");
        mostrarLibros(dao.listarTodos());
        
        //3. Update: actualizar datos del libro creado
        System.out.println("\n3. Actualizando el libro creado (ID: " + idGenerado + ")...");
        libroCreado.setTitulo("Libro de Prueba (ACTUALIZADO)");
        libroCreado.setPrecio(199.99);
        libroCreado.setExistencias(12);
        boolean actualizado = dao.actualizar(libroCreado);
        System.out.println("¿Se actualizó correctamente?: " + actualizado + "\n");

        // Mostrar la lista para verificar la actualización
        System.out.println("Lista tras la actualización:");
        mostrarLibros(dao.listarTodos());

        // 4. Delete: Eliminar el libro de prueba 
        System.out.println("\n4. Eliminando el libro creado (ID: " + idGenerado + ")...");
        boolean eliminado = dao.eliminar(idGenerado);
        System.out.println("¿Se eliminó correctamente?: " + eliminado + "\n");

        // Listado final para verificar la eliminación
        System.out.println("Lista final tras la eliminación:");
        mostrarLibros(dao.listarTodos());

        System.out.println("\n=== PRUEBA FINALIZADA CON ÉXITO ===");

    } catch (SQLException e) {
        System.err.println("Error durante la prueba del CRUD: " + e.getMessage());
        e.printStackTrace();
    }
}

// Método auxiliar para imprimir el listado
private static void mostrarLibros(List<Libro> libros) {
    if (libros.isEmpty()) {
        System.out.println("   (No hay libros registrados)");
        return;
    }
    for (Libro l : libros) {
        System.out.println("   ID: " + l.getId() + " | Título: " + l.getTitulo() + " | Autor: " + l.getAutor() + " | Precio: $" + l.getPrecio() + " | Stock: " + l.getExistencias());
    }
}
}