package com.stackmasters.adoptaanimales.repository;

import com.stackmasters.adoptaanimales.model.Adoptante;
import com.stackmasters.adoptaanimales.model.RespuestaBD;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Bianca Parra
 */
public class AdoptanteRepository extends BaseRepository<Adoptante> {
    
        @Override
        protected String getTableName(){
            return "Adoptante";
        }
        
        @Override
        protected String getPk(){
            return "id_adoptante";
        }
        
        @Override
        protected Adoptante mapearTabla(ResultSet datos) throws SQLException{
            Adoptante adoptante = new Adoptante();
            
            adoptante.setIdAdoptante(datos.getInt("id_adoptante"));
            adoptante.setNombre(datos.getString("nombre"));
            adoptante.setApellido(datos.getString("apellido"));
            adoptante.setFechaNacimiento(datos.getDate("fecha_nacimiento").toLocalDate());
            adoptante.setTelefono(datos.getString("telefono"));
            adoptante.setDireccion(datos.getString("direccion"));
            
            return adoptante;
            
        }
        
       //Insertar adoptante
      public RespuestaBD insertAdoptante(Adoptante adoptante){
            String sql = "INSERT INTO "+getTableName() + " (nombre, apellido, fecha_nacimiento, telefono, direccion)"
              + " VALUES (?,?,?,?,?)";
            return insert(sql, adoptante.getNombre(),
                                adoptante.getApellido(),
                                adoptante.getFechaNacimiento(),
                                adoptante.getTelefono(),
                                adoptante.getDireccion());     
      }
      
      //Actualizar adoptante
      public boolean updateAdoptante(Adoptante adoptante, int idAdoptante){
            String sql = "UPDATE "+getTableName() + " SET nombre = ?, apellido = ?, fecha_nacimiento = ?, telefono = ?, direccion = ?  "
                    + " WHERE " + getPk()+ " = ?";
            return update(sql, adoptante.getNombre(),
                                  adoptante.getApellido(),
                                  adoptante.getFechaNacimiento(),
                                  adoptante.getTelefono(),
                                  adoptante.getDireccion(),
                                  idAdoptante);         
            }
}