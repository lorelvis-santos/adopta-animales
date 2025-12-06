package com.stackmasters.adoptaanimales.view.impl.complement.dashboard;

import java.awt.event.ActionListener;
import net.miginfocom.swing.MigLayout;

public class Header extends javax.swing.JPanel {

    public Header() {
        initComponents();
    }

    public void addMenuEvent(ActionListener event) {
        cmdMenu.addActionListener(event);
    }

    private void initComponents() {
        // Inicializamos los componentes
        cmdMenu = new com.stackmasters.adoptaanimales.view.impl.swing.Button();
        pic = new com.stackmasters.adoptaanimales.view.impl.swing.ImageAvatar();
        lbUserName = new javax.swing.JLabel();
        lbRole = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        // Configuración visual básica
        setBackground(new java.awt.Color(255, 255, 255));
        
        cmdMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/menu.png")));
        cmdMenu.setOpaque(true);
        
        pic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/profile1.jpg")));
        
        lbUserName.setFont(new java.awt.Font("sansserif", 1, 12));
        lbUserName.setForeground(new java.awt.Color(127, 127, 127)); 
        lbUserName.setText("Albergue");

        lbRole.setFont(new java.awt.Font("sansserif", 0, 12));
        lbRole.setForeground(new java.awt.Color(127, 127, 127));
        lbRole.setText("Admin");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        // Usamos MigLayout para el Header.
        // "fillx" = ocupa todo el ancho.
        // "aligny center" = centrar verticalmente todo el contenido automáticamente.
        // "insets 5 15 5 15" = Márgenes externos (Arriba, Izquierda, Abajo, Derecha).
        setLayout(new MigLayout("fillx, aligny center, insets 5 15 5 10", "[]push[]20[right]5[]", "fill"));

        // Agregamos componentes:
        
        // 1. Botón menú a la izquierda
        add(cmdMenu, "w 38!, h 38!"); 
        
        // 2. El "push" empuja todo lo siguiente hacia la derecha (crea el espacio vacío)
        add(new javax.swing.JLabel(), "push");
        // 3. Separador
        add(jSeparator1, "w 8!, h 25!"); 
        
        // 4. Textos (Nombre y Rol) en un panel o celda vertical
        // 'al right' alinea el texto a la derecha.
        add(lbUserName, "split 2, flowy, al right");
        add(lbRole, "al right");

        // 5. Foto de perfil
        add(pic, "w 38!, h 38!");
    }

    // Variables declaration
    private com.stackmasters.adoptaanimales.view.impl.swing.Button cmdMenu;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbRole;
    private javax.swing.JLabel lbUserName;
    private com.stackmasters.adoptaanimales.view.impl.swing.ImageAvatar pic;
}