-- ============================================================
-- Proyecto Individual: Catálogo de Librería (Variante B)
-- ============================================================

-- 1. Crear la base de datos si no existe y seleccionarla
CREATE DATABASE IF NOT EXISTS libreria_db;
USE libreria_db;

-- 2. Eliminar la tabla si ya existía para evitar conflictos
DROP TABLE IF EXISTS libros;

-- 3. Crear la tabla de libros con sus restricciones y tipos de datos
CREATE TABLE libros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    categoria VARCHAR(50),
    precio DECIMAL(10, 2) NOT NULL CHECK (precio > 0),
    existencias INT NOT NULL CHECK (existencias >= 0),
    anio_publicacion INT NOT NULL
);

-- 4. Registros iniciales de prueba (para probar el listado en la UI)
INSERT INTO libros (titulo, autor, categoria, precio, existencias, anio_publicacion) VALUES
('Cien años de soledad', 'Gabriel Garcia Marquez', 'Novela', 145.00, 12, 1967),
('Clean Code', 'Robert C. Martin', 'Tecnico', 220.50, 5, 2008),
('El principito', 'Antoine de Saint-Exupéry', 'Infantil', 85.00, 0, 1943);