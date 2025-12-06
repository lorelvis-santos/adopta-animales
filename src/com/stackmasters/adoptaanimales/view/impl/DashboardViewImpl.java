package com.stackmasters.adoptaanimales.view.impl;

import com.stackmasters.adoptaanimales.view.impl.complement.dashboard.Header;
import com.stackmasters.adoptaanimales.view.impl.complement.dashboard.Menu;
import com.stackmasters.adoptaanimales.view.impl.DashboardInicioViewImpl;
import com.stackmasters.adoptaanimales.view.impl.MainForm;
import com.stackmasters.adoptaanimales.view.impl.swing.icon.GoogleMaterialDesignIcons;
import com.stackmasters.adoptaanimales.view.impl.swing.icon.IconFontSwing;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLayeredPane;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import com.stackmasters.adoptaanimales.view.DashboardView;

/**
 *
 * @author Lorelvis Santos
 */
public class DashboardViewImpl extends JLayeredPane implements DashboardView {
    private MigLayout layout;
    private Menu menu;
    private Header header;
    private MainForm main;
    private Animator animator;
    
    private Runnable onIrInicio;
    private Runnable onIrMascotas;
    private Runnable onIrSolicitudes;
    private Runnable onCerrarSesion;
    
    public DashboardViewImpl() {
        //  Init google icon font
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        
        init();
        
        // Asignamos los Runnable
        menu.getBtnInicio().addActionListener(e -> { if (onIrInicio != null) onIrInicio.run(); });
        menu.getBtnMascotas().addActionListener(e -> { if (onIrMascotas != null) onIrMascotas.run(); });
        menu.getBtnSolicitudes().addActionListener(e -> { if (onIrSolicitudes != null) onIrSolicitudes.run(); });
        menu.getBtnCerrarSesion().addActionListener(e -> { if (onCerrarSesion != null) onCerrarSesion.run(); });
    }

    private void init() {
        layout = new MigLayout("fill", "0[]0[100%, fill]0", "0[fill, top]0");
        this.setLayout(layout);
        
        menu = new Menu();
        header = new Header();
        main = new MainForm();
        
        this.add(menu, "w 230!, spany 2");    // Span Y 2cell
        this.add(header, "h 50!, wrap");
        this.add(main, "w 100%, h 100%");
        
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double width;
                if (menu.isShowMenu()) {
                    width = 60 + (170 * (1f - fraction));
                } else {
                    width = 60 + (170 * fraction);
                }
                layout.setComponentConstraints(menu, "w " + width + "!, spany2");
                menu.revalidate();
            }

            @Override
            public void end() {
                menu.setShowMenu(!menu.isShowMenu());
                menu.setEnableMenu(true);
            }

        };
        
        animator = new Animator(500, target);
        animator.setResolution(0);
        animator.setDeceleration(0.5f);
        animator.setAcceleration(0.5f);
        
        header.addMenuEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!animator.isRunning()) {
                    animator.start();
                }
            }
        });
    }
    
    // Implementación de DashboardView.java 
    
    @Override
    public void onIrInicio(Runnable accion){
        this.onIrInicio = accion;      
    }
    
    @Override
    public void onIrMascotas(Runnable accion) {
        this.onIrMascotas = accion;
    }
    
    @Override
    public void onIrSolicitudes(Runnable accion) {
        this.onIrSolicitudes = accion;
    }
    
    @Override
    public void onCerrarSesion(Runnable accion) {
        this.onCerrarSesion = accion;
    }
    
    @Override
    public void alMostrar(Object parametros) {
        if (parametros != null && parametros instanceof Component) {
            main.showForm((Component) parametros);
        } else {
            main.showForm(new DashboardInicioViewImpl());
        }
    }
    
    // -------------------------------------------------------------
}