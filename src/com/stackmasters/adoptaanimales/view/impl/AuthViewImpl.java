
package com.stackmasters.adoptaanimales.view.impl;

import com.stackmasters.adoptaanimales.model.auth.Rol;
import com.stackmasters.adoptaanimales.utils.LoadingHandler;
import com.stackmasters.adoptaanimales.utils.Message;
import com.stackmasters.adoptaanimales.view.AuthView;
import com.stackmasters.adoptaanimales.view.impl.complement.Message.MessageType;
import com.stackmasters.adoptaanimales.view.impl.complement.auth.PanelCover;
import com.stackmasters.adoptaanimales.view.impl.complement.auth.PanelLoginAndRegistrer;
import com.stackmasters.adoptaanimales.view.impl.complement.auth.PanelLoading;
import java.awt.BorderLayout;
import java.text.DecimalFormat;
import javax.swing.JLayeredPane;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;

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
    public void limpiarCampos() {
        logAndReg.getTxtGmail().setText("");
        logAndReg.getTxtPass().setText("");
    }
    
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
    public void setCargando(boolean cargando){
        if (cargando) {
            LoadingHandler.show();
        } else {
            LoadingHandler.hide();
        }
        
        logAndReg.getBtnLogin().setEnabled(!cargando);
    }
    
    @Override
    public void mostrarMensaje(String mensaje, boolean error) {
        if(error) {
            Message.ShowMessage(this, MessageType.ERROR, mensaje);
        } else { 
            Message.ShowMessage(this, MessageType.SUCCESS, mensaje);
        }
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
