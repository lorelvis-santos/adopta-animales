package com.stackmasters.adoptaanimales.view.impl.complement.dashboard;

import java.awt.event.ActionListener;
import net.miginfocom.swing.MigLayout;
import com.stackmasters.adoptaanimales.view.impl.swing.Button;
import com.stackmasters.adoptaanimales.view.impl.swing.ImageAvatar;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;

public class Header extends JPanel {

    // Componentes declarados limpiamente
    private Button cmdMenu;
    private ImageAvatar pic;
    private JLabel lbUserName;
    private JLabel lbRole;
    private JSeparator jSeparator1;

    public Header() {
        initComponents();
    }

    public void addMenuEvent(ActionListener event) {
        cmdMenu.addActionListener(event);
    }

    private void initComponents() {
        // Configuración visual básica del Panel
        setBackground(new Color(255, 255, 255));
        
        // --- Inicialización de componentes ---
        
        // Botón Menú
        cmdMenu = new Button();
        cmdMenu.setIcon(new ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/menu.png")));
        cmdMenu.setOpaque(true);
        cmdMenu.setFocusable(false);
        
        // Avatar
        pic = new ImageAvatar();
        pic.setIcon(new ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/profile1.jpg")));
        
        // Labels
        lbUserName = new JLabel("Albergue");
        lbUserName.setFont(new Font("sansserif", Font.BOLD, 12));
        lbUserName.setForeground(new Color(127, 127, 127)); 

        lbRole = new JLabel("Admin");
        lbRole.setFont(new Font("sansserif", Font.PLAIN, 12));
        lbRole.setForeground(new Color(127, 127, 127));

        // Separador
        jSeparator1 = new JSeparator();
        jSeparator1.setOrientation(SwingConstants.VERTICAL);

        // --- Layout (MigLayout) ---
        // fillx: llena a lo ancho
        // aligny center: centra todo verticalmente
        setLayout(new MigLayout("fillx, aligny center, insets 5 15 5 10", "[]push[]20[right]5[]", "fill"));

        // Agregamos componentes
        add(cmdMenu, "w 38!, h 38!"); 
        add(new JLabel(), "push"); // El "push" empuja todo lo demás a la derecha
        add(jSeparator1, "w 8!, h 25!"); 
        
        // Nombre y Rol apilados
        add(lbUserName, "split 2, flowy, al right");
        add(lbRole, "al right");

        add(pic, "w 38!, h 38!");
    }
}