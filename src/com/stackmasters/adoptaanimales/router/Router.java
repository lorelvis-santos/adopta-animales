package com.stackmasters.adoptaanimales.router;

import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Component;
import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author Lorelvis 
 */
public class Router {
    private final JPanel contenedor;
    private final CardLayout cards;
    private final Map<Ruta, VistaNavegable> vistas = new HashMap<>();
    
    public Router(JPanel contenedor) {
        this.contenedor = contenedor;
        this.cards = (CardLayout) contenedor.getLayout();
    }
    
    public void registrar(Ruta ruta, VistaNavegable vista) {
        contenedor.add((Component) vista, ruta.name());
        vistas.put(ruta, vista);
    }
    
    public void navegar(Ruta ruta, Object parametros) {
        VistaNavegable vista = vistas.get(ruta);
        
        if (vista != null) {
            vista.alMostrar(parametros);
            cards.show(contenedor, ruta.name());
        }
    }
}
