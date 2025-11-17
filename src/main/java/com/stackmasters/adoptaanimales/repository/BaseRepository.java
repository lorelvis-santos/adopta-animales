package main.java.com.stackmasters.adoptaanimales.repository;

import main.java.com.stackmasters.adoptaanimales.dbconnection.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Bianca Parra
 */
public abstract class BaseRepository <T> {
    
    protected static String tableName;
    
    //Metodo para obtener una conexion a la base de datos
    protected Connection getConnection(){
        return DatabaseConnection.getConexion();
    }
    
       // Este método convierte una fila de la tabla (ResultSet)
      // en un objeto T (por ejemplo una Mascota).
      // Cada repositorio concreto implementará su propia forma de mapear.
    protected abstract T mapearTabla(ResultSet datos ) throws SQLException;
    
    //Metodo para obtener todos los registros de una tabla
    protected List<T> findAll(){
    List<T> lista = new ArrayList<>();
    
    String sql = "select * from " + tableName;
    
    try(PreparedStatement consulta = getConnection().prepareStatement(sql)){
    
        ResultSet resultadoConsulta = consulta.executeQuery();
        
        while(resultadoConsulta.next()){
            lista.add(mapearTabla(resultadoConsulta)); //Aqui cada fila se convierte en un objeto.
        }
        
    }catch(SQLException e){
    
        System.out.println("Error: "+ e.getMessage());
    }
    return lista;
    }
    
    //Metodo para buscar por id
    protected T findById(String sql, int id){
    
        try(PreparedStatement consulta = getConnection().prepareStatement(sql)){
            
            //Colocar el id en el ? del parametro sql
            consulta.setInt(1,id); 
            ResultSet resultadoConsulta= consulta.executeQuery();
            
            //Si existe devuelve la fila como un objeto
            if(resultadoConsulta.next()){
            
                return mapearTabla(resultadoConsulta);
            }
        }catch(SQLException e){
        
            System.out.println("Error: "+ e.getMessage());
        }
        // Si no existe devuelve null
        return null;
    }
    
    protected boolean existsById(String sql, int id){
    
        try(PreparedStatement consulta = getConnection().prepareStatement(sql)){
        
             //Colocar el id en el ? del parametro sql
            consulta.setInt(1, id);
            
            ResultSet resultadoConsulta = consulta.executeQuery();
           return resultadoConsulta.next(); //Si el id existe devuelve true
        }catch(SQLException e){
            
            System.out.println("Error: "+ e.getMessage());
                }
        //si el id no existe devuelve falso
        return false;
    }
    
    //Metodo para buscar por nombre
    protected List<T> findByName(String sql, String nombre){
        
    List <T> lista = new ArrayList<>();
    
    try(PreparedStatement  consulta = getConnection().prepareStatement(sql)){
    
        //Colocar el nombre en el ? de el parametro sql
        consulta.setString(1, nombre);
        //Ejecutar la consulta
        ResultSet resultadoConsulta = consulta.executeQuery();
        //Si se encuentra el nombre se guarda en la lista
        while(resultadoConsulta.next()){
            lista.add(mapearTabla(resultadoConsulta));
        }
    }catch(SQLException e){
        System.out.println("Error: "+ e.getMessage());
    }
        //Si no se encuentra se devuelve la lista vacia.
    return lista;
    }
    
    //Metodo para insertar datos
                                                    //Object... parametros indica los valores que van a reemplazar los ? del sql
    protected boolean insert(String sql, Object... parametros){
    
        try(PreparedStatement consulta = getConnection().prepareStatement(sql )){
        
            for(int i=0 ; i< parametros.length; i++){
                //Aqui se va colocando cada parametro en cada  "?"
                //se usa setObject por ser generico, ya que no se conoce el tipo de dato
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
    protected boolean update(String sql, Object... parametros){
     
        try(PreparedStatement consulta = getConnection().prepareStatement(sql)){
            
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
    protected boolean delete(String sql, int id){
     
        try(PreparedStatement consulta = getConnection().prepareStatement(sql)){
            
            consulta.setInt(1,id);   
            return consulta.executeUpdate()>0;
            
        }catch(SQLException e){
            System.out.println("Error: "+ e.getMessage());
        }
        return false;
    }
    
}
