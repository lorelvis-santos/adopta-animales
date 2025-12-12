
package com.stackmasters.adoptaanimales.view.impl.complement;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;

/**
 *
 * @author Vicma
 */
public class Message extends javax.swing.JPanel {

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    private MessageType messageType = MessageType.SUCCESS;
    private boolean show;

    public Message() {
        initComponents();
        setOpaque(false);
        setVisible(false);
    }

    public void showMessage(MessageType messageType, String message) {
       
        this.messageType = messageType;
        eMessage.setText(message);
        if (messageType == MessageType.SUCCESS) {
            eMessage.setIcon(new ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/success.png")));
        } else {
            eMessage.setIcon(new ImageIcon(getClass().getResource("/com/stackmasters/adoptaanimales/view/impl/icon/error.png")));
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        eMessage = new javax.swing.JLabel();

        eMessage.setBackground(new java.awt.Color(0, 0, 0));
        eMessage.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        eMessage.setForeground(new java.awt.Color(255, 255, 255));
        eMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eMessage.setText("Message");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(eMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(eMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

     

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        if (messageType == MessageType.SUCCESS) {
            g2.setColor(new Color(119, 221, 119));
        } else {
            g2.setColor(new Color(255, 105, 97));
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setComposite(AlphaComposite.SrcOver);
        g2.setColor(new Color(245, 245, 245));
        g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        super.paintComponent(grphcs);
    }

    public static enum MessageType {
        SUCCESS, ERROR
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel eMessage;
    // End of variables declaration//GEN-END:variables
}
