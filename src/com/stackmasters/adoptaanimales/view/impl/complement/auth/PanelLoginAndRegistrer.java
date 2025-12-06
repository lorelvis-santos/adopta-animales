package com.stackmasters.adoptaanimales.view.impl.complement.auth;

import com.stackmasters.adoptaanimales.view.impl.swing.MyTextField;
import com.stackmasters.adoptaanimales.view.impl.swing.Button;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import com.stackmasters.adoptaanimales.view.impl.swing.MyPasswordField;
import com.stackmasters.adoptaanimales.view.impl.swing.SquaredButton;

/**
 * PanelLoginAndRegistrer
 * ----------------------
 * Este componente es un panel que contiene dos formularios:
 *  - Un formulario de Registro (Register)
 *  - Un formulario de Login (Login)
 *
 * Usa un CardLayout (por medio de JLayeredPane) para mostrar
 * uno u otro formulario, y recibe por constructor los
 * ActionListener que manejarán los eventos de:
 *  - Botón "Crear" (registro)
 *  - Botón "Entrar" (login)
 */



public class PanelLoginAndRegistrer extends javax.swing.JLayeredPane {

    private MyTextField txtUser;
    private MyTextField txtGmail;
    private MyPasswordField txtPass;
    private SquaredButton cmd;
    
    // Panel que contiene el formulario de inicio de sesión
    public PanelLoginAndRegistrer() {
        initComponents();// inicializa los paneles Login y Register y el layout principal.      
        initLogin();
        Login.setVisible(true);
        
    }
    
    // Panel que contiene el formulario de registro
   
    
    // Panel que contiene el formulario de inicio de sesión
    private void initLogin() {
        // Mantenemos la estructura de centrado vertical y horizontal
        Login.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]30[]10[]push"));

        // --- TÍTULO ---
        JLabel label = new JLabel("Iniciar Sesión");
        label.setFont(new Font("sansserif", 1, 36));
        label.setForeground(new Color(0, 0, 0));
        Login.add(label);
        
        // Creamos una fuente común para los inputs (Tamaño 16 queda perfecto en altura 45px)
        Font fontInput = new Font("sansserif", 0, 16);

        // --- CAMPO GMAIL ---
        txtGmail = new MyTextField();
        txtGmail.setPrefixIcon(new ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/patas.png")));
        txtGmail.setHint("Gmail");
        txtGmail.setFont(fontInput);
        Login.add(txtGmail, "w 60%, h 45!"); 

        // --- CAMPO PASSWORD ---
        txtPass = new MyPasswordField();
        txtPass.setPrefixIcon(new ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/patas.png")));
        txtPass.setHint("Contraseña");
        txtPass.setFont(fontInput);
        Login.add(txtPass, "w 60%, h 45!");

        // --- BOTÓN ENTRAR ---
        cmd = new SquaredButton();
        cmd.setBackground(new Color(79, 172, 254));
        cmd.setForeground(new Color(255, 255, 255, 255));
        // CAMBIO: Fuente 16 y en negrita (Bold = 1) para que el botón destaque
        cmd.setFont(new Font("sansserif", 1, 16));
        cmd.setText("Entrar");
        cmd.setFocusPainted(false);
        cmd.setAlignmentX(500);

        Login.add(cmd,"w 150!,grow 0, align left, h 45!"); 
    }
    
    public void showRegister(boolean show){ //Controla cual formulario se ve.
        
        if(show){
            
            Login.setVisible(true);    
        }
    }
    

    
    public MyTextField getTxtUser() {
        return txtUser;
    }

    public MyTextField getTxtGmail() {
        return txtGmail;
    }

    public MyPasswordField getTxtPass() {
        return txtPass;
    }
    
    public SquaredButton getBtnLogin(){
        return cmd;
    }

    


  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Login = new javax.swing.JPanel();
        Register = new javax.swing.JPanel();

        setLayout(new java.awt.CardLayout());

        Login.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout LoginLayout = new javax.swing.GroupLayout(Login);
        Login.setLayout(LoginLayout);
        LoginLayout.setHorizontalGroup(
            LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 321, Short.MAX_VALUE)
        );
        LoginLayout.setVerticalGroup(
            LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(Login, "card3");

        Register.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout RegisterLayout = new javax.swing.GroupLayout(Register);
        Register.setLayout(RegisterLayout);
        RegisterLayout.setHorizontalGroup(
            RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 321, Short.MAX_VALUE)
        );
        RegisterLayout.setVerticalGroup(
            RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(Register, "card2");
    }// </editor-fold>//GEN-END:initComponents

   
    
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Login;
    private javax.swing.JPanel Register;
    // End of variables declaration//GEN-END:variables
}


 
