package main.java.com.stackmasters.adoptaanimales.model;

/**
 *
 * @author Bianca Parra
 */
public class AdminAlbergue {
    
    private int idAdmin;
    private String nombre;
    private String apellido;
    private String correo;
    private String contraseña;
    private int albergueId;

    public AdminAlbergue() {
    }

    public AdminAlbergue(int idAdmin, String nombre, String apellido, String correo, String contraseña, int albergueId) {
        this.idAdmin = idAdmin;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contraseña = contraseña;
        this.albergueId = albergueId;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getAlbergueId() {
        return albergueId;
    }

    public void setAlbergueId(int albergueId) {
        this.albergueId = albergueId;
    }
    
    
}
