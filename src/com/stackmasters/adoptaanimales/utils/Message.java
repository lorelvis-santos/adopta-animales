package com.stackmasters.adoptaanimales.utils;

import com.stackmasters.adoptaanimales.view.impl.complement.Message.MessageType;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Message {
    private static com.stackmasters.adoptaanimales.view.impl.complement.Message toast;
    private static Animator toastAnimator;
    private static javax.swing.Timer hideTimer;
    
    public static enum ToastPhase { ENTERING, EXITING }
    private static ToastPhase toastPhase;
    
    public static void ShowMessage(JComponent sourceComponent, MessageType type, String text) {
        
        JRootPane rootPane = SwingUtilities.getRootPane(sourceComponent);
        if (rootPane == null) return;
        
        JLayeredPane layeredPane = rootPane.getLayeredPane();
        
        if (toastAnimator != null && toastAnimator.isRunning()) toastAnimator.stop();
        if (hideTimer != null) hideTimer.stop();
        
        if (toast != null && toast.getParent() == layeredPane) {
             layeredPane.remove(toast);
             layeredPane.repaint();
        }

        toast = new com.stackmasters.adoptaanimales.view.impl.complement.Message();
        toast.showMessage(type, text);
        toast.setVisible(true);
        toast.setOpaque(false);

        // 1. DEFINIMOS LA FUENTE GIGANTE
        Font fontGigante = toast.getFont().deriveFont(Font.BOLD, 16f);

        // 2. APLICAMOS LA FUENTE A LOS LABELS INTERNOS
        aplicarEstilo(toast, fontGigante); 

        // 3. --- CÁLCULO MATEMÁTICO DEL TAMAÑO ---
        // Obtenemos las métricas de la fuente para saber cuánto mide el texto en píxeles
        FontMetrics metrics = toast.getFontMetrics(fontGigante);
        int textWidth = metrics.stringWidth(text);
        
        // Espacio base: 
        // 50px (icono aprox) + 50px (margen izq) + 50px (margen der) = 150px de colchón
        int paddingAncho = 150; 
        int anchoTotal = textWidth + paddingAncho;
        
        // Aseguramos un mínimo de 450px para mensajes cortos
        if (anchoTotal < 450) {
            anchoTotal = 450;
        }

        // Limitamos al ancho de la pantalla para seguridad
        int maxWidth = layeredPane.getWidth() - 50;
        if (anchoTotal > maxWidth) {
            anchoTotal = maxWidth;
        }
        
        // Altura fija generosa para que quepa la letra 18px
        int altoFijo = 75; 

        // APLICAMOS EL TAMAÑO CALCULADO
        toast.setSize(anchoTotal, altoFijo);
        toast.revalidate(); // Obligamos a reacomodar los internos
        // ----------------------------------------
        
        int x = (layeredPane.getWidth() - toast.getWidth()) / 2;
        int startY = -toast.getHeight(); 
        
        toast.setLocation(x, startY);
        
        layeredPane.add(toast, Integer.valueOf(JLayeredPane.DRAG_LAYER));
        layeredPane.repaint();

        toastPhase = ToastPhase.ENTERING;

        TimingTarget target = new TimingTargetAdapter() {
            @Override public void timingEvent(float fraction) {
                if (toast == null || !toast.isDisplayable()) return;

                int endY = 60; 
                int currentY;
                
                if (toastPhase == ToastPhase.ENTERING) {
                     currentY = (int) (startY + ((endY - startY) * fraction));
                } else {
                     currentY = (int) (endY - ((endY - startY) * fraction));
                }

                int currentX = (layeredPane.getWidth() - toast.getWidth()) / 2;
                toast.setLocation(currentX, currentY);
            }

            @Override public void end() {
                if (toast == null) return;
                if (toastPhase == ToastPhase.ENTERING) {
                    hideTimer = new javax.swing.Timer(2500, e -> { 
                        if (toast != null && !toastAnimator.isRunning()) {
                            toastPhase = ToastPhase.EXITING;
                            toastAnimator.start();
                        }
                    });
                    hideTimer.setRepeats(false);
                    hideTimer.start();
                } else { 
                    layeredPane.remove(toast);
                    layeredPane.repaint();
                    toast = null;
                }
            }
        };

        toastAnimator = new org.jdesktop.animation.timing.Animator(300, target);
        toastAnimator.setResolution(0);
        toastAnimator.setAcceleration(0.5f);
        toastAnimator.setDeceleration(0.5f);
        toastAnimator.start();
    }

    private static void aplicarEstilo(Container container, Font font) {
        for (Component c : container.getComponents()) {
            if (c instanceof JLabel) {
                JLabel label = (JLabel) c;
                label.setFont(font); // Aplicamos la fuente que pasamos por parámetro
            } else if (c instanceof Container) {
                aplicarEstilo((Container) c, font);
            }
        }
    }
}