package com.stackmasters.adoptaanimales.view.test;

import com.stackmasters.adoptaanimales.model.auth.Rol;
import com.stackmasters.adoptaanimales.router.VistaNavegable;
import com.stackmasters.adoptaanimales.view.AuthView;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AuthViewTest extends JPanel implements AuthView, VistaNavegable {
    private final JTextField txtCorreo = new JTextField(24);
    private final JPasswordField txtPass = new JPasswordField(24);
    private final JButton btnLogin = new JButton("Iniciar sesión");
    private final JLabel lblEstado = new JLabel(" ");
    private Runnable onLogin;

    public AuthViewTest() {
        setLayout(new GridBagLayout());
        var c = new GridBagConstraints();
        
        c.insets = new Insets(6,6,6,6);
        c.gridx=0; c.gridy=0; add(new JLabel("Correo:"), c);
        c.gridx=1; add(txtCorreo, c);
        c.gridx=0; c.gridy=1; add(new JLabel("Contraseña:"), c);
        c.gridx=1; add(txtPass, c);
        c.gridx=1; c.gridy=2; add(btnLogin, c);
        c.gridx=1; c.gridy=3; add(lblEstado, c);

        btnLogin.addActionListener(e -> { if (onLogin != null) onLogin.run(); });
    }

    @Override public String getCorreo() { return txtCorreo.getText().trim(); }
    @Override public String getContraseña() { return new String(txtPass.getPassword()); }
    @Override public Rol getRolSeleccionado() { return null; }
    @Override public void onLogin(Runnable accion) { this.onLogin = accion; }
    @Override public void setCargando(boolean on) { btnLogin.setEnabled(!on); lblEstado.setText(on ? "Cargando..." : " "); }
    @Override public void limpiarContraseña() { txtPass.setText(""); }
    @Override public void mostrarMensaje(String mensaje) { JOptionPane.showMessageDialog(this, mensaje); }
}