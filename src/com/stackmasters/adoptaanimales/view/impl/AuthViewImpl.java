
package com.stackmasters.adoptaanimales.view.impl;

import com.stackmasters.adoptaanimales.model.auth.Rol;
import com.stackmasters.adoptaanimales.router.VistaNavegable;
import com.stackmasters.adoptaanimales.view.AuthView;
import com.stackmasters.adoptaanimales.view.impl.complement.auth.Message;
import com.stackmasters.adoptaanimales.view.impl.complement.auth.PanelCover;
import com.stackmasters.adoptaanimales.view.impl.complement.auth.PanelLoginAndRegistrer;
import com.stackmasters.adoptaanimales.view.impl.complement.auth.PanelLoading;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.JLayeredPane;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

/**
 *
 * @author Vicma
 */
public class AuthViewImpl extends javax.swing.JPanel implements AuthView {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AuthViewImpl.class.getName());
    private MigLayout layout;
    private PanelCover cover;
    private PanelLoading carga;
    private PanelLoginAndRegistrer logAndReg;
    private boolean isLogin;
    private final double addSize=30;
    private final double coverSize=40;
    private final double loginSize=60;
    private final  DecimalFormat df = new DecimalFormat("##0.###"); //Para redondear decimales.
    private Runnable onLogin;
    private Message toast;
    private Animator toastAnimator;
    private javax.swing.Timer hideTimer;
    
    private enum ToastPhase { ENTERING, EXITING }
    private ToastPhase toastPhase;
   
    public AuthViewImpl() {
        initComponents();
        init();
        logAndReg.getBtnLogin().addActionListener(e -> { if (onLogin != null) onLogin.run(); });
    }
    
    //Implementacion de Auth view.
    @Override
    public String getCorreo(){       
       return logAndReg.getTxtGmail().getText();         
    }
    
    @Override    
    public String getContraseña(){  
       return   logAndReg.getTxtPass().getText();
    }
    
    @Override
     
    public Rol getRolSeleccionado(){   
        return null;
    }
    
    @Override
    public void onLogin(Runnable accion){
        
       
        this.onLogin=accion;      
    }
    
    @Override
    public void setCargando(boolean valor){
        carga.setVisible(valor);
        logAndReg.getBtnLogin().setEnabled(!valor);
    }
    
    @Override
    public void mostrarMensaje(String mensaje, boolean error){
        if(error){ShowMessage(Message.MessageType.ERROR, mensaje);}
        else{ShowMessage(Message.MessageType.SUCCESS, mensaje);}
    }
    
    
    
    private void init(){
        layout = new MigLayout("fill, insets 0,");
        cover = new PanelCover(); //Instanciamiento de PanelCover   
        carga = new PanelLoading();//Instanciamiento de Pantalla de carga. 
                
        logAndReg= new PanelLoginAndRegistrer();
        
        
        
        bg.setLayout(layout);
        bg.setLayer(carga, JLayeredPane.POPUP_LAYER);    //Agregando complementos.
        bg.add(carga, "pos 0 0 100% 100%");
        bg.add(cover,"width "+coverSize+ "%, pos 0al 0 n 100%"); //Padding
        bg.add(logAndReg,"width "+loginSize+ "%, pos 1al 0 n 100%");//Padding
        
        
    }
    
    // ===== Método =====
    private void ShowMessage(Message.MessageType type, String text) {
        // 1) Frenar y limpiar lo anterior
        if (toastAnimator != null && toastAnimator.isRunning()) toastAnimator.stop();
        if (hideTimer != null) hideTimer.stop();
        if (toast != null && toast.getParent() == bg) bg.remove(toast);

        // 2) Crear y agregar el único toast
        toast = new Message();
        toast.showMessage(type, text);              // setea icono + texto (Message arranca invisible) 
        bg.add(toast, "pos 0.5al -30", 0);          // 'bg' usa MigLayout posicional 
        toast.setVisible(true);
        bg.revalidate();
        bg.repaint();

        // 3) Animación: sin 'isShow'; controlamos con 'toastPhase'
        toastPhase = ToastPhase.ENTERING;

        TimingTarget target = new TimingTargetAdapter() {
            @Override public void timingEvent(float fraction) {
            if (toast == null || toast.getParent() != bg) return;

            // ENTRADA:    y = 40 * fraction      (de -30 a +10)
            // SALIDA:     y = 40 * (1 - fraction) (de +10 a -30)
            float y = (toastPhase == ToastPhase.ENTERING)
                    ? 40 * fraction
                    : 40 * (1f - fraction);

            ((MigLayout) bg.getLayout())
                .setComponentConstraints(toast, "pos 0.5al " + (int)(y - 30));

            bg.revalidate();
            bg.repaint();
        }

            @Override public void end() {
                if (toast == null) return;

                if (toastPhase == ToastPhase.ENTERING) {
                    // Terminó la ENTRADA: esperar 2s y luego correr SALIDA
                    hideTimer = new javax.swing.Timer(2000, e -> {
                        if (toast != null && !toastAnimator.isRunning()) {
                            toastPhase = ToastPhase.EXITING;
                            toastAnimator.start();     // corre la salida
                        }
                    });
                    hideTimer.setRepeats(false);
                    hideTimer.start();

                } else { // EXITING
                    // Terminó la SALIDA: retirar y limpiar
                    if (toast.getParent() == bg) bg.remove(toast);
                    toast = null;
                    bg.revalidate();
                    bg.repaint();
                }
            }
        };

        toastAnimator = new org.jdesktop.animation.timing.Animator(300, target);
        toastAnimator.setResolution(0);
        toastAnimator.setAcceleration(0.5f);
        toastAnimator.setDeceleration(0.5f);

        // 4) Lanzar ENTRADA una sola vez
        toastAnimator.start();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();

        

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setName(""); // NOI18N
        bg.setOpaque(true);

        setLayout(new BorderLayout());
        add(bg, BorderLayout.CENTER);
       
    }// </editor-fold>//GEN-END:initComponents

    
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    // End of variables declaration//GEN-END:variables
}
