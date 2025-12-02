package com.stackmasters.adoptaanimales.model;

import java.time.LocalDate;

/**
 *
 * @author Bianca Parra
 */
public class Mascota {
    
    private int idMascota;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String raza;
    private Tamaño tamañoMascota;
    private double peso;
    private Especie especieMascota;
    private String descripcion;
    private boolean estaCastrado;
    private boolean estaVacunado;
    private Sexo sexo;
    private EstadoMascota estado;
    private String condicionEspecial;
    private int albergueId;
    
    //Enums publicos 
    
    //Enum para el tamaño de la mascota
    public enum Tamaño {
    Pequeño,
    Mediano,
    Grande
}
     //Enum  para el sexo de la mascota
     public enum Sexo{
    Macho,
    Hembra
    }
     //Enum para la especie
     public enum Especie {
    Perro,
    Gato
}

    //Enum para el estado de la mascota
     public enum EstadoMascota{
         
    //Asignar un valor mas bonito al enum  para mostrar al usuario
    EnAlbergue("En albergue"),
    EnProcesoDeAdopcion("En proceso de adopción"),
    Adoptada("Adoptada");
    
    //Campo que contiene el texto "bonito" que se mostrara al usuario
    private final String descripcionEstado;
    
    //Constructor del enum que recibe el texto bonito
    EstadoMascota(String descripcion){
        this.descripcionEstado = descripcion;
    }
    
    //Con el metodo toString() se convierte el enum a String
    @Override
    public String toString(){
        return descripcionEstado;
        }
    }
     
    
    public Mascota() {
    }

    public Mascota(int idMascota, String nombre, LocalDate fechaNacimiento, String raza, Tamaño tamañoMascota, double peso, Especie especieMascota, String descripcion, boolean estaCastrado, boolean estaVacunado, Sexo sexo, EstadoMascota estado, String condicionEspecial, int albergueId) {
        this.idMascota = idMascota;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.raza = raza;
        this.tamañoMascota = tamañoMascota;
        this.peso = peso;
        this.especieMascota = especieMascota;
        this.descripcion = descripcion;
        this.estaCastrado = estaCastrado;
        this.estaVacunado = estaVacunado;
        this.sexo = sexo;
        this.estado = estado;
        this.condicionEspecial = condicionEspecial;
        this.albergueId = albergueId;
    }

    

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Tamaño getTamaño() {
        return tamañoMascota;
    }

    public void setTamaño(Tamaño tamaño) {
        this.tamañoMascota = tamaño;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public Especie getEspecie() {
        return especieMascota;
    }

    public void setEspecie(Especie especie) {
        this.especieMascota = especie;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEstaCastrado() {
        return estaCastrado;
    }

    public void setEstaCastrado(boolean estaCastrado) {
        this.estaCastrado = estaCastrado;
    }

    public boolean isEstaVacunado() {
        return estaVacunado;
    }

    public void setEstaVacunado(boolean estaVacunado) {
        this.estaVacunado = estaVacunado;
    }
    //Getters y Setters para cada Enum
    
    //Enum sexo
    public Sexo getSexo(){
        return sexo;
    }

    public void setSexo(Sexo sexoMascota){
        this.sexo= sexoMascota;
    }
    
    //Enum estado
    public EstadoMascota getEstado(){
        return estado;
    }
    
    public void setEstado(EstadoMascota estadoMascota){
        this.estado= estadoMascota;
    }
    
    public String getCondicionEspecial() {
        return condicionEspecial;
    }

    public void setCondicionEspecial(String condicionEspecial) {
        this.condicionEspecial = condicionEspecial;
    }

    public int getAlbergueId() {
        return albergueId;
    }

    public void setAlbergueId(int albergueId) {
        this.albergueId = albergueId;
    }   
}
