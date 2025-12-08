package com.stackmasters.adoptaanimales.view.impl.model;

import com.stackmasters.adoptaanimales.view.impl.swing.table.EventAction;
import com.stackmasters.adoptaanimales.view.impl.swing.table.ModelAction;
import com.stackmasters.adoptaanimales.view.impl.swing.table.ModelProfile;
import javax.swing.Icon;
import java.text.DecimalFormat;

public class ModelMascota {
    private int id;
    private Icon icon;        // Foto de la mascota
    private String name;      // Nombre
    private String gender;    // Género
    private String breed;     // Raza
    private boolean castrado; // Castrado o no
    private int edad;         // Edad
    private double peso;      // Peso

    public ModelMascota() {
    }

    public ModelMascota(int id, Icon icon, String name, String gender, String breed, boolean castrado, int edad, double peso) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.gender = gender;
        this.breed = breed;
        this.castrado = castrado;
        this.edad = edad;
        this.peso = peso;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public boolean isCastrado() {
        return castrado;
    }

    public void setCastrado(boolean castrado) {
        this.castrado = castrado;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    // Lo que se enviará como fila a la tabla
    public Object[] toRowTable(EventAction event) {
        return new Object[]{
            new ModelProfile(icon, name),     // Foto + Nombre
            gender,
            breed,
            castrado ? "      "+"Sí" :  "      "+"No",
            " "+" "+" "+" "+" "+" "+" "+edad + " años",
            peso + " kg",
            new ModelAction<ModelMascota>(this, event)      // Column acciones
        };
    }
}