
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
public class AuthViewImpl extends javax.swing.JPanel implements AuthView, VistaNavegable {
    
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
    private javax.swing.Timer hideTimer;
    private Message toast;
   
    
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
        ActionListener eventRegister=new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
               register(); 
            }
        };
        
        ActionListener eventLogin=new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
              login(); 
            }
        };
                
        logAndReg= new PanelLoginAndRegistrer(eventRegister,eventLogin);
        TimingTarget target=new TimingTargetAdapter(){
        
            @Override
            public void timingEvent(float fraction){
                
                double fractionCover;
                double fractionLogin;
                double size=coverSize;
                if(fraction<=0.5f){
                    size += fraction * addSize;
                }else{
                    size +=  addSize - fraction * addSize;
                }
                
            
                if(isLogin){
                    fractionCover= 1f - fraction;
                    fractionLogin= fraction;
                    if(fraction>=0.5f){
                        cover.registerRight(fractionCover * 100);
                    }else{
                        cover.loginRight(fractionLogin * 100);
                    }
                } else{
                
                    fractionCover= fraction;
                    fractionLogin= 1f - fraction;
                      if(fraction <=0.5f){
                          cover.registerLeft(fraction * 100);
                    }else{
                        cover.loginLeft((1f-fraction)*100);
                    }
                    
                }
                
                if(fraction>=0.5f){
                logAndReg.showRegister(isLogin);
                }
                
                fractionCover=Double.valueOf(df.format(fractionCover));
                fractionLogin=Double.valueOf(df.format(fractionLogin));
                layout.setComponentConstraints(cover, "width "+size+"%, pos"+fractionCover+"al 0 n 100%");
                layout.setComponentConstraints(logAndReg, "width "+loginSize+"%, pos"+fractionLogin+"al 0 n 100%");
                bg.revalidate();
            }
            
             @Override
            public void end(){
            
            
                isLogin = !isLogin;
            }
        
        };
        
        Animator animator=new Animator(800,target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0); //Para una aniamcion mas suave
        bg.setLayout(layout);
        bg.setLayer(carga, JLayeredPane.POPUP_LAYER);    //Agregando complementos.
        bg.add(carga, "pos 0 0 100% 100%");
        bg.add(cover,"width "+coverSize+ "%, pos 0al 0 n 100%"); //Padding
        bg.add(logAndReg,"width "+loginSize+ "%, pos 1al 0 n 100%");//Padding
        
        cover.addEvent(new ActionListener(){   
            
            @Override   
            public void actionPerformed(ActionEvent ae){
            
            if(!animator.isRunning()){
            
                animator.start();
            }
        }
        });
    }

    
    private void register(){
        //carga.setVisible(true);
        ShowMessage(Message.MessageType.SUCCESS,"Test_Message");
    }
    
    private void login(){
        //carga.setVisible(true);
        ShowMessage(Message.MessageType.SUCCESS,"Test_Message");
    }
    
 

    private void ShowMessage(Message.MessageType type, String text) {
    // limpiar anteriores
    for (Component c : bg.getComponents()) {
        if (c instanceof Message) bg.remove(c);
    }
    bg.revalidate();
    bg.repaint();

    Message ms = new Message();
    ms.showMessage(type, text);

    // añadir y mostrar arriba
    bg.add(ms, "pos 0.5al -30", 0);
    ms.setVisible(true);
    bg.revalidate();
    bg.repaint();

    // animator de entrada/salida
    TimingTarget target = new TimingTargetAdapter() {
        @Override public void timingEvent(float fraction) {
            float y = ms.isShow() ? 40 * (1f - fraction) : 40 * fraction;
            layout.setComponentConstraints(ms, "pos 0.5al " + (int)(y - 30));
            bg.revalidate();
            bg.repaint();
        }
        @Override public void end() {
            if (ms.isShow()) {
                bg.remove(ms);
                bg.revalidate();
                bg.repaint();
            } else {
                ms.setShow(true);
            }
        }
    };

    final Animator animator = new Animator(300, target);
    animator.setResolution(0);
    animator.setAcceleration(0.5f);
    animator.setDeceleration(0.5f);

    // entrada
    animator.start();

    // salida tras 2s (reemplaza Thread.sleep)
    javax.swing.Timer delay = new javax.swing.Timer(2000, e -> {
        if (!animator.isRunning()) animator.start();
    });
    delay.setRepeats(false);
    delay.start();
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
