package com.stackmasters.adoptaanimales.view.impl;

import com.stackmasters.adoptaanimales.dto.ActualizarMascotaDTO;
import com.stackmasters.adoptaanimales.dto.CrearMascotaDTO;
import com.stackmasters.adoptaanimales.model.Mascota;
import com.stackmasters.adoptaanimales.model.Mascota.Especie;
import com.stackmasters.adoptaanimales.model.Mascota.EstadoMascota;
import com.stackmasters.adoptaanimales.model.Mascota.Sexo;
import com.stackmasters.adoptaanimales.model.Mascota.Tamaño;
import com.stackmasters.adoptaanimales.repository.MascotaRepository;
import com.stackmasters.adoptaanimales.service.MascotaService;
import com.stackmasters.adoptaanimales.service.impl.MascotaServiceImpl;
import com.stackmasters.adoptaanimales.utils.LoadingHandler;
import com.stackmasters.adoptaanimales.utils.Message;
import com.stackmasters.adoptaanimales.view.MascotasFormView;
import com.stackmasters.adoptaanimales.view.impl.complement.Message.MessageType;
import com.stackmasters.adoptaanimales.view.impl.swing.SquaredButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class MascotasFormViewImpl extends javax.swing.JPanel implements MascotasFormView {

    private MascotaService mascotaService;
    private Runnable onCancelar;
    private Runnable onAccionPrincipal;
    private boolean modoEdicion = false;
    private Integer idMascota = null;

    // --- Componentes lógicos ---
    private JTextField txtNombre;
    private JComboBox<Especie> cmbEspecie;
    private JTextField txtRaza;
    private JComboBox<Sexo> cmbSexo;
    private JComboBox<Tamaño> cmbTamano;
    private JTextField txtPeso;
    private JTextField txtFechaNacimiento;
    private JTextArea txtDescripcion;
    private JCheckBox chkVacunado;
    private JCheckBox chkCastrado;
    private JComboBox<EstadoMascota> cmbEstado;
    private JLabel lblEstado;
    private SquaredButton btnAccionPrincipal;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public MascotasFormViewImpl() {
        initComponents(); 
        
        // 1. CONFIGURACIÓN DEL LAYOUT PRINCIPAL (FONDO)
        this.removeAll(); 
        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(245, 247, 250)); // Fondo gris muy suave

        mascotaService = new MascotaServiceImpl(new MascotaRepository());

        // 2. CONFIGURACIÓN DE LA TARJETA CENTRAL
        jPanel2.setBackground(Color.WHITE);
        // Sombra suave o borde
        jPanel2.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1), 
                new EmptyBorder(30, 50, 40, 50) // Padding generoso
        ));
        
        // Ancho fijo (900px), Alto dinámico
        jPanel2.setPreferredSize(new Dimension(900, 750)); 

        // 3. CONSTRUCCIÓN DEL FORMULARIO
        setupFormPanel();

        // 4. AGREGAR AL CENTRO
        this.add(jPanel2, new GridBagConstraints()); 

        // Listeners
        btnCancelar.addActionListener(e -> {
            if (onCancelar != null) onCancelar.run();
        });
        
        btnAccionPrincipal.addActionListener(e -> {
             if (onAccionPrincipal != null) onAccionPrincipal.run();
        });
        
        setModoEdicion(false);
    }

    private void setupFormPanel() {
        jPanel2.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Fuentes
        Font fontTitle = new Font("SansSerif", Font.BOLD, 26);
        Font fontBtnBack = new Font("SansSerif", Font.BOLD, 14);

        // --- FILA 0: CABECERA (Botón Volver + Título) ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 25, 0); // Separación con el formulario
        
        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setOpaque(false);
        
        // CONFIGURACIÓN DEL BOTÓN VOLVER (MÁS GRANDE)
        btnCancelar.setBackground(new Color(235, 235, 235));
        btnCancelar.setForeground(new Color(80, 80, 80));
        btnCancelar.setText("< Volver");
        btnCancelar.setFont(fontBtnBack);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Tamaño fijo más grande
        btnCancelar.setPreferredSize(new Dimension(120, 40)); 
        
        jLabel1.setFont(fontTitle);
        jLabel1.setForeground(new Color(33, 33, 33));
        
        GridBagConstraints gbcH = new GridBagConstraints();
        gbcH.gridx = 0; gbcH.insets = new Insets(0,0,0,30); // Separación entre botón y título
        headerPanel.add(btnCancelar, gbcH);
        
        gbcH.gridx = 1; gbcH.weightx = 1.0; 
        headerPanel.add(jLabel1, gbcH);
        
        jPanel2.add(headerPanel, gbc);
        
        // --- INICIALIZACIÓN COMPONENTES ---
        txtNombre = crearTextField();
        cmbEspecie = new JComboBox<>(new DefaultComboBoxModel<>(Especie.values())); estilarCombo(cmbEspecie);
        txtRaza = crearTextField();
        cmbSexo = new JComboBox<>(new DefaultComboBoxModel<>(Sexo.values())); estilarCombo(cmbSexo);
        cmbTamano = new JComboBox<>(new DefaultComboBoxModel<>(Tamaño.values())); estilarCombo(cmbTamano);
        txtPeso = crearTextField();
        txtFechaNacimiento = crearTextField(); txtFechaNacimiento.setToolTipText("yyyy-MM-dd");
        
        txtDescripcion = new JTextArea(4, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtDescripcion.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200)), new EmptyBorder(5, 5, 5, 5)));
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        
        chkVacunado = new JCheckBox("Sí, cuenta con vacunas");
        chkVacunado.setOpaque(false); chkVacunado.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        chkCastrado = new JCheckBox("Sí, está castrado/esterilizado");
        chkCastrado.setOpaque(false); chkCastrado.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        cmbEstado = new JComboBox<>(new DefaultComboBoxModel<>(EstadoMascota.values())); estilarCombo(cmbEstado);
        lblEstado = new JLabel("Estado actual");

        btnAccionPrincipal = new SquaredButton();
        btnAccionPrincipal.setBackground(new Color(33, 150, 243)); 
        btnAccionPrincipal.setForeground(Color.WHITE);
        btnAccionPrincipal.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnAccionPrincipal.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // --- CONSTRUCCIÓN DEL FORMULARIO (STACKED) ---
        // gbc reset
        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        int row = 1;

        // Fila 1: Nombre y Especie
        row = addStackedDualField(jPanel2, row, "Nombre", txtNombre, "Especie", cmbEspecie);
        
        // Fila 2: Raza y Sexo
        row = addStackedDualField(jPanel2, row, "Raza", txtRaza, "Sexo", cmbSexo);
        
        // Fila 3: Tamaño y Peso
        row = addStackedDualField(jPanel2, row, "Tamaño", cmbTamano, "Peso (kg)", txtPeso);
        
        // Fila 4: Fecha Nacimiento (Sola, mitad de ancho o completo según prefieras)
        // Lo pondremos solo en la primera columna pero ocupando mitad de espacio para no estirarlo feo
        addStackedLabel(jPanel2, 0, row, "Fecha Nacimiento (yyyy-MM-dd)");
        gbc.gridx = 0; gbc.gridy = row + 1; gbc.weightx = 0.5; gbc.insets = new Insets(3, 0, 15, 20); // Margen derecho
        jPanel2.add(txtFechaNacimiento, gbc);
        row += 2;

        // Fila 5: Descripción
        addStackedLabel(jPanel2, 0, row, "Descripción / Historia");
        gbc.gridx = 0; gbc.gridy = row + 1; gbc.gridwidth = 2; // Ocupa todo el ancho
        gbc.weightx = 1.0; 
        gbc.ipady = 50; // Más alto
        gbc.insets = new Insets(3, 0, 15, 0);
        jPanel2.add(scrollDescripcion, gbc);
        
        // Reset description constraints
        gbc.gridwidth = 1; gbc.ipady = 0;
        row += 2;

        // Fila 6: Checkboxes
        addStackedLabel(jPanel2, 0, row, "Información Médica");
        gbc.gridx = 0; gbc.gridy = row + 1; gbc.insets = new Insets(3, 0, 15, 0);
        jPanel2.add(chkVacunado, gbc);
        
        gbc.gridx = 1; gbc.gridy = row + 1;
        jPanel2.add(chkCastrado, gbc);
        row += 2;
        
        // Fila 7: Estado (Condicional)
        addStackedLabel(jPanel2, 0, row, "Estado", lblEstado); // Pasamos el label objeto para poder ocultarlo luego
        gbc.gridx = 0; gbc.gridy = row + 1; gbc.insets = new Insets(3, 0, 15, 20);
        jPanel2.add(cmbEstado, gbc);
        row += 2;
        
        // Botón Principal
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 0, 0); 
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.CENTER; // Centrado
        btnAccionPrincipal.setPreferredSize(new Dimension(300, 50)); // Botón grande
        jPanel2.add(btnAccionPrincipal, gbc);
    }

    // --- HELPER PARA ETIQUETAS ENCIMA DE CAMPOS (STACKED) ---
    // Retorna la siguiente fila libre (row + 2)
    private int addStackedDualField(JPanel p, int startRow, String l1, JComponent c1, String l2, JComponent c2) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0.5; // Reparto equitativo 50% - 50%
        
        // --- COLUMNA IZQUIERDA ---
        // 1. Etiqueta (Fila N)
        gbc.gridx = 0; gbc.gridy = startRow;
        gbc.insets = new Insets(0, 0, 0, 20); // Sin margen abajo (pegado al input), margen der 20
        JLabel label1 = new JLabel(l1);
        label1.setFont(new Font("SansSerif", Font.BOLD, 13));
        label1.setForeground(new Color(70, 70, 70));
        p.add(label1, gbc);
        
        // 2. Input (Fila N+1)
        gbc.gridy = startRow + 1;
        gbc.insets = new Insets(3, 0, 15, 20); // Margen arriba 3 (pegado label), Margen abajo 15 (separar siguiente grupo)
        p.add(c1, gbc);

        // --- COLUMNA DERECHA ---
        // 1. Etiqueta (Fila N)
        gbc.gridx = 1; gbc.gridy = startRow;
        gbc.insets = new Insets(0, 0, 0, 0); // Sin margen derecho
        JLabel label2 = new JLabel(l2);
        label2.setFont(new Font("SansSerif", Font.BOLD, 13));
        label2.setForeground(new Color(70, 70, 70));
        p.add(label2, gbc);
        
        // 2. Input (Fila N+1)
        gbc.gridy = startRow + 1;
        gbc.insets = new Insets(3, 0, 15, 0); 
        p.add(c2, gbc);
        
        return startRow + 2;
    }
    
    // Helper para etiqueta sola (para casos como fecha o descripción)
    private void addStackedLabel(JPanel p, int x, int y, String text) {
        addStackedLabel(p, x, y, text, null);
    }
    
    private void addStackedLabel(JPanel p, int x, int y, String text, JLabel labelRef) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x; gbc.gridy = y; 
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 0);
        
        JLabel l = (labelRef != null) ? labelRef : new JLabel(text);
        l.setText(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        l.setForeground(new Color(70, 70, 70));
        p.add(l, gbc);
    }
    
    private JTextField crearTextField() {
        JTextField t = new JTextField();
        t.setFont(new Font("SansSerif", Font.PLAIN, 14));
        t.setPreferredSize(new Dimension(0, 35)); // Altura fija cómoda
        t.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200)), 
                new EmptyBorder(0, 8, 0, 8))); 
        return t;
    }
    
    private void estilarCombo(JComboBox box) {
        box.setBackground(Color.WHITE);
        box.setFont(new Font("SansSerif", Font.PLAIN, 14));
        box.setPreferredSize(new Dimension(0, 35));
    }
    
    @Override
    public Integer getIdMascota() {
        return idMascota;
    }

    @Override
    public void mostrarMensaje(String mensaje, boolean error) {
        if (error) Message.ShowMessage(this, MessageType.ERROR, mensaje);
        else Message.ShowMessage(this, MessageType.SUCCESS, mensaje);
    }

    @Override
    public void onCancelar(Runnable accion) { this.onCancelar = accion; }

    @Override
    public void onAccionPrincipal(Runnable accion) { this.onAccionPrincipal = accion; }

    @Override
    public CrearMascotaDTO getCrearMascotaDTO(Integer idAlbergue) {
        try {
            String nombre = txtNombre.getText().trim();
            Especie especie = (Especie) cmbEspecie.getSelectedItem();
            String raza = txtRaza.getText().trim();
            Sexo sexo = (Sexo) cmbSexo.getSelectedItem();
            Tamaño tamano = (Tamaño) cmbTamano.getSelectedItem();
            String descripcion = txtDescripcion.getText().trim();
            boolean vacunado = chkVacunado.isSelected();
            boolean castrado = chkCastrado.isSelected();

            if (nombre.isEmpty()) throw new IllegalArgumentException("El nombre es obligatorio.");
            
            double peso = 0.0;
            try {
                 peso = Double.parseDouble(txtPeso.getText().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                 if(!txtPeso.getText().isEmpty()) throw new IllegalArgumentException("El peso debe ser un número válido.");
            }
            
            LocalDate fechaNac = null;
            try {
                if(!txtFechaNacimiento.getText().isEmpty())
                    fechaNac = LocalDate.parse(txtFechaNacimiento.getText().trim(), dateFormatter);
            } catch (DateTimeParseException e) {
                 throw new IllegalArgumentException("La fecha debe tener el formato yyyy-MM-dd.");
            }

            return new CrearMascotaDTO(nombre, especie, raza, sexo, tamano, peso, fechaNac, descripcion, vacunado, castrado, idAlbergue);

        } catch (IllegalArgumentException e) {
            mostrarMensaje(e.getMessage(), true);
            return null;
        }
    }

    @Override
    public ActualizarMascotaDTO getActualizarMascotaDTO() {
        return new ActualizarMascotaDTO(
            txtNombre.getText().trim(),
            txtDescripcion.getText().trim(),
            chkVacunado.isSelected(),
            chkCastrado.isSelected(),
            (EstadoMascota) cmbEstado.getSelectedItem()
        );
    }

    @Override
    public void limpiarFormulario() {
        idMascota = null;
        txtNombre.setText("");
        cmbEspecie.setSelectedIndex(0);
        txtRaza.setText("");
        cmbSexo.setSelectedIndex(0);
        cmbTamano.setSelectedIndex(0);
        txtPeso.setText("");
        txtFechaNacimiento.setText("");
        txtDescripcion.setText("");
        chkVacunado.setSelected(false);
        chkCastrado.setSelected(false);
        cmbEstado.setSelectedIndex(0);
    }

    @Override
    public void setDatosFormulario(Mascota mascota) {
        this.idMascota = mascota.getIdMascota();
        txtNombre.setText(mascota.getNombre());
        cmbEspecie.setSelectedItem(mascota.getEspecie());
        txtRaza.setText(mascota.getRaza());
        cmbSexo.setSelectedItem(mascota.getSexo());
        cmbTamano.setSelectedItem(mascota.getTamaño());
        txtPeso.setText(String.valueOf(mascota.getPeso()));
        
        if(mascota.getFechaNacimiento() != null) {
             txtFechaNacimiento.setText(mascota.getFechaNacimiento().format(dateFormatter));
        }

        txtDescripcion.setText(mascota.getDescripcion());
        chkVacunado.setSelected(mascota.isEstaVacunado());
        chkCastrado.setSelected(mascota.isEstaCastrado());
        cmbEstado.setSelectedItem(mascota.getEstado());
    }

    @Override
    public void setModoEdicion(boolean valor) {
        this.modoEdicion = valor;
        if(modoEdicion) {
            jLabel1.setText("Editar Mascota");
            btnAccionPrincipal.setText("Guardar Cambios");
            cmbEspecie.setEnabled(false);
            cmbSexo.setEnabled(false);
            txtFechaNacimiento.setEditable(false);
            lblEstado.setVisible(true);
            cmbEstado.setVisible(true);
        } else {
            jLabel1.setText("Registrar Nueva Mascota");
            btnAccionPrincipal.setText("Registrar Mascota");
            limpiarFormulario();
            cmbEspecie.setEnabled(true);
            cmbSexo.setEnabled(true);
            txtFechaNacimiento.setEditable(true);
            lblEstado.setVisible(false);
            cmbEstado.setVisible(false);
        }
    }

    @Override
    public void setCargando(boolean cargando) {
        if (cargando) LoadingHandler.show();
        else LoadingHandler.hide();
    }

    @Override
    public void alMostrar(Object... parametros) {
        if (parametros == null || parametros.length == 0 || !(parametros[0] instanceof Integer)) {
            setModoEdicion(false);
            return;
        }
        
        Integer id = (Integer)parametros[0];
        
        if (id == null || id <= 0) {
            setModoEdicion(false);
            return;
        }
        
        Mascota mascota = new MascotaServiceImpl(new MascotaRepository()).obtenerPorId(id);
        
        if (mascota == null) {
            setModoEdicion(false);
            return;
        }

        idMascota = id;
        setModoEdicion(true);
        setDatosFormulario(mascota);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        btnCancelar = new com.stackmasters.adoptaanimales.view.impl.swing.SquaredButton();

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Mascotas - Crear nueva mascota");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 666, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 604, Short.MAX_VALUE)
        );

        jLabel6.setOpaque(true);

        btnCancelar.setText("Volver");
        btnCancelar.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(210, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(191, 191, 191))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.stackmasters.adoptaanimales.view.impl.swing.SquaredButton btnCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
