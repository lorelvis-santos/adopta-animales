package com.stackmasters.adoptaanimales.view.impl;

import com.stackmasters.adoptaanimales.model.Adoptante;
import com.stackmasters.adoptaanimales.model.Mascota;
import com.stackmasters.adoptaanimales.model.Mascota.EstadoMascota;
import com.stackmasters.adoptaanimales.model.SolicitudAdopcion;
import com.stackmasters.adoptaanimales.model.SolicitudAdopcion.EstadoSolicitud;
import com.stackmasters.adoptaanimales.repository.AdoptanteRepository;
import com.stackmasters.adoptaanimales.repository.CitaRepository;
import com.stackmasters.adoptaanimales.repository.MascotaRepository;
import com.stackmasters.adoptaanimales.repository.SolicitudAdopcionRepository;
import com.stackmasters.adoptaanimales.service.AdoptanteService;
import com.stackmasters.adoptaanimales.service.MascotaService;
import com.stackmasters.adoptaanimales.service.SolicitudService;
import com.stackmasters.adoptaanimales.service.impl.AdoptanteServiceImpl;
import com.stackmasters.adoptaanimales.service.impl.MascotaServiceImpl;
import com.stackmasters.adoptaanimales.service.impl.SolicitudServiceImpl;
import com.stackmasters.adoptaanimales.utils.LoadingHandler;
import com.stackmasters.adoptaanimales.view.impl.complement.Dialog;
import com.stackmasters.adoptaanimales.view.impl.model.ModelMascota;
import com.stackmasters.adoptaanimales.view.impl.swing.table.EventAction;
import com.stackmasters.adoptaanimales.utils.Message;
import com.stackmasters.adoptaanimales.view.DashboardMascotasView;
import com.stackmasters.adoptaanimales.view.DashboardSolicitudesView;
import com.stackmasters.adoptaanimales.view.impl.complement.Message.MessageType;
import com.stackmasters.adoptaanimales.view.impl.model.ModelCard;
import com.stackmasters.adoptaanimales.view.impl.model.ModelSolicitudes;
import com.stackmasters.adoptaanimales.view.impl.swing.icon.GoogleMaterialDesignIcons;
import com.stackmasters.adoptaanimales.view.impl.swing.icon.IconFontSwing;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class DashboardSolicitudesViewImpl extends javax.swing.JPanel implements DashboardSolicitudesView {
    private SolicitudService solicitudService;
    private MascotaService mascotaService;
    private AdoptanteService adoptanteService;
    private Runnable onCrear;
    private Runnable onBuscar;
    private Consumer<Integer> onEditar;
    
    public DashboardSolicitudesViewImpl() {
        initComponents();        
        table2.fixTable(jScrollPane1);
        setOpaque(false);
        
        solicitudService = new SolicitudServiceImpl( new MascotaRepository(),  new AdoptanteRepository(), new SolicitudAdopcionRepository());
        adoptanteService = new AdoptanteServiceImpl(new AdoptanteRepository());
        mascotaService = new MascotaServiceImpl(new MascotaRepository());
        
        btnCrear.addActionListener(e -> { if (onCrear != null) { onCrear.run(); } });
        btnBuscar.addActionListener(e -> { if (onBuscar != null) { onBuscar.run(); } });
        cmbEstado.addActionListener(e -> { if (onBuscar != null) { onBuscar.run(); } });
        
        DefaultComboBoxModel<EstadoSolicitud> model = new DefaultComboBoxModel<>();
        model.addElement(null); // <--- ESTO ES LA OPCIÓN "TODOS" (null)
        for (EstadoSolicitud e : EstadoSolicitud.values()) {
            model.addElement(e);
        }
        
        cmbEstado.setModel(model);

        cmbEstado.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value == null) {
                    setText("Todas los estados"); // Texto para el null
                    setFont(getFont().deriveFont(Font.BOLD)); // Opcional: negrita
                } else if (value instanceof EstadoSolicitud) {
                    setText(((EstadoSolicitud) value).db());
                }
                return this;
            }
        });
    }
    
    // Implementación de VistaConAlertas
    
    @Override
    public void mostrarMensaje(String mensaje, boolean error) {
        if(error) {
            Message.ShowMessage(this, MessageType.ERROR, mensaje);
        } else { 
            Message.ShowMessage(this, MessageType.SUCCESS, mensaje);
        }
    }
    
    // Implementación de DashboardMascotasView
    
    @Override
    public void onCrear(Runnable accion) {
        this.onCrear = accion;
    }
    
    @Override
    public void onEditar(Consumer<Integer> accion) {
        this.onEditar = accion;
    }
    
    @Override
    public void onBuscar(Runnable accion) {
        this.onBuscar = accion;
    }
    
    @Override
    public String getBusqueda() {
        return txtBusqueda.getText();
    }
    
    @Override
    public EstadoSolicitud getEstadoSolicitud() {
        return (EstadoSolicitud)cmbEstado.getSelectedItem();
    }
    
    
    /**
     * Muestra las estadísticas generales de la aplicación.
     * (total de mascotas, solicitudes pendientes, mascotas adoptadas)
     */
    @Override
    public void mostrarEstadisticas(int totalSolicitudes, int solicitudesPendientes, int solicitudesAprobadas, int solicitudesRechazadasYCanceladas) {
        Icon icon1 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.PETS, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        card1.setData(new ModelCard("Solicitudes pendientes", solicitudesPendientes, calcularPorciento(totalSolicitudes, solicitudesPendientes), icon1));
        Icon icon2 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.BOOK, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        card2.setData(new ModelCard("Solicitudes aprobadas",solicitudesAprobadas, calcularPorciento(totalSolicitudes, solicitudesAprobadas), icon2));
        Icon icon3 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.NATURE_PEOPLE, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        card3.setData(new ModelCard("Solicitudes rechazadas/canceladas", solicitudesRechazadasYCanceladas, calcularPorciento(totalSolicitudes, solicitudesRechazadasYCanceladas), icon3));
    }
    
    /**
     * Carga la tabla de animales en el dashboard.
     * Cada fila debe permitir editar y eliminar.
     * 
     * @param mascotas Lista de mascotas a mostrar en la tabla
     */
    @Override
    public void cargarTablaSolicitudes(List<SolicitudAdopcion> solicitudes) {
        // Hay que reemplazar esto
        EventAction <ModelSolicitudes> eventAction = new EventAction<ModelSolicitudes>() {
            @Override
            public void delete(ModelSolicitudes item) {
                if (table2.isEditing()) {
                    table2.getCellEditor().cancelCellEditing();
                }
                
                if (showMessage("¿Estás seguro de eliminar la solicitud de adopcion?")) {
                    try {
                        // Llamas a tu servicio real usando el ID que escondimos en el modelo
                        boolean ok = solicitudService.eliminar(item.getId()); 

                        if (ok) { 
                            // IMPORTANTE: Recargar la tabla para que desaparezca la fila
                            // Puedes llamar a alMostrar() de nuevo o quitar la fila del modelo de la tabla manualmente
                            alMostrar(); 
                            mostrarMensaje("Solicitud eliminada correctamente", false);
                            System.out.println("Solicitud eliminada: " + item.getId());
                        }
                    } catch (Exception e) {
                        System.err.println("Error borrando: " + e.getMessage());
                    }
                }
            }

            @Override
            public void update(ModelSolicitudes item) {
                if (onEditar != null) {
                    onEditar.accept(item.getId());
                }           
            }
        };
        
        DefaultTableModel model = (DefaultTableModel) table2.getModel();
        model.setRowCount(0);
        
        for (SolicitudAdopcion solicitud : solicitudes) {
            Mascota mascota = mascotaService.obtenerPorId(solicitud.getMascotaId());
            Adoptante adoptante = adoptanteService.obtenerPorId(solicitud.getAdoptanteId());

            // Estado string (el enum trae .db() )
            String estadoString = solicitud.getEstado().db();

            ModelSolicitudes ms = new ModelSolicitudes(
                solicitud.getIdSolicitud(),                                  // id
                new ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/profile3.jpg")),
                mascota.getNombre() + " (" + mascota.getEspecie().db() + ": " + mascota.getRaza() + ")",                      // name (placeholder)
                adoptante.getNombre() + " " + adoptante.getApellido(),                  // adoptante (placeholder)
                estadoString,                                                // estado string
                solicitud.getFechaSolicitud().toString(),                                                 // fecha int
                solicitud.getCita().toString()                                                       // cita NO existe en el modelo
            );

    model.addRow(ms.toRowTable(eventAction));
}
    }
    
    /**
     * Método para mostrar un spinner de carga en el UI.
     * 
     * @param cargando True si está cargando, false si no
     */
    @Override
    public void setCargando(boolean cargando) {
        if (cargando) {
            LoadingHandler.show();
        } else {
            LoadingHandler.hide();
        }
    }
    
    @Override
    public void alMostrar(Object... parametros) {
        setCargando(true);
        
        new SwingWorker<Void, Void>() {
            private Exception error;
            private int totalSolicitudes, totalSolicitudesPendientes, totalSolicitudesAprobadas, totalSolicitudesRechazadas, totalSolicitudesCanceladas;
            private List<SolicitudAdopcion> solicitud;

            @Override protected Void doInBackground() {
                try { 
                    // Obtener datos para las tarjetas
                    totalSolicitudes = solicitudService.totalSolicitudes();
                    totalSolicitudesPendientes = solicitudService.totalSolicitudesPorEstado(SolicitudAdopcion.EstadoSolicitud.Pendiente);
                    totalSolicitudesAprobadas = solicitudService.totalSolicitudesPorEstado(SolicitudAdopcion.EstadoSolicitud.Aprobada);
                    totalSolicitudesRechazadas = solicitudService.totalSolicitudesPorEstado(SolicitudAdopcion.EstadoSolicitud.Rechazada);
                    totalSolicitudesCanceladas = solicitudService.totalSolicitudesPorEstado(SolicitudAdopcion.EstadoSolicitud.Cancelada);
                    
                    // Obtener lista de mascotas
                    solicitud = solicitudService.listar(null);
                } catch (Exception e) {
                    error = e; 
                }

                return null;
            }

            @Override protected void done() {
                setCargando(false);                         
                    
                if (error != null) {
                    System.out.println(error.getMessage());
                    return; 
                } 
                
                mostrarEstadisticas(totalSolicitudes, totalSolicitudesPendientes, totalSolicitudesAprobadas, (totalSolicitudesRechazadas + totalSolicitudesCanceladas));
                cargarTablaSolicitudes(solicitud);
            }
        }.execute();
    }
    
    // ----------------------------------------------------------------------
    
    // Pendiente dar soporte a meses, no solo años
    private int calcularEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new IllegalArgumentException("Fecha de nacimiento inválida");
        }
        
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
    
    
    private int calcularPorciento(double cifraTotal, double valor) {
        if (cifraTotal == 0) {
            return 0;
        }
        
        return (int)Math.round(valor/cifraTotal*100);
    }

    private boolean showMessage(String message) {
        // 1. Buscamos dinámicamente quién es la ventana padre de este panel
        JFrame framePrincipal = (JFrame) SwingUtilities.getWindowAncestor(this);

        // 2. Pasamos ese frame al constructor del Mensaje
        Dialog obj = new Dialog(framePrincipal, true);

        // 3. Mostramos el mensaje (el resto sigue igual)
        obj.showMessage(message);

        return obj.isOk();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbEstado = new javax.swing.JComboBox();
        txtBusqueda = new javax.swing.JTextField();
        btnBuscar = new com.stackmasters.adoptaanimales.view.impl.swing.SquaredButton();
        card1 = new com.stackmasters.adoptaanimales.view.impl.complement.dashboard.Card();
        jLabel1 = new javax.swing.JLabel();
        card2 = new com.stackmasters.adoptaanimales.view.impl.complement.dashboard.Card();
        card3 = new com.stackmasters.adoptaanimales.view.impl.complement.dashboard.Card();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table2 = new com.stackmasters.adoptaanimales.view.impl.swing.table.Table();
        btnCrear = new com.stackmasters.adoptaanimales.view.impl.swing.SquaredButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmbEstado.setBackground(new java.awt.Color(204, 204, 204));
        cmbEstado.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        cmbEstado.setForeground(new java.awt.Color(0, 0, 0));
        cmbEstado.setToolTipText("Estado");
        cmbEstado.setFocusable(false);
        cmbEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEstadoActionPerformed(evt);
            }
        });
        add(cmbEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(646, 180, 190, 48));

        txtBusqueda.setToolTipText("Busca por nombre o raza");
        add(txtBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 180, 154, 48));

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/lupa (2).png"))); // NOI18N
        btnBuscar.setToolTipText("Buscar");
        btnBuscar.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 180, 36, 48));

        card1.setBackground(new java.awt.Color(79, 172, 254));
        card1.setForeground(new java.awt.Color(255, 255, 255));
        card1.setColorGradient(new java.awt.Color(0, 242, 254));
        add(card1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 41, 322, -1));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Solicitudes");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));

        card2.setBackground(new java.awt.Color(255, 154, 158));
        card2.setForeground(new java.awt.Color(255, 255, 255));
        card2.setColorGradient(new java.awt.Color(254, 207, 239));
        add(card2, new org.netbeans.lib.awtextra.AbsoluteConstraints(346, 41, 342, -1));

        card3.setBackground(new java.awt.Color(246, 211, 101));
        card3.setColorGradient(new java.awt.Color(253, 160, 133));
        add(card3, new org.netbeans.lib.awtextra.AbsoluteConstraints(706, 41, 349, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(76, 76, 76));
        jLabel5.setText(" Solicitudes");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setOpaque(false);

        table2.setBackground(new java.awt.Color(255, 255, 255));
        table2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mascota", "Adoptante", "Estado solicitud", "Fecha solicitud", "Cita programada", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table2);
        if (table2.getColumnModel().getColumnCount() > 0) {
            table2.getColumnModel().getColumn(0).setPreferredWidth(150);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 242, 1049, 476));

        btnCrear.setText("Nueva solicitud");
        btnCrear.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        add(btnCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 182, 158, 42));
    }// </editor-fold>//GEN-END:initComponents

    private void cmbEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbEstadoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.stackmasters.adoptaanimales.view.impl.swing.SquaredButton btnBuscar;
    private com.stackmasters.adoptaanimales.view.impl.swing.SquaredButton btnCrear;
    private com.stackmasters.adoptaanimales.view.impl.complement.dashboard.Card card1;
    private com.stackmasters.adoptaanimales.view.impl.complement.dashboard.Card card2;
    private com.stackmasters.adoptaanimales.view.impl.complement.dashboard.Card card3;
    private javax.swing.JComboBox cmbEstado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.stackmasters.adoptaanimales.view.impl.swing.table.Table table2;
    private javax.swing.JTextField txtBusqueda;
    // End of variables declaration//GEN-END:variables
}
