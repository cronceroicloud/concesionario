package org.example.controller;

// Importamos las clases necesarias de nuestro proyecto
import org.example.dao.AutomovilDAO;           // DAO para manejar operaciones con la base de datos
import org.example.model.Automovil;            // Clase modelo de Automovil
import org.example.view.AltaAutomovilPanel;    // Panel de alta de automóviles
import org.example.view.ConsultaAutomovilPanel;// Panel de consulta de automóviles
import org.example.view.MainMenu;              // Menú principal de la app

// Librerías Swing y utilidades
import javax.swing.*;                          // Para ventanas, botones, cuadros de diálogo, etc.
import javax.swing.table.DefaultTableModel;    // Para controlar los datos de la tabla
import java.awt.event.WindowAdapter;           // Para eventos de ventanas
import java.awt.event.WindowEvent;
import java.util.List;                          // Para manejar listas de objetos Automovil

// Clase controlador que gestiona la interacción entre la vista y el modelo
public class AutomovilController {

    // --- Atributos ---
    private final AutomovilDAO dao;             // Objeto DAO para acceder a la base de datos
    private final MainMenu menu;                // Ventana principal
    private AltaAutomovilPanel altaPanel;       // Ventana de alta
    private ConsultaAutomovilPanel consultaPanel; // Ventana de consulta

    // --- Constructor ---
    public AutomovilController(MainMenu menu) {
        this.menu = menu;                       // Guardamos la referencia del menú principal
        this.dao = new AutomovilDAO();          // Creamos el DAO para manejar los automóviles
        initController();                       // Inicializamos los eventos de los botones
    }

    // --- Inicializar los listeners de los botones del menú principal ---
    private void initController() {
        menu.btnAlta.addActionListener(e -> openAlta());          // Abrir ventana de alta
        menu.btnConsulta.addActionListener(e -> openConsulta());  // Abrir ventana de consulta
        menu.btnSalir.addActionListener(e -> System.exit(0));    // Salir de la aplicación
    }

    // --- Abrir ventana de alta de automóvil ---
    private void openAlta() {
        altaPanel = new AltaAutomovilPanel();   // Creamos la ventana de alta
        altaPanel.setVisible(true);             // La mostramos

        // --- Guardar un nuevo automóvil ---
        altaPanel.btnGuardar.addActionListener(ev -> {
            try {
                // Leemos los datos ingresados por el usuario
                String mat = altaPanel.txtMatricula.getText().trim();
                if (mat.isEmpty()) {
                    JOptionPane.showMessageDialog(altaPanel, "La matrícula es obligatoria");
                    return;
                }
                String marca = altaPanel.txtMarca.getText().trim();
                String modelo = altaPanel.txtModelo.getText().trim();
                String color = altaPanel.txtColor.getText().trim();
                double precio = Double.parseDouble(altaPanel.txtPrecio.getText().trim());

                // Creamos un objeto Automovil y lo guardamos en la base de datos
                Automovil a = new Automovil(mat, marca, modelo, color, precio);
                dao.insertar(a);

                // Mensaje de éxito y cerramos la ventana
                JOptionPane.showMessageDialog(altaPanel, "Automóvil guardado");
                altaPanel.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(altaPanel, "Precio inválido"); // Si el precio no es un número
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(altaPanel, "Error: " + ex.getMessage());
                ex.printStackTrace(); // Para depuración
            }
        });

        // --- Cancelar alta ---
        altaPanel.btnCancelar.addActionListener(ev -> altaPanel.dispose());

        // Listener para eventos de ventana (no hace nada en este caso)
        altaPanel.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) { /* nada */ }
        });
    }

    // --- Abrir ventana de consulta, edición y baja ---
    private void openConsulta() {
        consultaPanel = new ConsultaAutomovilPanel(); // Creamos la ventana de consulta
        cargarTabla();                                // Cargamos los datos de la base de datos
        consultaPanel.setVisible(true);               // Mostramos la ventana

        // --- Botón Volver ---
        consultaPanel.btnVolver.addActionListener(e -> consultaPanel.dispose());

        // --- Botón Eliminar ---
        consultaPanel.btnEliminar.addActionListener(e -> {
            int row = consultaPanel.tablaAutos.getSelectedRow(); // Fila seleccionada
            if (row == -1) { JOptionPane.showMessageDialog(consultaPanel, "Selecciona un registro"); return; }

            String matricula = (String) consultaPanel.tablaAutos.getValueAt(row, 0); // Obtener matrícula
            int r = JOptionPane.showConfirmDialog(consultaPanel, "¿Eliminar " + matricula + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) {
                dao.eliminar(matricula); // Eliminar del DAO
                cargarTabla();            // Recargar la tabla
            }
        });

        // --- Botón Editar ---
        consultaPanel.btnEditar.addActionListener(e -> {
            int row = consultaPanel.tablaAutos.getSelectedRow(); // Fila seleccionada
            if (row == -1) { JOptionPane.showMessageDialog(consultaPanel, "Selecciona un registro"); return; }

            String matricula = (String) consultaPanel.tablaAutos.getValueAt(row, 0); // Obtener matrícula
            Automovil a = dao.buscarPorMatricula(matricula); // Buscar el automóvil en la base de datos
            if (a == null) { JOptionPane.showMessageDialog(consultaPanel, "Registro no encontrado"); return; }

            // --- Crear panel para edición de campos ---
            JTextField tfMarca = new JTextField(a.getMarca());
            JTextField tfModelo = new JTextField(a.getModelo());
            JTextField tfColor = new JTextField(a.getColor());
            JTextField tfPrecio = new JTextField(String.valueOf(a.getPrecio()));

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel("Marca:")); panel.add(tfMarca);
            panel.add(new JLabel("Modelo:")); panel.add(tfModelo);
            panel.add(new JLabel("Color:")); panel.add(tfColor);
            panel.add(new JLabel("Precio:")); panel.add(tfPrecio);

            // Mostrar cuadro de diálogo de edición
            int res = JOptionPane.showConfirmDialog(consultaPanel, panel, "Editar " + matricula, JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                try {
                    // Guardar cambios en el objeto Automovil
                    a.setMarca(tfMarca.getText().trim());
                    a.setModelo(tfModelo.getText().trim());
                    a.setColor(tfColor.getText().trim());
                    a.setPrecio(Double.parseDouble(tfPrecio.getText().trim()));
                    dao.actualizar(a);   // Actualizar en la base de datos
                    cargarTabla();       // Recargar la tabla
                    JOptionPane.showMessageDialog(consultaPanel, "Actualizado");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(consultaPanel, "Precio inválido");
                }
            }
        });
    }

    // --- Método para cargar los datos de los automóviles en la tabla ---
    private void cargarTabla() {
        List<Automovil> lista = dao.listar(); // Obtener lista del DAO

        // Creamos un modelo de tabla donde las celdas no se pueden editar
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Matrícula","Marca","Modelo","Color","Precio"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        // Agregar cada automóvil a la tabla
        for (Automovil a : lista) {
            model.addRow(new Object[]{a.getMatricula(), a.getMarca(), a.getModelo(), a.getColor(), a.getPrecio()});
        }

        consultaPanel.tablaAutos.setModel(model); // Aplicar el modelo a la tabla
    }
}
