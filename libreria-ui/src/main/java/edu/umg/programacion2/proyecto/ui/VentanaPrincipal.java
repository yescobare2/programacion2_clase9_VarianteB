package edu.umg.programacion2.proyecto.ui;

import edu.umg.programacion2.proyecto.dao.LibroDAO;
import edu.umg.programacion2.proyecto.modelo.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Year;
import java.util.List;
import java.sql.Date;

public class VentanaPrincipal extends JFrame {

    private final LibroDAO libroDAO = new LibroDAO();

    private Integer idLibroSeleccionado = null;

    // Componentes del Formulario
    private JTextField txtTitulo, txtAutor, txtCategoria, txtPrecio, txtStock, txtAnio, txtfechaIngreso;
    private JButton btnGuardar, btnActualizar, btnLimpiar, btnEliminar;

    // Componentes de la Tabla
    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;
    

    public VentanaPrincipal() {
        setTitle("Gestión de Librería - Panel Principal");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. PANEL SUPERIOR: Formulario de Registro
        JPanel panelFormulario = new JPanel(new GridLayout(4, 4, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Libro"));

        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtCategoria = new JTextField();
        txtPrecio = new JTextField();
        txtStock = new JTextField();
        txtAnio = new JTextField();
        txtfechaIngreso = new JTextField();
        txtfechaIngreso = new JTextField();
        
        panelFormulario.add(new JLabel("Título:"));
        panelFormulario.add(txtTitulo);
        panelFormulario.add(new JLabel("Autor:"));
        panelFormulario.add(txtAutor);
        panelFormulario.add(new JLabel("Categoría:"));
        panelFormulario.add(txtCategoria);
        panelFormulario.add(new JLabel("Precio ($):"));
        panelFormulario.add(txtPrecio);
        panelFormulario.add(new JLabel("Stock:"));
        panelFormulario.add(txtStock);
        panelFormulario.add(new JLabel("Año Publicación:"));
        panelFormulario.add(txtAnio);
        panelFormulario.add(new JLabel("Fecha de ingreso al catalogo:"));
        panelFormulario.add(txtfechaIngreso);
        panelFormulario.add(new JLabel(""));
        panelFormulario.add(new JLabel(""));
        
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        
        add(panelFormulario, BorderLayout.NORTH);

        // 2. PANEL CENTRAL: Configuración de la Tabla
        String[] columnas = {"ID", "Título", "Autor", "Categoría", "Precio", "Stock", "Año", "Fecha de ingreso"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaLibros = new JTable(modeloTabla);
        add(new JScrollPane(tablaLibros), BorderLayout.CENTER);

        tablaLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarFila();
            }
        });

        // 3. PANEL INFERIOR: Botones de Acción
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar Nuevo");
        btnActualizar = new JButton("Actualizar");
        btnLimpiar = new JButton("Limpiar Campos");
        btnEliminar = new JButton("Eliminar Seleccionado");
        JButton btnResumen = new JButton("Ver Resumen");
        
        
        panelBotones.add(btnResumen);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnEliminar); 
        add(panelBotones, BorderLayout.SOUTH);
        

