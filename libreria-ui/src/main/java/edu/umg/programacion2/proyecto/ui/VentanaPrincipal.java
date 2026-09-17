package edu.umg.programacion2.proyecto.ui;

import edu.umg.programacion2.proyecto.dao.LibroDAO;
import edu.umg.programacion2.proyecto.modelo.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    private final LibroDAO libroDAO = new LibroDAO();

    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;

    public VentanaPrincipal() {
        setTitle("Gestión de Librería - Panel Principal");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Tabla de datos
        String[] columnas = {"ID", "Título", "Autor", "Categoría", "Precio", "Stock", "Año"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaLibros = new JTable(modeloTabla);
        add(new JScrollPane(tablaLibros), BorderLayout.CENTER);

        // Cargar datos
        cargarDatosTabla();
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        try {
            List<Libro> libros = libroDAO.listarTodos();
            for (Libro l : libros) {
                Object[] fila = {
                    l.getId(), l.getTitulo(), l.getAutor(),
                    l.getCategoria(), l.getPrecio(),
                    l.getExistencias(), l.getAnioPublicacion()
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}