package com.stackmasters.adoptaanimales.view.impl.complement.dashboard;

import com.stackmasters.adoptaanimales.view.impl.swing.MenuButton;
import com.stackmasters.adoptaanimales.view.impl.swing.scrollbar.ScrollBarCustom;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.ImageIcon;
import net.miginfocom.swing.MigLayout;

public class Menu extends javax.swing.JPanel {
    private final MigLayout layout;
    private boolean enableMenu = true;
    private boolean showMenu = true;
    
    public boolean isShowMenu() {
        return showMenu;
    }
    
    public void setEnableMenu(boolean enableMenu) {
        this.enableMenu = enableMenu;
    }

    public void setShowMenu(boolean showMenu) {
        this.showMenu = showMenu;
    }
    
    public MenuButton getBtnInicio() {
        return menuBtnInicio;
    }
    
    public MenuButton getBtnMascotas() {
        return menuBtnMascotas;
    }
    
    public MenuButton getBtnSolicitudes() {
        return menuBtnSolicitudes;
    }
    
    public MenuButton getBtnCerrarSesion() {
        return menuBtnCerrarSesion;
    }

    public Menu() {
        initComponents();
        setOpaque(false);
        layout = new MigLayout("wrap, fillx, insets 0", "[fill]", "[]0[]");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        profile1 = new com.stackmasters.adoptaanimales.view.impl.complement.dashboard.Profile();
        menuBtnInicio = new com.stackmasters.adoptaanimales.view.impl.swing.MenuButton();
        menuBtnCerrarSesion = new com.stackmasters.adoptaanimales.view.impl.swing.MenuButton();
        menuBtnMascotas = new com.stackmasters.adoptaanimales.view.impl.swing.MenuButton();
        menuBtnSolicitudes = new com.stackmasters.adoptaanimales.view.impl.swing.MenuButton();

        profile1.setForeground(new java.awt.Color(255, 255, 255));

        menuBtnInicio.setForeground(new java.awt.Color(255, 255, 255));
        menuBtnInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/home.png"))); // NOI18N
        menuBtnInicio.setText("Inicio");
        menuBtnInicio.setToolTipText("");
        menuBtnInicio.setFocusable(false);
        menuBtnInicio.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        menuBtnInicio.setIconTextGap(20);

        menuBtnCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
        menuBtnCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/logout.png"))); // NOI18N
        menuBtnCerrarSesion.setText("Cerrar sesión");
        menuBtnCerrarSesion.setFocusable(false);
        menuBtnCerrarSesion.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        menuBtnCerrarSesion.setIconTextGap(20);

        menuBtnMascotas.setForeground(new java.awt.Color(255, 255, 255));
        menuBtnMascotas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/cat.png"))); // NOI18N
        menuBtnMascotas.setText("Mascotas");
        menuBtnMascotas.setFocusable(false);
        menuBtnMascotas.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        menuBtnMascotas.setIconTextGap(20);

        menuBtnSolicitudes.setForeground(new java.awt.Color(255, 255, 255));
        menuBtnSolicitudes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/note.png"))); // NOI18N
        menuBtnSolicitudes.setText("Solicitudes");
        menuBtnSolicitudes.setFocusable(false);
        menuBtnSolicitudes.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        menuBtnSolicitudes.setIconTextGap(20);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profile1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, Short.MAX_VALUE)
            .addComponent(menuBtnInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menuBtnMascotas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menuBtnSolicitudes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menuBtnCerrarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(profile1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuBtnInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuBtnMascotas, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuBtnSolicitudes, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuBtnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(341, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gra = new GradientPaint(0, 0, new Color(102, 187, 106), getWidth(), 0, new Color(129, 199, 132));
        g2.setPaint(gra);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(grphcs);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.stackmasters.adoptaanimales.view.impl.swing.MenuButton menuBtnCerrarSesion;
    private com.stackmasters.adoptaanimales.view.impl.swing.MenuButton menuBtnInicio;
    private com.stackmasters.adoptaanimales.view.impl.swing.MenuButton menuBtnMascotas;
    private com.stackmasters.adoptaanimales.view.impl.swing.MenuButton menuBtnSolicitudes;
    private com.stackmasters.adoptaanimales.view.impl.complement.dashboard.Profile profile1;
    // End of variables declaration//GEN-END:variables
}
