package com.stackmasters.adoptaanimales.utils;

import com.stackmasters.adoptaanimales.view.impl.complement.PanelLoading;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class LoadingHandler {

    private static PanelLoading loadingPanel;

    public static void init(JFrame frame) {
        loadingPanel = new PanelLoading();
        // Configuramos el panel como el GlassPane del frame principal
        frame.setGlassPane(loadingPanel); 
    }

    public static void show() {
        if (loadingPanel != null) {
            // Hacemos visible el GlassPane
            loadingPanel.setVisible(true); 
            // Validamos para asegurar que se pinte encima de todo
            loadingPanel.repaint();
        }
    }

    public static void hide() {
        if (loadingPanel != null) {
            loadingPanel.setVisible(false);
        }
    }
    
    // Método auxiliar por si necesitas llamarlo desde un JPanel sin tener la referencia estática a mano
    public static void show(Component context) {
        SwingUtilities.invokeLater(() -> show());
    }
}