package com.stackmasters.adoptaanimales.view.impl;

import com.stackmasters.adoptaanimales.model.Mascota;
import com.stackmasters.adoptaanimales.model.Mascota.Especie;
import com.stackmasters.adoptaanimales.model.Mascota.EstadoMascota;
import com.stackmasters.adoptaanimales.model.SolicitudAdopcion;
import com.stackmasters.adoptaanimales.repository.MascotaRepository;
import com.stackmasters.adoptaanimales.service.MascotaService;
import com.stackmasters.adoptaanimales.service.impl.MascotaServiceImpl;
import com.stackmasters.adoptaanimales.utils.LoadingHandler;
import com.stackmasters.adoptaanimales.view.impl.complement.Dialog;
import com.stackmasters.adoptaanimales.view.impl.model.ModelMascota;
import com.stackmasters.adoptaanimales.view.impl.swing.table.EventAction;
import com.stackmasters.adoptaanimales.utils.Message;
import com.stackmasters.adoptaanimales.view.DashboardMascotasView;
import com.stackmasters.adoptaanimales.view.impl.complement.Message.MessageType;
import com.stackmasters.adoptaanimales.view.impl.model.ModelCard;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class DashboardMascotasViewImpl extends javax.swing.JPanel implements DashboardMascotasView {
    private MascotaService mascotaService;
    private Runnable onCrear;
    private Runnable onBuscar;
    private Consumer<Integer> onEditar;
    
    public DashboardMascotasViewImpl() {
        initComponents();        
        table1.fixTable(jScrollPane1);
        setOpaque(false);
        
        mascotaService = new MascotaServiceImpl(new MascotaRepository());
        
        btnCrearMascota.addActionListener(e -> { if (onCrear != null) { onCrear.run(); } });
        btnBuscar.addActionListener(e -> { if (onBuscar != null) { onBuscar.run(); } });
        cmbEspecie.addActionListener(e -> { if (onBuscar != null) { onBuscar.run(); } });
        cmbEstado.addActionListener(e -> { if (onBuscar != null) { onBuscar.run(); } });
        
        // ----------------------------------------------------------------------------------
        // 1. CONFIGURACIÓN DE ESPECIE (Con opción "Todas")
        // ----------------------------------------------------------------------------------
        DefaultComboBoxModel<Especie> modelEspecie = new DefaultComboBoxModel<>();
        modelEspecie.addElement(null); // <--- ESTO ES LA OPCIÓN "TODOS" (null)
        for (Especie e : Especie.values()) {
            modelEspecie.addElement(e);
        }
        cmbEspecie.setModel(modelEspecie);

        cmbEspecie.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value == null) {
                    setText("Todas las especies"); // Texto para el null
                    setFont(getFont().deriveFont(Font.BOLD)); // Opcional: negrita
                } else if (value instanceof Especie) {
                    setText(((Especie) value).db());
                }
                return this;
            }
        });

        // ----------------------------------------------------------------------------------
        // 2. CONFIGURACIÓN DE ESTADO (Con opción "Todos")
        // ----------------------------------------------------------------------------------
        DefaultComboBoxModel<EstadoMascota> modelEstado = new DefaultComboBoxModel<>();
        modelEstado.addElement(null); // <--- ESTO ES LA OPCIÓN "TODOS" (null)
        for (EstadoMascota e : EstadoMascota.values()) {
            modelEstado.addElement(e);
        }
        cmbEstado.setModel(modelEstado);

        cmbEstado.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value == null) {
                    setText("Todos los estados"); // Texto para el null
                    setFont(getFont().deriveFont(Font.BOLD));
                    setForeground(Color.BLACK);
                } else if (value instanceof EstadoMascota) {
                    EstadoMascota estado = (EstadoMascota) value;
                    setText(estado.db());

                    if (estado == EstadoMascota.Adoptada) {
                        setForeground(new Color(0, 150, 0));
                    } else if (estado == EstadoMascota.EnProcesoDeAdopcion) {
                        setForeground(new Color(255, 140, 0));
                    } else {
                        setForeground(Color.BLACK);
                    }
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
    public EstadoMascota getEstadoMascota() {
        return (EstadoMascota)cmbEstado.getSelectedItem();
    }
    
    @Override
    public Especie getEspecie() {
        return (Especie)cmbEspecie.getSelectedItem();
    }
    
    /**
     * Muestra las estadísticas generales de la aplicación.
     * (total de mascotas, solicitudes pendientes, mascotas adoptadas)
     */
    @Override
    public void mostrarEstadisticas(int totalMascotas, int totalMascotasEnAlbergue, int totalMascotasAdoptadas) {
        Icon icon1 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.PETS, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        card1.setData(new ModelCard("Total de mascotas", totalMascotas, 100, icon1));
        Icon icon2 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.BOOK, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        card2.setData(new ModelCard("Mascotas en albergue", totalMascotasEnAlbergue, calcularPorciento(totalMascotas, totalMascotasEnAlbergue), icon2));
        Icon icon3 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.NATURE_PEOPLE, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        card3.setData(new ModelCard("Mascotas adoptadas", totalMascotasAdoptadas, calcularPorciento(totalMascotas, totalMascotasAdoptadas), icon3));
    }
    
    /**
     * Carga la tabla de animales en el dashboard.
     * Cada fila debe permitir editar y eliminar.
     * 
     * @param mascotas Lista de mascotas a mostrar en la tabla
     */
    @Override
    public void cargarTablaMascotas(List<Mascota> mascotas) {
        // Hay que reemplazar esto
        EventAction <ModelMascota> eventAction = new EventAction <ModelMascota> () {
            @Override
            public void delete(ModelMascota item) {
                if (table1.isEditing()) {
                    // Cancelamos la edición. No usamos 'stop' porque no queremos guardar,
                    // solo queremos que suelte el foco para poder borrar la fila en paz.
                    table1.getCellEditor().cancelCellEditing();
                }
                
                if (showMessage("¿Estás seguro de eliminar a " + item.getName() + "?")) {
                    try {
                        // Llamas a tu servicio real usando el ID que escondimos en el modelo
                        boolean ok = mascotaService.eliminar(item.getId()); 

                        if (ok) { 
                            // IMPORTANTE: Recargar la tabla para que desaparezca la fila
                            // Puedes llamar a alMostrar() de nuevo o quitar la fila del modelo de la tabla manualmente
                            alMostrar(); 
                            mostrarMensaje("Mascota eliminada correctamente", false);
                            System.out.println("Mascota eliminada: " + item.getId());
                        }
                    } catch (Exception e) {
                        System.err.println("Error borrando: " + e.getMessage());
                    }
                }
            }

            @Override
            public void update(ModelMascota item) {
                if (onEditar != null) {
                    onEditar.accept(item.getId());
                }
            }

           
        };
        
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0);
        
        for (Mascota mascota : mascotas) {
            String url = mascota.getEspecie() == Especie.Perro ? 
                "/com/stackmasters/adoptaanimales/view/impl/icon/dog.png" : 
                "/com/stackmasters/adoptaanimales/view/impl/icon/cat-black.png";

            model.addRow(
                new ModelMascota(
                    mascota.getIdMascota(),
                    new ImageIcon(getClass().getResource(url)),
                    mascota.getNombre(),
                    mascota.getSexo().db(),
                    mascota.getRaza(),
                    mascota.isEstaCastrado(),
                    calcularEdad(mascota.getFechaNacimiento()),
                    mascota.getPeso(),
                    mascota.getEstado().db()
                ).toRowTable(eventAction)
            );
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
            private int totalMascotas, totalMascotasEnAlbergue, totalMascotasAdoptadas;
            private List<Mascota> mascotas;

            @Override protected Void doInBackground() {
                try { 
                    // Obtener datos para las tarjetas
                    totalMascotas = mascotaService.totalMascotas();
                    totalMascotasEnAlbergue = mascotaService.totalMascotasPorEstado(EstadoMascota.EnAlbergue);
                    totalMascotasAdoptadas = mascotaService.totalMascotasPorEstado(EstadoMascota.Adoptada);
                    
                    // Obtener lista de mascotas
                    mascotas = mascotaService.buscar(null);
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
                
                mostrarEstadisticas(totalMascotas, totalMascotasEnAlbergue, totalMascotasAdoptadas);
                cargarTablaMascotas(mascotas);
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

        card1 = new com.stackmasters.adoptaanimales.view.impl.complement.dashboard.Card();
        jLabel1 = new javax.swing.JLabel();
        card2 = new com.stackmasters.adoptaanimales.view.impl.complement.dashboard.Card();
        card3 = new com.stackmasters.adoptaanimales.view.impl.complement.dashboard.Card();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new com.stackmasters.adoptaanimales.view.impl.swing.table.Table();
        jLabel6 = new javax.swing.JLabel();
        btnBuscar = new com.stackmasters.adoptaanimales.view.impl.swing.SquaredButton();
        cmbEspecie = new javax.swing.JComboBox<>();
        cmbEstado = new javax.swing.JComboBox<>();
        txtBusqueda = new javax.swing.JTextField();
        btnCrearMascota = new com.stackmasters.adoptaanimales.view.impl.swing.SquaredButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        card1.setBackground(new java.awt.Color(79, 172, 254));
        card1.setForeground(new java.awt.Color(255, 255, 255));
        card1.setColorGradient(new java.awt.Color(0, 242, 254));
        add(card1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 41, 322, -1));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Mascotas");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1001, 6, -1, -1));

        card2.setBackground(new java.awt.Color(255, 154, 158));
        card2.setForeground(new java.awt.Color(255, 255, 255));
        card2.setColorGradient(new java.awt.Color(254, 207, 239));
        add(card2, new org.netbeans.lib.awtextra.AbsoluteConstraints(346, 41, 342, -1));

        card3.setBackground(new java.awt.Color(246, 211, 101));
        card3.setColorGradient(new java.awt.Color(253, 160, 133));
        add(card3, new org.netbeans.lib.awtextra.AbsoluteConstraints(702, 41, 360, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(76, 76, 76));
        jLabel5.setText("Mascotas");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setOpaque(false);

        table1.setBackground(new java.awt.Color(255, 255, 255));
        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Genero", "Raza", "Castrado", "Edad", "Peso", "Estado", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table1);
        if (table1.getColumnModel().getColumnCount() > 0) {
            table1.getColumnModel().getColumn(0).setPreferredWidth(150);
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

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 242, 1056, 476));

        jLabel6.setOpaque(true);
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(534, 373, -1, -1));

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/lupa (2).png"))); // NOI18N
        btnBuscar.setToolTipText("Buscar");
        btnBuscar.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1026, 176, 36, 48));

        cmbEspecie.setBackground(new java.awt.Color(204, 204, 204));
        cmbEspecie.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        cmbEspecie.setForeground(new java.awt.Color(0, 0, 0));
        cmbEspecie.setToolTipText("Especie");
        cmbEspecie.setFocusable(false);
        cmbEspecie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEspecieActionPerformed(evt);
            }
        });
        add(cmbEspecie, new org.netbeans.lib.awtextra.AbsoluteConstraints(344, 176, 202, 48));

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
        add(cmbEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(564, 176, 190, 48));

        txtBusqueda.setToolTipText("Busca por nombre");
        add(txtBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(772, 176, 242, 48));

        btnCrearMascota.setText("Nueva mascota");
        btnCrearMascota.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        add(btnCrearMascota, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 176, 158, 48));
    }// </editor-fold>//GEN-END:initComponents

    private void cmbEspecieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEspecieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbEspecieActionPerformed

    private void cmbEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbEstadoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.stackmasters.adoptaanimales.view.impl.swing.SquaredButton btnBuscar;
    private com.stackmasters.adoptaanimales.view.impl.swing.SquaredButton btnCrearMascota;
    private com.stackmasters.adoptaanimales.view.impl.complement.dashboard.Card card1;
    private com.stackmasters.adoptaanimales.view.impl.complement.dashboard.Card card2;
    private com.stackmasters.adoptaanimales.view.impl.complement.dashboard.Card card3;
    private javax.swing.JComboBox<Especie> cmbEspecie;
    private javax.swing.JComboBox<EstadoMascota> cmbEstado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.stackmasters.adoptaanimales.view.impl.swing.table.Table table1;
    private javax.swing.JTextField txtBusqueda;
    // End of variables declaration//GEN-END:variables
}
