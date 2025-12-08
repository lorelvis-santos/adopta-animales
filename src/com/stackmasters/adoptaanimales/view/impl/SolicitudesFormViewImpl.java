package com.stackmasters.adoptaanimales.view.impl;

import com.stackmasters.adoptaanimales.dto.ActualizarSolicitudDTO;
import com.stackmasters.adoptaanimales.dto.CrearAdoptanteDTO;
import com.stackmasters.adoptaanimales.dto.FiltroMascotaDTO;
import com.stackmasters.adoptaanimales.model.Adoptante;
import com.stackmasters.adoptaanimales.model.Mascota;
import com.stackmasters.adoptaanimales.model.Mascota.EstadoMascota;
import com.stackmasters.adoptaanimales.model.SolicitudAdopcion;
import com.stackmasters.adoptaanimales.model.SolicitudAdopcion.EstadoSolicitud; // <--- IMPORTANTE: Importar el Enum
import com.stackmasters.adoptaanimales.repository.AdoptanteRepository;
import com.stackmasters.adoptaanimales.repository.MascotaRepository;
import com.stackmasters.adoptaanimales.service.AdoptanteService;
import com.stackmasters.adoptaanimales.service.MascotaService;
import com.stackmasters.adoptaanimales.service.impl.AdoptanteServiceImpl;
import com.stackmasters.adoptaanimales.service.impl.MascotaServiceImpl;
import com.stackmasters.adoptaanimales.utils.LoadingHandler;
import com.stackmasters.adoptaanimales.utils.Message;
import com.stackmasters.adoptaanimales.view.SolicitudesFormView;
import com.stackmasters.adoptaanimales.view.impl.complement.Message.MessageType;
import com.stackmasters.adoptaanimales.view.impl.swing.SquaredButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class SolicitudesFormViewImpl extends javax.swing.JPanel implements SolicitudesFormView {

    private MascotaService mascotaService;
    private AdoptanteService adoptanteService;
    private Runnable onCancelar;
    private Runnable onAccionPrincipal;
    
    // --- Estado del Formulario ---
    private Integer idSolicitudActual = null; // null = Creando, ID = Editando
    
    // --- Componentes lógicos ---
    private JComboBox<Mascota> cmbMascotas;
    private JComboBox<EstadoSolicitud> cmbEstado; // <--- 1. NUEVO COMBOBOX
    
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtFechaNacimiento;
    private JTextField txtTelefono;
    private JTextField txtFechaCita; 
    private JTextArea txtDireccion;
    private SquaredButton btnAccionPrincipal;
    private Consumer<Integer> onCargarSolicitud;
    private Integer idPendienteDeCarga = null;

    // --- Formateadores ---
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public SolicitudesFormViewImpl() {
        initComponents(); 
        
        // Inicialización del servicio
        this.mascotaService = new MascotaServiceImpl(new MascotaRepository());
        this.adoptanteService = new AdoptanteServiceImpl(new AdoptanteRepository());
        
        // Configuración de UI manual
        this.removeAll(); 
        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(245, 247, 250));

        // Configuración del panel tarjeta
        jPanel2.setBackground(Color.WHITE);
        jPanel2.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1), 
                new EmptyBorder(30, 50, 40, 50)
        ));
        
        jPanel2.setPreferredSize(new Dimension(900, 800)); 

        // Construcción de campos
        setupFormPanel();

        this.add(jPanel2, new GridBagConstraints()); 

        // Listeners básicos
        btnCancelar.addActionListener(e -> {
            if (onCancelar != null) onCancelar.run();
        });
        
        btnAccionPrincipal.addActionListener(e -> {
             if (onAccionPrincipal != null) onAccionPrincipal.run();
        });
    }

    // =========================================================================
    // IMPLEMENTACIÓN DE VISTA NAVEGABLE
    // =========================================================================
    
    @Override
    public void onCargarSolicitud(Consumer<Integer> accion) {
        this.onCargarSolicitud = accion;
    }

    @Override
    public void alMostrar(Object... parametros) {
        // 1. Verificamos si nos pasaron un ID desde el Router
        if (parametros.length > 0 && parametros[0] instanceof Integer) {
            this.idPendienteDeCarga = (Integer) parametros[0]; // Guardamos el ID
        } else {
            this.idPendienteDeCarga = null; // Es una creación nueva
        }

        // 2. Iniciamos la carga de mascotas (necesaria para el combo)
        cargarMascotas();
    }

    private void cargarMascotas() {
        setCargando(true);
        new SwingWorker<List<Mascota>, Void>() {
            @Override
            protected List<Mascota> doInBackground() throws Exception {
                return mascotaService.buscar(new FiltroMascotaDTO(null, null, null, null, EstadoMascota.EnAlbergue));
            }

            @Override
            protected void done() {
                try {
                    List<Mascota> lista = get();
                    setListaMascotas(lista);

                    // --- Carga diferida ---
                    if (idPendienteDeCarga != null) {
                        // MODO EDICIÓN: Pedimos al controlador que traiga los datos
                        if (onCargarSolicitud != null) {
                            onCargarSolicitud.accept(idPendienteDeCarga);
                        }
                    } else {
                        // MODO CREACIÓN: Limpiamos todo
                        limpiarFormulario();
                    }

                } catch (Exception e) {
                    mostrarMensaje("Error al cargar mascotas: " + e.getMessage(), true);
                    e.printStackTrace();
                } finally {
                    setCargando(false);
                }
            }
        }.execute();
    }

    // =========================================================================
    // LÓGICA DE EDICIÓN Y CAPTURA DE DATOS
    // =========================================================================

    @Override
    public Integer getIdSolicitud() {
        return this.idSolicitudActual;
    }

    @Override
    public void setSolicitudParaEditar(SolicitudAdopcion solicitud) {
        if (solicitud == null) return;
        
        this.idSolicitudActual = solicitud.getIdSolicitud();

        // 1. Establecer ID y Modo Visual (Edición)
        jLabel1.setText("Editar Solicitud #" + idSolicitudActual);
        btnAccionPrincipal.setText("Guardar Cambios");

        // --- CORRECCIÓN DEL COMBOBOX ---
        boolean encontradaEnCombo = false;
        Mascota mascotaActual = mascotaService.obtenerPorId(solicitud.getMascotaId()); // Asegúrate de que tu Solicitud traiga el objeto Mascota

        // 1. Buscamos si ya está en la lista
        DefaultComboBoxModel<Mascota> model = (DefaultComboBoxModel<Mascota>) cmbMascotas.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            Mascota m = model.getElementAt(i);
            if (m.getIdMascota() == solicitud.getMascotaId()) {
                cmbMascotas.setSelectedIndex(i);
                encontradaEnCombo = true;
                break;
            }
        }

        // 2. Si NO está (porque su estado cambió), la agregamos forzosamente para visualizarla
        if (!encontradaEnCombo && mascotaActual != null) {
            model.addElement(mascotaActual); // La agregamos al final
            cmbMascotas.setSelectedItem(mascotaActual); // La seleccionamos
        }
        
        // 3. SELECCIONAR EL ESTADO CORRECTO EN EL NUEVO COMBO
        if (solicitud.getEstado() != null) {
            cmbEstado.setSelectedItem(solicitud.getEstado());
        }

        // 4. Llenar fecha de cita
        if (solicitud.getCita() != null) {
            txtFechaCita.setText(solicitud.getCita().format(dateTimeFormatter));
        }
        
        Adoptante adoptante = adoptanteService.obtenerPorId(solicitud.getAdoptanteId());

        // 5. Llenar datos del Adoptante
        if (adoptante != null) {
            txtNombre.setText(adoptante.getNombre());
            txtApellido.setText(adoptante.getApellido());
            txtTelefono.setText(adoptante.getTelefono());
            txtDireccion.setText(adoptante.getDireccion());
            
            if (adoptante.getFechaNacimiento() != null) {
                txtFechaNacimiento.setText(adoptante.getFechaNacimiento().format(dateFormatter));
            }
        }
        
        setModoEdicion(true);
    }

    @Override
    public ActualizarSolicitudDTO getActualizarSolicitudDTO() {
        // Obtenemos datos validados reutilizando métodos existentes
        CrearAdoptanteDTO datosAdoptante = getDatosAdoptante(); 
        Mascota mascota = getMascotaSeleccionada();
        LocalDateTime cita = getFechaHoraCita();

        if (mascota == null) {
            throw new IllegalArgumentException("Debe seleccionar una mascota.");
        }

        ActualizarSolicitudDTO dto = new ActualizarSolicitudDTO();
        dto.setMascotaId(mascota.getIdMascota());
        dto.setFechaCita(cita);
        dto.setDatosAdoptante(datosAdoptante);
        
        // CAPTURAR EL NUEVO ESTADO PARA EL DTO
        // Asumiendo que tu DTO soporta setEstado. Si no, debes agregarlo al DTO.
        dto.setEstado((EstadoSolicitud) cmbEstado.getSelectedItem());
        
        return dto;
    }

    @Override
    public CrearAdoptanteDTO getDatosAdoptante() {
        try {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String direccion = txtDireccion.getText().trim();
            
            if(nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty()) {
                throw new IllegalArgumentException("Nombre, Apellido y Teléfono son obligatorios.");
            }

            LocalDate fechaNac = null;
            try {
                if(!txtFechaNacimiento.getText().isEmpty())
                    fechaNac = LocalDate.parse(txtFechaNacimiento.getText().trim(), dateFormatter);
                else 
                     throw new IllegalArgumentException("La fecha de nacimiento es obligatoria.");
            } catch (DateTimeParseException e) {
                 throw new IllegalArgumentException("La fecha de nacimiento debe tener el formato yyyy-MM-dd.");
            }
            
            return new CrearAdoptanteDTO(nombre, apellido, fechaNac, telefono, direccion);

        } catch (IllegalArgumentException e) {
            throw e; 
        }
    }

    @Override
    public LocalDateTime getFechaHoraCita() {
        String texto = txtFechaCita.getText().trim();
        if (texto.isEmpty()) {
            throw new IllegalArgumentException("La fecha y hora de la cita son obligatorias.");
        }
        try {
            return LocalDateTime.parse(texto, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("La cita debe tener formato: yyyy-MM-dd HH:mm");
        }
    }

    @Override
    public Mascota getMascotaSeleccionada() {
        return (Mascota) cmbMascotas.getSelectedItem();
    }
    
    // =========================================================================
    // UI BUILDER & HELPERS
    // =========================================================================

    private void setupFormPanel() {
        jPanel2.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        Font fontTitle = new Font("SansSerif", Font.BOLD, 26);
        Font fontSubtitle = new Font("SansSerif", Font.BOLD, 16); 
        Font fontBtnBack = new Font("SansSerif", Font.BOLD, 14);

        // --- CABECERA ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 25, 0); 
        
        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setOpaque(false);
        
        btnCancelar.setBackground(new Color(235, 235, 235));
        btnCancelar.setForeground(new Color(80, 80, 80));
        btnCancelar.setText("< Volver");
        btnCancelar.setFont(fontBtnBack);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.setPreferredSize(new Dimension(120, 40)); 
        
        jLabel1.setFont(fontTitle);
        jLabel1.setForeground(new Color(33, 33, 33));
        jLabel1.setText("Nueva Solicitud de Adopción"); 
        
        GridBagConstraints gbcH = new GridBagConstraints();
        gbcH.gridx = 0; gbcH.insets = new Insets(0,0,0,30); 
        headerPanel.add(btnCancelar, gbcH);
        gbcH.gridx = 1; gbcH.weightx = 1.0; 
        headerPanel.add(jLabel1, gbcH);
        
        jPanel2.add(headerPanel, gbc);
        
        // --- INICIALIZACIÓN COMPONENTES ---
        cmbMascotas = new JComboBox<>();
        estilarCombo(cmbMascotas);
        cmbMascotas.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Mascota) {
                    Mascota m = (Mascota) value;
                    setText(m.getNombre() + " (" + m.getEspecie().db() + ": " + m.getRaza() + ")");
                }
                return this;
            }
        });

        // INICIALIZACIÓN DEL COMBO ESTADO
        cmbEstado = new JComboBox<>(EstadoSolicitud.values());
        estilarCombo(cmbEstado);
        
        txtNombre = crearTextField();
        txtApellido = crearTextField();
        txtFechaNacimiento = crearTextField(); txtFechaNacimiento.setToolTipText("yyyy-MM-dd");
        txtTelefono = crearTextField();
        
        // Campo Cita
        txtFechaCita = crearTextField();
        txtFechaCita.setToolTipText("yyyy-MM-dd HH:mm");
        
        txtDireccion = new JTextArea(3, 20);
        txtDireccion.setLineWrap(true);
        txtDireccion.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtDireccion.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200)), new EmptyBorder(5, 5, 5, 5)));
        JScrollPane scrollDireccion = new JScrollPane(txtDireccion);
        
        btnAccionPrincipal = new SquaredButton();
        btnAccionPrincipal.setText("Registrar Solicitud"); // Texto default
        btnAccionPrincipal.setBackground(new Color(33, 150, 243)); 
        btnAccionPrincipal.setForeground(Color.WHITE);
        btnAccionPrincipal.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnAccionPrincipal.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // --- CONSTRUCCIÓN GRIDBAG ---
        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        int row = 1;

        // 1. Mascota
        addSubtitle(jPanel2, row, "1. Selección de Mascota", fontSubtitle);
        row++;
        addStackedLabel(jPanel2, 0, row, "Mascota a Adoptar");
        gbc.gridx = 0; gbc.gridy = row + 1; gbc.gridwidth = 2;
        gbc.insets = new Insets(3, 0, 25, 0); 
        jPanel2.add(cmbMascotas, gbc);
        gbc.gridwidth = 1; row += 2;

        // 2. Adoptante
        addSubtitle(jPanel2, row, "2. Datos del Solicitante", fontSubtitle);
        row++;
        row = addStackedDualField(jPanel2, row, "Nombre", txtNombre, "Apellido", txtApellido);
        row = addStackedDualField(jPanel2, row, "Fecha Nacimiento (yyyy-MM-dd)", txtFechaNacimiento, "Teléfono", txtTelefono);
        
        // 3. Cita y Estado (AHORA EN LA MISMA FILA)
        addSubtitle(jPanel2, row, "3. Detalles de la Solicitud", fontSubtitle);
        row++;
        row = addStackedDualField(jPanel2, row, "Cita de Visita (yyyy-MM-dd HH:mm)", txtFechaCita, "Estado Actual", cmbEstado);

        // 4. Dirección
        addStackedLabel(jPanel2, 0, row, "Dirección Completa");
        gbc.gridx = 0; gbc.gridy = row + 1; gbc.gridwidth = 2; 
        gbc.weightx = 1.0; gbc.ipady = 40; 
        gbc.insets = new Insets(3, 0, 15, 0);
        jPanel2.add(scrollDireccion, gbc);
        gbc.gridwidth = 1; gbc.ipady = 0; row += 2;
        
        // Botón
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 0, 0, 0); 
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.CENTER; 
        btnAccionPrincipal.setPreferredSize(new Dimension(300, 50)); 
        jPanel2.add(btnAccionPrincipal, gbc);
    }

    private void addSubtitle(JPanel p, int row, String text, Font font) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        JLabel l = new JLabel(text);
        l.setFont(font);
        l.setForeground(new Color(33, 150, 243));
        p.add(l, gbc);
    }

    private int addStackedDualField(JPanel p, int startRow, String l1, JComponent c1, String l2, JComponent c2) {
        JPanel rowPanel = new JPanel(new java.awt.GridLayout(1, 2, 20, 0));
        rowPanel.setOpaque(false);

        JPanel leftPanel = new JPanel(new java.awt.BorderLayout(0, 5));
        leftPanel.setOpaque(false);
        leftPanel.add(crearLabel(l1), java.awt.BorderLayout.NORTH);
        leftPanel.add(c1, java.awt.BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new java.awt.BorderLayout(0, 5));
        rightPanel.setOpaque(false);
        rightPanel.add(crearLabel(l2), java.awt.BorderLayout.NORTH);
        rightPanel.add(c2, java.awt.BorderLayout.CENTER);

        rowPanel.add(leftPanel);
        rowPanel.add(rightPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; 
        gbc.gridy = startRow;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 15, 0);

        p.add(rowPanel, gbc);
        return startRow + 1; 
    }
    
    private void addStackedLabel(JPanel p, int x, int y, String text) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x; gbc.gridy = y; 
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 0);
        p.add(crearLabel(text), gbc);
    }
    
    private JLabel crearLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        l.setForeground(new Color(70, 70, 70));
        return l;
    }
    
    private JTextField crearTextField() {
        JTextField t = new JTextField();
        t.setFont(new Font("SansSerif", Font.PLAIN, 14));
        t.setPreferredSize(new Dimension(0, 35)); 
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

    // =========================================================================
    // LISTENERS Y UTILIDADES DE VISTA
    // =========================================================================
    
    public void setModoEdicion(boolean activo) {
        boolean habilitado = !activo; 

        // 1. Datos Fijos (Deshabilitados en edición)
        cmbMascotas.setEnabled(habilitado);
        txtNombre.setEditable(habilitado);
        txtNombre.setEnabled(habilitado);
        txtApellido.setEditable(habilitado);
        txtApellido.setEnabled(habilitado);
        txtFechaNacimiento.setEditable(habilitado);
        txtFechaNacimiento.setEnabled(habilitado);
        txtDireccion.setEditable(habilitado);
        txtDireccion.setEnabled(habilitado);
        txtTelefono.setEditable(habilitado);
        txtTelefono.setEnabled(habilitado);

        // 2. Datos Editables (Siempre habilitados)
        txtFechaCita.setEditable(true); 
        cmbEstado.setEnabled(true); // El estado se puede cambiar en edición

        if (activo) {
            btnAccionPrincipal.setText("Actualizar Datos");
            jLabel1.setText("Editar Solicitud");
        } else {
            btnAccionPrincipal.setText("Registrar Solicitud");
            jLabel1.setText("Nueva Solicitud");
            // En creación, generalmente el estado es fijo "Pendiente", así que lo bloqueamos
            cmbEstado.setEnabled(false); 
        }
    }

    @Override
    public void onAccionPrincipal(Runnable accion) {
        this.onAccionPrincipal = accion;
    }

    @Override
    public void onCancelar(Runnable accion) {
        this.onCancelar = accion;
    }

    @Override
    public void setListaMascotas(List<Mascota> mascotas) {
        DefaultComboBoxModel<Mascota> model = new DefaultComboBoxModel<>();
        for (Mascota m : mascotas) {
            model.addElement(m);
        }
        cmbMascotas.setModel(model);
    }

    @Override
    public void mostrarMensaje(String msg, boolean error) {
        if(error) Message.ShowMessage(this, MessageType.ERROR, msg);
        else Message.ShowMessage(this, MessageType.SUCCESS, msg);
    }

    @Override
    public void setCargando(boolean activo) {
        if(activo) LoadingHandler.show();
        else LoadingHandler.hide();
    }

    @Override
    public void limpiarFormulario() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtFechaNacimiento.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtFechaCita.setText("");
        
        if(cmbMascotas.getItemCount() > 0) cmbMascotas.setSelectedIndex(0);
        
        // Resetear Estado (Seleccionar el primero, usualmente Pendiente)
        if (cmbEstado.getItemCount() > 0) cmbEstado.setSelectedIndex(0);
        
        // Resetear Estado de Edición -> Modo Creación
        this.idSolicitudActual = null;
        setModoEdicion(false);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        btnCancelar = new com.stackmasters.adoptaanimales.view.impl.swing.SquaredButton();

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Nueva Solicitud");

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
    }// </editor-fold>                        

    // Variables declaration - do not modify                     
    private com.stackmasters.adoptaanimales.view.impl.swing.SquaredButton btnCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration                   
}