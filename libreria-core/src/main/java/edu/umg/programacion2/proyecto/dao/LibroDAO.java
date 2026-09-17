package edu.umg.programacion2.proyecto.dao;

import edu.umg.programacion2.proyecto.db.ConexionDB;
import edu.umg.programacion2.proyecto.modelo.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibroDAO {

	//1. Crear un nuevo libro en la DB
	public Libro crear (Libro libro) throws SQLException{
		String sql = "INSERT INTO libros (titulo, autor, categoria, precio, existencia, anio_publicacion) VALUES (?, ?, ?, ?, ?, ?)";
	
		try (Connection conn = ConexionDB.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			
			stmt.setString(1, libro.getTitulo());
			stmt.setString(2, libro.getAutor());
			stmt.setString(3, libro.getCategoria());
			stmt.setDouble(5, libro.getPrecio());
			stmt.setInt(5, libro.getExistencias());
			stmt.setInt(6, libro.getAnioPublicacion());
			
			stmt.executeUpdate();
			
		 try (ResultSet keys = stmt.getGeneratedKeys()){
			 if (keys.next()) {
				 libro.setId(keys.getInt(1));
				 return libro;
			 }
			 throw new SQLException("Error al obtener el ID generado");
			 
		 }
			
		}
	}
}

