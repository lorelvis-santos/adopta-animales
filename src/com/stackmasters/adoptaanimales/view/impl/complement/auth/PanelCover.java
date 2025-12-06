/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.stackmasters.adoptaanimales.view.impl.complement.auth;

import com.stackmasters.adoptaanimales.view.impl.swing.ButtonOutLine;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;




public class PanelCover extends javax.swing.JPanel {

   private final DecimalFormat df=new DecimalFormat("##0.###");
   private ActionListener event;
   private MigLayout layout;
   private JLabel title;
   private JLabel descripction;
   private JLabel descripction1;
   private JLabel descripction2;
   private JLabel icon;
   private ButtonOutLine button;
   private boolean isLogin;
    
    public PanelCover() {
        initComponents();
        setOpaque(false);
        layout = new MigLayout("wrap, fill","[center]","push[]25[]10[]25[]push");
        setLayout (layout);
        init();
    }
    
    
    private void init(){
        
           icon=new JLabel();
           java.net.URL imageUrl = getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/petlog.png");
           ImageIcon iconImage = new ImageIcon(imageUrl);
           icon.setIcon(iconImage);
           add(icon);
        
           title=new JLabel("Bienvenido a Adoptar");
           title.setFont(new Font("sansserif",1,30));
           title.setForeground(new Color(255,255,255,255));
           add(title);
           
           descripction=new JLabel("Entra con tu cuenta");
           descripction.setForeground(new Color(255,255,255,255));
           descripction.setFont(new Font("sansserif",5,20));
           add(descripction); 
        
           descripction1=new JLabel("Administra tus mascotas");
           descripction1.setForeground(new Color(255,255,255,255));
           descripction1.setFont(new Font("sansserif",5,20));
           add(descripction1);
           
    
            
    }
          
           
           
    
    

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 321, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics grphcs){
        
         super.paintComponent(grphcs);
        // Llamamos primero al método original de Swing para asegurar
        // que el panel se repinte correctamente (borra artefactos).
    
        
        Graphics2D g2= (Graphics2D) grphcs;
        // Convertimos el objeto Graphics estándar a Graphics2D.
        // Graphics2D permite dibujar degradados, curvas y efectos avanzados.
        
        
        GradientPaint gra=new GradientPaint
        (0,0, new Color(102, 187, 106), // Color en la parte superior del panel
                
        0 , getHeight(),new Color(129, 199, 132) // Color en la parte inferior del panel
        
        );
        // Creamos un degradado vertical usando GradientPaint.
        // El degradado va desde la posición (0,0) hasta (0, getHeight()).
        // Color superior: amarillo muy claro  -> new Color(255, 255, 191)
        // Color inferior: azul grisáceo suave -> new Color(174, 198, 207)
        // Este gradient produce una transición suave entre ambos colores.
        
        
        
        g2.setPaint(gra);
    // Establecemos el degradado como la pintura activa de Graphics2D.
    // Todo lo que se dibuje a partir de ahora lo utilizará.
    
    
    
        g2.fillRect(0,0,getWidth(),getHeight());
        // Rellenamos un rectángulo del tamaño completo del panel.
        // Esto pinta el fondo completo con el degradado vertical.
    }
    
    
    public void addEvent(ActionListener event){
    
        this.event=event;  
    }
    
        

      public void loginLeft(double v) {
          
          v = Double.valueOf(df.format(v));      
          login(true);       
          layout.setComponentConstraints(title, "pad 0 " + v + "% 0 " + v + "%");
          layout.setComponentConstraints(descripction, "pad 0 " + v + "% 0 " + v + "%");
          layout.setComponentConstraints(descripction1, "pad 0 " + v + "% 0 " + v + "%");
      }

      public void loginRight(double v) {
          
          v = Double.valueOf(df.format(v));
          login(true);      
          layout.setComponentConstraints(title, "pad 0 -" + v + "% 0 " + v + "%");
          layout.setComponentConstraints(descripction, "pad 0 -" + v + "% 0 " + v + "%");
          layout.setComponentConstraints(descripction1, "pad 0 -" + v + "% 0 " + v + "%");
      }
    
    private void login(boolean login){
        if (this.isLogin != login){
            if (login){
                title.setText("Bienvenido a Adoptar");
                descripction.setText("Entra con tus datos personales");
                descripction1.setText("Has algo bonito con nosotros");                
                button.setText("Registrate");
            }else{
                
                title.setText("Bienvenido");
                descripction.setText("Para mantenerte en contacto con nosotros");
                descripction1.setText("Registrate con tus datos personales");  
                button.setText("Iniciar sesión");
            }
            
            this.isLogin=login;
            
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}


 