        // Eventos
        btnGuardar.addActionListener(e -> guardarLibro());
        btnActualizar.addActionListener(e -> actualizarLibro());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnEliminar.addActionListener(e -> eliminarLibro()); 
        btnResumen.addActionListener(e -> mostrarResumenLibros());
        
                
        // Cargar datos
        cargarDatosTabla();
    }

    private void mostrarResumenLibros() {
        try {
            // 1. Obtiene la lista completa de libros desde el DAO
            List<Libro> listaLibros = libroDAO.listarTodos();

            int totalLibros = 0;
            int librosConStock = 0;

            // 2. Recorremos la lista en Java con un foreach y un contador
            for (Libro libro : listaLibros) {
                totalLibros++; // Cuenta el total
                
                // Condición (ejemplo: libros que tienen stock disponible > 0)
                if (libro.getExistencias() > 0) { 
                    librosConStock++; // Cuenta los que cumplen la condición
                }
            }

            // 3. Muestra el resumen en un mensaje emergente
            String mensaje = "=== RESUMEN GENERAL ===\n" +
                             "Total de libros registrados: " + totalLibros + "\n" +
                             "Libros con stock disponible: " + librosConStock;

            JOptionPane.showMessageDialog(this, mensaje, "Resumen de Libros", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al obtener el resumen: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        try {
            List<Libro> libros = libroDAO.listarTodos();
            for (Libro l : libros) {
                Object[] fila = {
                    l.getId(), l.getTitulo(), l.getAutor(),
                    l.getCategoria(), l.getPrecio(),
                    l.getExistencias(), l.getAnioPublicacion(),
                    l.getFechaIngreso()
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void seleccionarFila() {
        int fila = tablaLibros.getSelectedRow();
        if (fila != -1) {
            idLibroSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
            txtTitulo.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtAutor.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtCategoria.setText(modeloTabla.getValueAt(fila, 3).toString());
            txtPrecio.setText(modeloTabla.getValueAt(fila, 4).toString());
            txtStock.setText(modeloTabla.getValueAt(fila, 5).toString());
            txtAnio.setText(modeloTabla.getValueAt(fila, 6).toString());
            txtfechaIngreso.setText(modeloTabla.getValueAt(fila, 7).toString());
        }
    }

    private void guardarLibro() {
        try {
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            String categoria = txtCategoria.getText().trim();
            java.sql.Date fechaActual = new java.sql.Date(System.currentTimeMillis());

            if (titulo.isEmpty() || autor.isEmpty() || categoria.isEmpty() || 
                txtPrecio.getText().trim().isEmpty() || txtStock.getText().trim().isEmpty() || txtAnio.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());
            int anio = Integer.parseInt(txtAnio.getText().trim());

            if (precio <= 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser mayor a 0.", "Validación de Precio", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (stock < 0) {
                JOptionPane.showMessageDialog(this, "El stock no puede ser un número negativo.", "Validación de Stock", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int anioActual = Year.now().getValue();
            if (anio < 1000 || anio > anioActual) {
                JOptionPane.showMessageDialog(this, "El año de publicación debe estar entre 1000 y " + anioActual + ".", "Validación de Año", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String fechaTexto = txtfechaIngreso.getText().trim();
            java.sql.Date fechaIngreso;
            
            if (fechaTexto.isEmpty()) { 
            	fechaIngreso = new java.sql.Date(System.currentTimeMillis());
            } else {
            	fechaIngreso = java.sql.Date.valueOf(fechaTexto);
            }
            
          
            int anioIngreso = fechaIngreso.toLocalDate().getYear();
            if (anioIngreso > anioActual) {
                JOptionPane.showMessageDialog(this, "La fecha de ingreso no puede ser posterior al año actual (" + anioActual + ").", "Validación de Fecha", JOptionPane.WARNING_MESSAGE);
                return;
          }


            List<Libro> librosExistentes = libroDAO.listarTodos();
            for (Libro l : librosExistentes) {
                if (l.getTitulo().equalsIgnoreCase(titulo) && l.getAutor().equalsIgnoreCase(autor)) {
                    JOptionPane.showMessageDialog(this, "Ya existe un libro registrado con el mismo Título y Autor.", "Libro Duplicado", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            
            Libro libro = new Libro(titulo, autor, categoria, precio, stock, anio, fechaIngreso);
            libroDAO.crear(libro);

            JOptionPane.showMessageDialog(this, "¡Libro guardado exitosamente!");
            limpiarFormulario();
            cargarDatosTabla();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio, Stock y Año deben ser valores numéricos válidos.", "Error de Formato", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "El formato de la fecha debe ser AAAA-MM-DD (ejemplo: 2015-12-12).", "Formato de Fecha Inválido", JOptionPane.WARNING_MESSAGE );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar el libro: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarLibro() {
        if (idLibroSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un libro de la tabla para actualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            String categoria = txtCategoria.getText().trim();

            if (titulo.isEmpty() || autor.isEmpty() || categoria.isEmpty() || 
                txtPrecio.getText().trim().isEmpty() || txtStock.getText().trim().isEmpty() || txtAnio.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());
            int anio = Integer.parseInt(txtAnio.getText().trim());

            if (precio <= 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser mayor a 0.", "Validación de Precio", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (stock < 0) {
                JOptionPane.showMessageDialog(this, "El stock no puede ser un número negativo.", "Validación de Stock", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int anioActual = Year.now().getValue();
            if (anio < 1000 || anio > anioActual) {
                JOptionPane.showMessageDialog(this, "El año de publicación debe estar entre 1000 y " + anioActual + ".", "Validación de Año", JOptionPane.WARNING_MESSAGE);
                return;
            }

            java.sql.Date fechaActual = new java.sql.Date(System.currentTimeMillis());
            Libro libro = new Libro(idLibroSeleccionado, titulo, autor, categoria, precio, stock, anio, fechaActual);
            libroDAO.actualizar(libro);
            
            JOptionPane.showMessageDialog(this, "¡Libro actualizado correctamente!");
            limpiarFormulario();
            cargarDatosTabla();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio, Stock y Año deben ser valores numéricos válidos.", "Error de Formato", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el libro: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método Eliminar 
    private void eliminarLibro() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un libro de la tabla para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas eliminar el libro con ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                libroDAO.eliminar(id);
                JOptionPane.showMessageDialog(this, "Libro eliminado correctamente.");
                limpiarFormulario();
                cargarDatosTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarFormulario() {
        idLibroSeleccionado = null;
        txtTitulo.setText("");
        txtAutor.setText("");
        txtCategoria.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        txtAnio.setText("");
        txtfechaIngreso.setText("");
        tablaLibros.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}