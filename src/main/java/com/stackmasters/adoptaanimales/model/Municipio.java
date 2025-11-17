package main.java.com.stackmasters.adoptaanimales.model;

/**
 *
 * @author Bianca Parra
 */
public class Municipio {
    private int idMunicipio;
    private String nombre;
    private int provinciaId;

    public Municipio() {
    }

    public Municipio(int idMunicipio, String nombre, int provinciaId) {
        this.idMunicipio = idMunicipio;
        this.nombre = nombre;
        this.provinciaId = provinciaId;
    }

    public int getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getProvinciaId() {
        return provinciaId;
    }

    public void setProvinciaId(int provinciaId) {
        this.provinciaId = provinciaId;
    }
    
    
}
