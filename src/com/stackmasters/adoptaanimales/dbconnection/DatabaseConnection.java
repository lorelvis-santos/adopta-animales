package com.stackmasters.adoptaanimales.dbconnection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Bianca Parra
 */
public class DatabaseConnection {
    
    private static Connection conexion = null; 
    private static String url;
    private static String user;
    private static String password;
    
    //Constructor privado
    private DatabaseConnection () {}
    
    //Metodo para cargar las propiedades desde el archivo .properties
    private static void loadProperties() throws SQLException{
        
        Properties propiedades = new Properties();
        
        try(InputStream rutaPropiedades = DatabaseConnection.class
            .getClassLoader().getResourceAsStream("config.properties")){
            
            if(rutaPropiedades==null){
                throw new SQLException("No se encontró el archivo con las propiedades"); 
            }
        
        //Cargar las credenciales de la bd mediante el archivo .properties
            propiedades.load(rutaPropiedades);
            url = propiedades.getProperty("db.url");
            user =  propiedades.getProperty("db.user");
            password = propiedades.getProperty("db.password");
            
        }catch(IOException e){ //Excepcion que indica que hubo un error con una operacion de entrada/salida
            throw new RuntimeException("No se pudo cargar el archivo con las credenciales", e);
        }
    }
    
    //Metodo para obtener la conexion 
    public static Connection getConexion() {
       try { 
                if (conexion == null || conexion.isClosed()) {
                    conectar();
            }
            return conexion;
    } catch(SQLException e ){ 
        throw new RuntimeException("No se pudo obtener la conexion a la base de datos",e);
    }
}
    
    //Metodo para cerrar la conexion
    public static void cerrarConexion() {
        
         if (conexion != null ) {
        try {
              conexion.close();
            
        }catch (SQLException e) {
            throw new RuntimeException("No se pudo cerrar la conexion a la base de datos", e);
        }
    }
}
    
    //Metodo para iniciar la conexion 
    private static void conectar() throws SQLException {
        
        if(url == null || user == null || password == null){
            loadProperties();
        }
        
        try {
            conexion = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
                throw new RuntimeException("No se pudo conectar a la base de datos", e);
        }
    }
}