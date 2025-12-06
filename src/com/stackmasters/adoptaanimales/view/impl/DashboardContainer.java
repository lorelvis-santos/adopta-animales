package com.stackmasters.adoptaanimales.view.impl;

import com.stackmasters.adoptaanimales.router.VistaNavegable;
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

/**
 *
 * @author Lorelvis Santos
 */
public class DashboardContainer extends JLayeredPane implements VistaNavegable {
    private MigLayout layout;
    private Menu menu;
    private Header header;
    private MainForm main;
    private Animator animator;

    public DashboardContainer() {
        //  Init google icon font
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        init();
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
    
    @Override
    public void alMostrar(Object parametros) {
        if (parametros != null && parametros instanceof Component) {
            main.showForm((Component) parametros);
        } else {
            main.showForm(new DashboardInicioViewImpl());
        }
    }
}