package com.stackmasters.adoptaanimales.repository;

import com.stackmasters.adoptaanimales.dbconnection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bianca Parra
 */
public abstract class BaseRepository <T>{
    
    //Metodo para obtener una conexion a la base de datos
    protected Connection getConnection(){
        return DatabaseConnection.getConexion();
    }
    //Metodo para obtener el nombre de la tabla correspondiente a cada modelo
    protected abstract String getTableName();
    
     //Metodo para obtener el nombre del primary key de cada tabla
    protected abstract String getPk();
    
       // Este método convierte una fila de la tabla (ResultSet)
      // en un objeto T (por ejemplo una Mascota).
      // Cada repositorio concreto implementará su propia forma de mapear.
    protected abstract T mapearTabla(ResultSet datos ) throws SQLException;
    
    //Metodo para obtener todos los registros de una tabla
    public List<T> findAll(){
    List<T> lista = new ArrayList<>();
    
    String sql = "SELECT * FROM " + getTableName();
    
       try(Connection conexion = getConnection();
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultadoConsulta = consulta.executeQuery()){
        
        while(resultadoConsulta.next()){
            lista.add(mapearTabla(resultadoConsulta)); //Aqui cada fila se convierte en un objeto.
        }
        
        }catch(SQLException e){
            System.out.println("Error: "+ e.getMessage());
        }
        return lista;
    }
    
    //Metodo para buscar por id
    public T findById( int id){
        
        String sql = "SELECT * FROM " + getTableName() + " WHERE "+ getPk() +" = ?";
    
        try(Connection conexion = getConnection();
             PreparedStatement consulta = conexion.prepareStatement(sql)){
            
            //Colocar el id en el ? del parametro sql
            consulta.setInt(1,id);
            try( ResultSet resultadoConsulta= consulta.executeQuery()){
                   if(resultadoConsulta.next()){
                         return mapearTabla(resultadoConsulta); //Si existe devuelve la fila como un objeto
                    }
            }
        }catch(SQLException e){

            System.out.println("Error: "+ e.getMessage());
        }
        // Si no existe devuelve null
        return null;
    }
    
    public boolean existsById( int id){
        
            String sql = "SELECT * FROM " + getTableName() + " WHERE "+ getPk() +" = ?";
    
        try(Connection conexion = getConnection();
              PreparedStatement consulta = conexion.prepareStatement(sql)){
        
             //Colocar el id en el ? del parametro sql
            consulta.setInt(1, id);
            
            try(ResultSet resultadoConsulta = consulta.executeQuery()){
                  return resultadoConsulta.next(); //Si el id existe devuelve true
            }
        }catch(SQLException e){
            
            System.out.println("Error: "+ e.getMessage());
        }
        //si el id no existe devuelve falso
        return false;
    }
    
    //Metodo para buscar por nombre
    public List<T> findByName( String nombre){
        List <T> lista = new ArrayList<>();
    
        String sql  = "SELECT * FROM "+ getTableName() + " WHERE nombre LIKE ?";
        try(Connection conexion = getConnection();
            PreparedStatement  consulta = conexion.prepareStatement(sql)){
    
        //Colocar el nombre en el ? de el parametro sql
        consulta.setString(1, "%" + nombre + "%");
        
        //Ejecutar la consulta
        try(ResultSet resultadoConsulta = consulta.executeQuery()){
            while(resultadoConsulta.next()){
                lista.add(mapearTabla(resultadoConsulta)); //Si se encuentra el nombre se guarda en la lista
            }
        }
    }catch(SQLException e){
        System.out.println("Error: "+ e.getMessage());
    }
        //Si no se encuentra se devuelve la lista vacia.
    return lista;
    }
    
    //Metodo para insertar datos
                                                    //Object... parametros indica los valores que van a reemplazar los ? del sql
    public boolean insert(String sql, Object... parametros){
        try(Connection conexion = getConnection();
              PreparedStatement consulta = conexion.prepareStatement(sql )){
        
            for(int i=0 ; i< parametros.length; i++){
                //Aqui se va colocando cada parametro en cada  "?"
                //se usa setObject() para permitir valores de cualquier tipo
                consulta.setObject(i+1, parametros[i]);
            }
            //Se almacena el numero de filas afectadas
                                           // Se usa executeUpdate() para operaciones INSERT/UPDATE/DELETE
                                           //Este metodo modifica la base de datos, obteniendo como resultado el numero de filas afectadas
            return consulta.executeUpdate()>0;
             
        }catch(SQLException e){
        
            System.out.println("Error: "+ e.getMessage());
        }
        return false;
    }
    
    //Metodo para actualizar
    public boolean update(String sql, Object... parametros){
        try(Connection conexion = getConnection();
         PreparedStatement consulta = conexion.prepareStatement(sql)){

       for(int i =0; i <parametros.length; i++){
           consulta.setObject(i+1, parametros[i]);
       }
        return consulta.executeUpdate()>0;
            
        }catch(SQLException e){
            System.out.println("Error: "+ e.getMessage());
        }
        return false;
    }
    
    //Metodo para eliminar por id
    public boolean delete(int id){
        String sql= "DELETE FROM " + getTableName() + " WHERE "+ getPk() +" = ?";
           
        try(Connection conexion  = getConnection();
             PreparedStatement consulta = conexion.prepareStatement(sql)){
            
            consulta.setInt(1,id);   
            return consulta.executeUpdate()>0;
            
        }catch(SQLException e){
            System.out.println("Error: "+ e.getMessage());
        }
        return false;
    }
    
    
    protected List<T> executeSelect(String sql, Object... parametros) throws SQLException{
        List<T> lista = new ArrayList<>();

        try(Connection conexion = getConnection(); 
              PreparedStatement consulta= conexion.prepareStatement(sql)){

            for(int i = 0; i< parametros.length; i++){

                consulta.setObject(i+1, parametros[i]);
            }

            try(ResultSet resultadoConsulta = consulta.executeQuery()){

                while(resultadoConsulta.next()){
                    lista.add(mapearTabla(resultadoConsulta));
                }
            }

        }
        return lista;
    }
    
    //Modificar la base de datos
    protected int executeUpdate(String sql, Object... parametros) throws SQLException {
        try (Connection conexion = getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            for (int i = 0; i < parametros.length; i++) {
                ps.setObject(i + 1, parametros[i]);
            }
            return ps.executeUpdate();
        }
    } 
}
