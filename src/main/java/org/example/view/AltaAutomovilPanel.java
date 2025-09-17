package org.example.view;

// Importamos las librerías necesarias para crear interfaces gráficas
import javax.swing.*;   // Para JFrame, JPanel, JTextField, JButton, JLabel, etc.
import java.awt.*;      // Para los layouts, dimensiones y alineaciones

// Clase que representa la ventana para dar de alta un automóvil
public class AltaAutomovilPanel extends JFrame {

    // Campos de texto públicos para poder acceder desde otras clases (controlador)
    public JTextField txtMatricula, txtMarca, txtModelo, txtColor, txtPrecio;

    // Botones públicos para poder añadirles acciones desde el controlador
    public JButton btnGuardar, btnCancelar;

    // Constructor de la clase, aquí se construye toda la ventana
    public AltaAutomovilPanel() {
        setTitle("Alta de Automóvil");         // Título de la ventana
        setSize(450, 320);                     // Tamaño de la ventana (ancho x alto)
        setLocationRelativeTo(null);           // Centrar la ventana en la pantalla
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cerrar solo esta ventana

        // --- Panel principal que contiene todos los componentes ---
        JPanel panel = new JPanel(new GridBagLayout()); // Usamos GridBagLayout para alinear todo en filas y columnas
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Margen interior de 15px alrededor
        GridBagConstraints gbc = new GridBagConstraints(); // Objeto que controla la posición de cada componente
        gbc.insets = new Insets(5, 5, 5, 5); // Espacio (margen) entre cada componente
        gbc.fill = GridBagConstraints.HORIZONTAL; // Los campos de texto se expanden horizontalmente

        // --- Fila 1: Matrícula ---
        gbc.gridx = 0; gbc.gridy = 0;     // Columna 0, fila 0
        gbc.weightx = 0.3;                // La etiqueta ocupa un 30% del espacio horizontal
        panel.add(new JLabel("Matrícula:"), gbc); // Añadimos la etiqueta

        gbc.gridx = 1; gbc.weightx = 0.7; // La caja de texto ocupa un 70% del espacio
        txtMatricula = new JTextField();  // Creamos el campo de texto
        panel.add(txtMatricula, gbc);     // Lo añadimos al panel

        // --- Fila 2: Marca ---
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        panel.add(new JLabel("Marca:"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        txtMarca = new JTextField();
        panel.add(txtMarca, gbc);

        // --- Fila 3: Modelo ---
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        panel.add(new JLabel("Modelo:"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        txtModelo = new JTextField();
        panel.add(txtModelo, gbc);

        // --- Fila 4: Color ---
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.3;
        panel.add(new JLabel("Color:"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        txtColor = new JTextField();
        panel.add(txtColor, gbc);

        // --- Fila 5: Precio ---
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.3;
        panel.add(new JLabel("Precio:"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        txtPrecio = new JTextField();
        panel.add(txtPrecio, gbc);

        // --- Fila 6: Botones centrados ---
        gbc.gridx = 0; gbc.gridy = 5;        // Columna 0, fila 5
        gbc.gridwidth = 2;                   // Ocupa las 2 columnas (etiqueta + campo)
        gbc.anchor = GridBagConstraints.CENTER; // Centrar los botones horizontalmente
        gbc.fill = GridBagConstraints.NONE;     // No expandir los botones

        // Panel interno para los botones, con espacio entre ellos
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnGuardar = new JButton("Guardar"); // Botón guardar
        btnCancelar = new JButton("Cancelar"); // Botón cancelar
        buttons.add(btnGuardar);             // Añadimos botón guardar
        buttons.add(btnCancelar);            // Añadimos botón cancelar
        panel.add(buttons, gbc);             // Añadimos el panel de botones al panel principal

        // Añadimos el panel principal a la ventana
        add(panel);
    }
}

