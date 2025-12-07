package com.stackmasters.adoptaanimales.view.test;

import com.stackmasters.adoptaanimales.view.InicioView;
import com.stackmasters.adoptaanimales.router.VistaNavegable;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author santo
 */
public class InicioViewTest extends JPanel implements InicioView, VistaNavegable {
    private final JLabel lblMensaje = new JLabel("Hola, inicio cargado correctamente");

    public InicioViewTest() {
        setLayout(new GridBagLayout());
        lblMensaje.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(lblMensaje);
    }

    @Override
    public void setMensaje(String mensaje) {
        lblMensaje.setText(mensaje);
    }

    @Override
    public void alMostrar(Object... params) {
        // Si se pasan parámetros, los puedes usar
        if (params.length > 0 && params[0] instanceof String msg) {
            lblMensaje.setText(msg);
        }
    }
}
