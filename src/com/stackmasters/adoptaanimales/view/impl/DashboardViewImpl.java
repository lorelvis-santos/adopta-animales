package com.stackmasters.adoptaanimales.view.impl;

import com.stackmasters.adoptaanimales.router.DashboardRuta;
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
import com.stackmasters.adoptaanimales.view.DashboardView;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    
    private final Map<DashboardRuta, VistaNavegable> subVistasRegistradas = new HashMap<>();
    private DashboardRuta subRutaActual;
    
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
    public void alMostrar(Object... parametros) {
        // Validamos la entrada. Si no hay parametros, vamos a Inicio por defecto.
        if (parametros == null || parametros.length == 0) {
            navegarSubRuta(DashboardRuta.INICIO, new Object[0]);
            return;
        }
        
        // El primer parámetro siempre es la subruta en forma de DashboardRuta.
        
        // Declaramos valores iniciales para evitar fallos posteriores
        DashboardRuta subRuta = DashboardRuta.INICIO;
        Object[] datosSubVista = new Object[0];
        
        // Verificamos que el primer parametro sea una sub-ruta.
        if (parametros[0] instanceof DashboardRuta) {
            subRuta = (DashboardRuta) parametros[0];
            
            // Si hay mas datos, los separamos para pasarlos a la nueva vista.
            if (parametros.length > 1) {
                datosSubVista = Arrays.copyOfRange(parametros, 1, parametros.length);
            }
        } else {
            // Si no, vamos a asumir que son datos para la subruta Inicio.
            datosSubVista = parametros;
        }
        
        // Solo renderizar la vista si es diferente a la actual
        if (subRuta == subRutaActual) {
            return;
        }
        
        // Llamamos a la nueva subruta.
        navegarSubRuta(subRuta, datosSubVista);
        subRutaActual = subRuta;
    }
    
    // -------------------------------------------------------------
    
    public void registrarSubVista(DashboardRuta ruta, VistaNavegable vista) {
        subVistasRegistradas.put(ruta, vista);
    }
    
    private void navegarSubRuta(DashboardRuta subRuta, Object[] datos) {
        VistaNavegable vistaDestino = subVistasRegistradas.get(subRuta);
        
        if (vistaDestino == null) {
            System.out.println("Subruta no registrada o nula: " + subRuta);
            // Fallback a inicio si existe
            vistaDestino = subVistasRegistradas.get(DashboardRuta.INICIO);
        }

        if (vistaDestino != null) {
            // 4. Pasamos los datos.
            vistaDestino.alMostrar(datos);
            
            // Renderizamos
            if (vistaDestino instanceof Component) {
                main.showForm((Component) vistaDestino);
            }
        }
    }
}