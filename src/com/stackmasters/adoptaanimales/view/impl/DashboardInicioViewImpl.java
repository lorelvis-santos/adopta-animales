package com.stackmasters.adoptaanimales.view.impl;

import com.stackmasters.adoptaanimales.model.Mascota;
import com.stackmasters.adoptaanimales.model.Mascota.EstadoMascota;
import com.stackmasters.adoptaanimales.model.SolicitudAdopcion.EstadoSolicitud;
import com.stackmasters.adoptaanimales.repository.AdoptanteRepository;
import com.stackmasters.adoptaanimales.repository.CitaRepository;
import com.stackmasters.adoptaanimales.repository.MascotaRepository;
import com.stackmasters.adoptaanimales.repository.SolicitudAdopcionRepository;
import com.stackmasters.adoptaanimales.service.MascotaService;
import com.stackmasters.adoptaanimales.service.SolicitudService;
import com.stackmasters.adoptaanimales.service.impl.MascotaServiceImpl;
import com.stackmasters.adoptaanimales.service.impl.SolicitudServiceImpl;
import com.stackmasters.adoptaanimales.utils.LoadingHandler;
import com.stackmasters.adoptaanimales.view.DashboardInicioView;
import com.stackmasters.adoptaanimales.view.impl.complement.Dialog;
import com.stackmasters.adoptaanimales.view.impl.model.ModelCard;
import com.stackmasters.adoptaanimales.view.impl.model.ModelMascota;
import com.stackmasters.adoptaanimales.view.impl.swing.icon.GoogleMaterialDesignIcons;
import com.stackmasters.adoptaanimales.view.impl.swing.icon.IconFontSwing;
import com.stackmasters.adoptaanimales.view.impl.swing.noticeboard.ModelNoticeBoard;
import com.stackmasters.adoptaanimales.view.impl.swing.table.EventAction;
import com.stackmasters.adoptaanimales.utils.Message;
import com.stackmasters.adoptaanimales.view.impl.complement.Message.MessageType;
import java.awt.Color;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class DashboardInicioViewImpl extends javax.swing.JPanel implements DashboardInicioView {
    private MascotaService mascotaService;
    private SolicitudService solicitudService;
    private Consumer<Integer> onEditar;
    
    public DashboardInicioViewImpl() {
        initComponents();        
        table1.fixTable(jScrollPane1);
        setOpaque(false);
        
        mascotaService = new MascotaServiceImpl(new MascotaRepository());
        solicitudService = new SolicitudServiceImpl(
            new MascotaRepository(),
            new AdoptanteRepository(),
            new SolicitudAdopcionRepository(),
            new CitaRepository()
        );
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
    
    // Implementación de DashboardInicioView
    
    @Override
    public void onEditar(Consumer<Integer> accion) {
        this.onEditar = accion;
    }
    
    /**
     * Muestra las estadísticas generales de la aplicación.
     * (total de mascotas, solicitudes pendientes, mascotas adoptadas)
     */
    @Override
    public void mostrarEstadisticas(int totalMascotas, int totalSolicitudes, int solicitudesPendientes, int mascotasAdoptadas) {
        Icon icon1 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.PETS, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        card1.setData(new ModelCard("Total de mascotas", totalMascotas, 100, icon1));
        Icon icon2 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.BOOK, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        card2.setData(new ModelCard("Solicitudes pendientes", solicitudesPendientes, calcularPorciento(totalSolicitudes, solicitudesPendientes), icon2));
        Icon icon3 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.NATURE_PEOPLE, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        card3.setData(new ModelCard("Mascotas adoptadas", mascotasAdoptadas, calcularPorciento(totalMascotas, mascotasAdoptadas), icon3));
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
        EventAction <ModelMascota> evenAction = new EventAction <ModelMascota> () {
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

         model.addRow(
             new ModelMascota(
                 mascota.getIdMascota(),
                 new ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/profile3.jpg")),
                 mascota.getNombre(),
                 mascota.getSexo().db(),
                 mascota.getRaza(),
                 mascota.isEstaCastrado(),
                 calcularEdad(mascota.getFechaNacimiento()),
                 mascota.getPeso()
             ).toRowTable(evenAction) 
         );
        }
    }
    
    /**
     * Muestra las últimas actividades realizadas en la aplicación.
     * 
     * @param actividades Lista de actividades recientes
     * 
     * to do: preparar una tabla en la bd que guarde el tipo de evento y fecha.
     */
    @Override
    public void mostrarActividadesRecientes(List<String> actividades) {
        // To do: implementar...
        noticeBoard.addDate("04/10/2021");
        noticeBoard.addNoticeBoard(new ModelNoticeBoard(new Color(94, 49, 238), "Hidemode", "Now", "Sets the hide mode for the component. If the hide mode has been specified in the This hide mode can be overridden by the component constraint."));
        noticeBoard.addNoticeBoard(new ModelNoticeBoard(new Color(218, 49, 238), "Tag", "2h ago", "Tags the component with metadata name that can be used by the layout engine. The tag can be used to explain for the layout manager what the components is showing, such as an OK or Cancel button."));
        noticeBoard.addDate("03/10/2021");
        noticeBoard.addNoticeBoard(new ModelNoticeBoard(new Color(32, 171, 43), "Further Reading", "12:30 PM", "There are more information to digest regarding MigLayout. The resources are all available at www.migcomponents.com"));
        noticeBoard.addNoticeBoard(new ModelNoticeBoard(new Color(50, 93, 215), "Span", "10:30 AM", "Spans the current cell (merges) over a number of cells. Practically this means that this cell and the count number of cells will be treated as one cell and the component can use the space that all these cells have."));
        noticeBoard.addNoticeBoard(new ModelNoticeBoard(new Color(27, 188, 204), "Skip ", "9:00 AM", "Skips a number of cells in the flow. This is used to jump over a number of cells before the next free cell is looked for. The skipping is done before this component is put in a cell and thus this cells is affected by it. \"count\" defaults to 1 if not specified."));
        noticeBoard.addNoticeBoard(new ModelNoticeBoard(new Color(238, 46, 57), "Push", "7:15 AM", "Makes the row and/or column that the component is residing in grow with \"weight\". This can be used instead of having a \"grow\" keyword in the column/row constraints."));
        noticeBoard.scrollToTop();
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
            private int totalMascotas, totalSolicitudes, solicitudesPendientes, mascotasAdoptadas;
            private List<Mascota> mascotas;
            private List<String> actividades;

            @Override protected Void doInBackground() {
                try { 
                    // Obtener datos para las tarjetas
                    totalMascotas = mascotaService.totalMascotas();
                    totalSolicitudes = solicitudService.totalSolicitudes(); 
                    solicitudesPendientes = solicitudService.totalSolicitudesPorEstado(EstadoSolicitud.Pendiente);
                    mascotasAdoptadas = mascotaService.totalMascotasPorEstado(EstadoMascota.Adoptada);
                    
                    // Obtener lista de mascotas
                    mascotas = mascotaService.buscar(null);
                    
                    // Obtener lista de actividades, por implementar
                    actividades = new ArrayList<>();
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
                
                mostrarEstadisticas(totalMascotas, totalSolicitudes, solicitudesPendientes, mascotasAdoptadas);
                cargarTablaMascotas(mascotas);
                mostrarActividadesRecientes(actividades);
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
    
    private int calcularPorciento(int cifraTotal, int valor) {
        System.out.println(cifraTotal + " " + valor);
        if (cifraTotal == 0) {
            return 0;
        }
        
        System.out.println(Math.round(valor/cifraTotal*100));
        
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
        jPanel1 = new javax.swing.JPanel();
        noticeBoard = new com.stackmasters.adoptaanimales.view.impl.swing.noticeboard.NoticeBoard();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new com.stackmasters.adoptaanimales.view.impl.swing.table.Table();
        jLabel6 = new javax.swing.JLabel();

        card1.setBackground(new java.awt.Color(79, 172, 254));
        card1.setForeground(new java.awt.Color(255, 255, 255));
        card1.setColorGradient(new java.awt.Color(0, 242, 254));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Inicio");

        card2.setBackground(new java.awt.Color(255, 154, 158));
        card2.setForeground(new java.awt.Color(255, 255, 255));
        card2.setColorGradient(new java.awt.Color(254, 207, 239));

        card3.setBackground(new java.awt.Color(246, 211, 101));
        card3.setColorGradient(new java.awt.Color(253, 160, 133));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(76, 76, 76));
        jLabel2.setText("Solicitudes recientes");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(105, 105, 105));
        jLabel3.setText("Simple Miglayout API Doc");
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        jLabel4.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(noticeBoard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(0, 257, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(15, 15, 15)
                .addComponent(jLabel3)
                .addGap(9, 9, 9)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(noticeBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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
                "Nombre", "Genero", "Raza", "Castrado", "Edad", "Peso", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel6.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(card3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12))))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.stackmasters.adoptaanimales.view.impl.complement.dashboard.Card card1;
    private com.stackmasters.adoptaanimales.view.impl.complement.dashboard.Card card2;
    private com.stackmasters.adoptaanimales.view.impl.complement.dashboard.Card card3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.stackmasters.adoptaanimales.view.impl.swing.noticeboard.NoticeBoard noticeBoard;
    private com.stackmasters.adoptaanimales.view.impl.swing.table.Table table1;
    // End of variables declaration//GEN-END:variables
}
