package com.stackmasters.adoptaanimales.dbconnection;

import java.sql.*;

/**
 *
 * @author Bianca Parra
 */
public class DatabaseConnection {
    final static String url = "jdbc:mysql://localhost:3306/Adopcion_animales";
    final static String user = "root";
    final static String password = "admin";
    static Connection conexion = null;
    
    private DatabaseConnection () {}
    
    public static Connection getConexion() {
       try {
       
       
        if (conexion == null || conexion.isClosed()) {
            conectar();
        }
    }catch(SQLException e ){ 
            System.out.println("Error: "+e.getMessage());
    
}
       return conexion;
    }
    
    public static void cerrarConexion() {
        try {
            if (conexion != null ) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private static void conectar() {
        try {
            conexion = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
