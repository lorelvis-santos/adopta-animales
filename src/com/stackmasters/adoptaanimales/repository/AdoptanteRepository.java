package com.stackmasters.adoptaanimales.repository;

import com.stackmasters.adoptaanimales.model.Adoptante;
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
            adoptante.setFechanacimiento(datos.getDate("fecha_nacimiento").toLocalDate());
            adoptante.setTelefono(datos.getString("telefono"));
            adoptante.setDireccion(datos.getString("direccion"));
            adoptante.setProvinciaId(datos.getInt("provincia_id"));
            adoptante.setMunicipioId(datos.getInt("municipio_id"));
            
            return adoptante;
            
        }
        
       //Insertar adoptante
      public boolean insertAdoptante(Adoptante adoptante){
            String sql = "INSERT INTO "+getTableName() + " (nombre, apellido, fecha_nacimiento, telefono, direccion, provincia_id, municipio_id)"
              + " VALUES (?,?,?,?,?,?,?)";
            return insert(sql, adoptante.getNombre(),
                                adoptante.getApellido(),
                                adoptante.getFechanacimiento(),
                                adoptante.getTelefono(),
                                adoptante.getDireccion(),
                                adoptante.getProvinciaId(),
                                adoptante.getMunicipioId());         
      }
      
      //Actualizar adoptante
      public boolean updateAdoptante(Adoptante adoptante, int idAdoptante){
            String sql = "UPDATE "+getTableName() + " SET nombre = ?, apellido = ?, fecha_nacimiento = ?, telefono = ?, direccion = ?, "
                    + "provincia_id = ?, municipio_id = ? "
                    + " WHERE " + getPk()+ " = ?";
            return update(sql, adoptante.getNombre(),
                                  adoptante.getApellido(),
                                  adoptante.getFechanacimiento(),
                                  adoptante.getTelefono(),
                                  adoptante.getDireccion(),
                                  adoptante.getProvinciaId(),
                                  adoptante.getMunicipioId(),
                                  idAdoptante);         
            }
}