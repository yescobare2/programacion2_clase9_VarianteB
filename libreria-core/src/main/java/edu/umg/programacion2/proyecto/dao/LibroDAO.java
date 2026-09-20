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
		String sql = "INSERT INTO libros (titulo, autor, categoria, precio, existencias, anio_publicacion, fechaIngreso) VALUES (?, ?, ?, ?, ?, ?, ?)";
	
		try (Connection conn = ConexionDB.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			
			stmt.setString(1, libro.getTitulo());
			stmt.setString(2, libro.getAutor());
			stmt.setString(3, libro.getCategoria());
			stmt.setDouble(4, libro.getPrecio());
			stmt.setInt(5, libro.getExistencias());
			stmt.setInt(6, libro.getAnioPublicacion());
			stmt.setDate(7, libro.getFechaIngreso());
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
	
	//2. Leer libros
	public List<Libro> listarTodos() throws SQLException{
		String sql = "SELECT id, titulo, autor, categoria, precio, existencias, anio_publicacion, fechaIngreso FROM libros ORDER BY id";
		List<Libro> libros = new ArrayList<>();
		
		try (Connection conn = ConexionDB.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs= stmt.executeQuery()){
			
			while (rs.next()) {
				libros.add(new Libro(
				  rs.getInt("id"),
				  rs.getString("titulo"),
				  rs.getString("autor"),
				  rs.getString("categoria"),
				  rs.getDouble("precio"),
				  rs.getInt("existencias"),
				  rs.getInt("anio_publicacion"),	
				  rs.getDate("fechaIngreso")
			 ));
			}
		}
		return libros;
	}

//3. Actualizar datos de libros
public boolean actualizar(Libro libro) throws SQLException{
	String sql = "UPDATE libros SET titulo = ?, autor = ?, categoria = ?, precio = ?, existencias = ?, anio_publicacion = ?, fechaIngreso = ? WHERE id = ?";
	
	try (Connection conn = ConexionDB.getConnection();
		 PreparedStatement stmt = conn.prepareStatement(sql)){
	
		stmt.setString(1, libro.getTitulo());
		stmt.setString(2, libro.getAutor());
		stmt.setString(3, libro.getCategoria());
		stmt.setDouble(4, libro.getPrecio());
		stmt.setInt(5, libro.getExistencias());
		stmt.setInt(6, libro.getAnioPublicacion());
		stmt.setDate(7, libro.getFechaIngreso());
		stmt.setInt(8, libro.getId());
		return stmt.executeUpdate() > 0;
	  }
	}

//4. Eliminar libro
public boolean eliminar(int id) throws SQLException {
	String sql = "DELETE FROM libros WHERE id = ?";
	
	try (Connection conn = ConexionDB.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql)){
		
		stmt.setInt(1, id);
		return stmt.executeUpdate() > 0;
	}
}
}




