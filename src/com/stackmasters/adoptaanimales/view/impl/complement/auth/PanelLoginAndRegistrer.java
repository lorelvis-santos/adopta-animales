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
    private Button cmd;
    
    // Panel que contiene el formulario de inicio de sesión
    public PanelLoginAndRegistrer(ActionListener eventRegister,ActionListener eventLogin) {
        initComponents();// inicializa los paneles Login y Register y el layout principal.
        initRegistrer(eventRegister);
        initLogin(eventLogin);
        Login.setVisible(false);
        Register.setVisible(true);
    }
    
    // Panel que contiene el formulario de registro
    private void initRegistrer(ActionListener eventRegister){
    
        Register.setLayout(new MigLayout("wrap","push[center]push","push[]25[]10[]10[]15[]push")); //Distancia de los componentes.
        // Título del formulario
        JLabel label=new JLabel("Crear una cuenta");
        label.setFont(new Font("sansserif",1,30));
        label.setForeground(new Color(128, 128, 128));
        Register.add(label);
        // Campo de texto para el nombre de usuario
        txtUser = new MyTextField();
        txtUser.setPrefixIcon(new ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/patas.png")));
        txtUser.setHint("Nombre");
        Register.add(txtUser, "w 60%");
        txtGmail = new MyTextField();
        txtGmail.setPrefixIcon(new ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/patas.png")));
        txtGmail.setHint("Gmail");
        Register.add(txtGmail, "w 60%");
        txtPass = new MyPasswordField();
        txtPass.setPrefixIcon(new ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/patas.png")));
        txtPass.setHint("Contraseña");
        Register.add(txtPass, "w 60%");
        cmd= new Button();
        cmd.setBackground(new Color (255, 255, 191));
        cmd.setForeground(new Color (89, 140, 163));
        cmd.addActionListener(eventRegister);
        cmd.setText("Crear");
        cmd.setFocusPainted(false);
        Register.add(cmd,"w 40%, h 40");
        
        
        
    }
    
    // Panel que contiene el formulario de inicio de sesión
    private void initLogin(ActionListener eventLogin){
        Login.setLayout(new MigLayout("wrap","push[center]push","push[]25[]10[]10[]10[]push"));
        // Título del formulario
        JLabel label=new JLabel("Iniciar  Sesión");
        label.setFont(new Font("sansserif",1,30));
        label.setForeground(new Color(128, 128, 128));
        Login.add(label);
        // Campo de texto para el nombre de usuario
        txtGmail = new MyTextField();
        txtGmail.setPrefixIcon(new ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/patas.png")));
        txtGmail.setHint("Gmail");
        Login.add(txtGmail, "w 60%");
        txtPass = new MyPasswordField();
        txtPass.setPrefixIcon(new ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/patas.png")));
        txtPass.setHint("Contraseña");
        Login.add(txtPass, "w 60%");     
        cmd= new Button();
        cmd.setBackground(new Color (255, 255, 191));
        cmd.setForeground(new Color (89, 140, 163));
        cmd.addActionListener(eventLogin);
        cmd.setText("Entrar");
        cmd.setFocusPainted(false);
        Login.add(cmd,"w 40%, h 40");
        
            
    }
    
    public void showRegister(boolean show){ //Controla cual formulario se ve.
        
        if(show){
            Register.setVisible(true);
            Login.setVisible(false);    
            
        }else{
            Register.setVisible(false);
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
    
    public Button getBtnLogin(){
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


 
