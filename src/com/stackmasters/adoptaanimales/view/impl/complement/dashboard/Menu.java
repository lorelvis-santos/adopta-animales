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

    public Menu() {
        initComponents();
        setOpaque(false);
        layout = new MigLayout("wrap, fillx, insets 0", "[fill]", "[]0[]");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        profile1 = new com.stackmasters.adoptaanimales.view.impl.complement.dashboard.Profile();
        menuButton2 = new com.stackmasters.adoptaanimales.view.impl.swing.MenuButton();
        menuButton3 = new com.stackmasters.adoptaanimales.view.impl.swing.MenuButton();
        menuButton4 = new com.stackmasters.adoptaanimales.view.impl.swing.MenuButton();
        menuButton5 = new com.stackmasters.adoptaanimales.view.impl.swing.MenuButton();

        profile1.setForeground(new java.awt.Color(255, 255, 255));

        menuButton2.setForeground(new java.awt.Color(255, 255, 255));
        menuButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/home.png"))); // NOI18N
        menuButton2.setText("Inicio");
        menuButton2.setToolTipText("");
        menuButton2.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        menuButton2.setIconTextGap(20);
        menuButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButton2ActionPerformed(evt);
            }
        });

        menuButton3.setForeground(new java.awt.Color(255, 255, 255));
        menuButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/logout.png"))); // NOI18N
        menuButton3.setText("Cerrar sesión");
        menuButton3.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        menuButton3.setIconTextGap(20);
        menuButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButton3ActionPerformed(evt);
            }
        });

        menuButton4.setForeground(new java.awt.Color(255, 255, 255));
        menuButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/cat.png"))); // NOI18N
        menuButton4.setText("Mascotas");
        menuButton4.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        menuButton4.setIconTextGap(20);
        menuButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButton4ActionPerformed(evt);
            }
        });

        menuButton5.setForeground(new java.awt.Color(255, 255, 255));
        menuButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/note.png"))); // NOI18N
        menuButton5.setText("Solicitudes");
        menuButton5.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        menuButton5.setIconTextGap(20);
        menuButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profile1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
            .addComponent(menuButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menuButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menuButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menuButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(profile1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(341, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void menuButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuButton2ActionPerformed

    private void menuButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuButton3ActionPerformed

    private void menuButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuButton4ActionPerformed

    private void menuButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuButton5ActionPerformed

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
    private com.stackmasters.adoptaanimales.view.impl.swing.MenuButton menuButton2;
    private com.stackmasters.adoptaanimales.view.impl.swing.MenuButton menuButton3;
    private com.stackmasters.adoptaanimales.view.impl.swing.MenuButton menuButton4;
    private com.stackmasters.adoptaanimales.view.impl.swing.MenuButton menuButton5;
    private com.stackmasters.adoptaanimales.view.impl.complement.dashboard.Profile profile1;
    // End of variables declaration//GEN-END:variables
}
