package com.stackmasters.adoptaanimales.view.impl.model;

import com.stackmasters.adoptaanimales.utils.FechaUtils;
import com.stackmasters.adoptaanimales.view.impl.swing.table.EventAction;
import com.stackmasters.adoptaanimales.view.impl.swing.table.ModelAction;
import com.stackmasters.adoptaanimales.view.impl.swing.table.ModelProfile;
import javax.swing.Icon;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ModelSolicitudes {
    private int id;
    private Icon icon;        
    private String name;      
    private String adoptante; 
    private String estado;   
    private String fecha;        
    private String cita;      

    public ModelSolicitudes() {
    }

    public ModelSolicitudes(int id, Icon icon, String name, String adoptante, String estado, String fecha, String cita) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.adoptante = adoptante;
        this.estado = estado;
        this.fecha = fecha;
        this.cita = cita;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    // Getters y setters
    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getAdoptante() {
        return adoptante;
    }

    public void setAdoptante(String adoptante) {
        this.adoptante = adoptante;
    }

   
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCita() {
        return cita;
    }

    public void setCita(String cita) {
        this.cita = cita;
    }

    // Lo que se enviará como fila a la tabla
    public Object[] toRowTable(EventAction <ModelSolicitudes> action) {
        return new Object[]{
            new ModelProfile(icon, name),     // Foto + Nombre
            adoptante,
            estado,
            " "+" "+" "+" "+" "+" "+" "+LocalDate.parse(fecha),
            FechaUtils.formatearCorto(
                (cita != null ? LocalDateTime.parse(cita) : null),
                "No agendada"
            ),
            new ModelAction<ModelSolicitudes>(this, action)      // Column acciones
        };
    }
}