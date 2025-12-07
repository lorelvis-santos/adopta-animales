package com.stackmasters.adoptaanimales;

import com.stackmasters.adoptaanimales.router.*;
import com.stackmasters.adoptaanimales.view.test.*;
import com.stackmasters.adoptaanimales.controller.*;
import com.stackmasters.adoptaanimales.service.impl.*;
import com.stackmasters.adoptaanimales.repository.AdoptanteRepository;
import com.stackmasters.adoptaanimales.repository.AdminAlbergueRepository;
import com.stackmasters.adoptaanimales.security.BCryptPasswordHasher;
import com.stackmasters.adoptaanimales.utils.LoadingHandler;
import com.stackmasters.adoptaanimales.view.impl.AuthViewImpl;
import com.stackmasters.adoptaanimales.view.impl.DashboardViewImpl;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.*;

/**
 *
 * @author Lorelvis Santos
 */
public class App extends JFrame {
    private final JPanel contenedor = new JPanel(new CardLayout());
    private final Router router = new Router(contenedor);
    
    public App() {
        setTitle("Adopta Animales");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1440, 820);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(contenedor, BorderLayout.CENTER);
        
        LoadingHandler.init(this);
        
        // Registro de vistas
        var autenticacion =  new AuthViewImpl();
        var dashboard = new DashboardViewImpl();
        
        router.registrar(Ruta.AUTENTICACION, autenticacion);
        router.registrar(Ruta.PRINCIPAL, dashboard);
        
        // Aquí se crean los controladores
        new AuthController(
            autenticacion,
            new AuthServiceImpl(
                new AdoptanteRepository(),
                new AdminAlbergueRepository(),
                new BCryptPasswordHasher()
            ),  // servicio
            router
        );
        
        new DashboardController(
            dashboard,
            new AuthServiceImpl(
                new AdoptanteRepository(),
                new AdminAlbergueRepository(),
                new BCryptPasswordHasher()
            ),  // servicio auth
            router
        );
        
        router.navegar(Ruta.AUTENTICACION, new Object[0]);
    }
    
    public Router getRouter() { return router; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}
