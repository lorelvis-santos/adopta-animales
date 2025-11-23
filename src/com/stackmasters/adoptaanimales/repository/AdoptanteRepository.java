package com.stackmasters.adoptaanimales.repository;

import com.stackmasters.adoptaanimales.model.Adoptante;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
            adoptante.setCorreo(datos.getString("correo"));
            adoptante.setContraseña(datos.getString("contraseña"));
            adoptante.setDireccion(datos.getString("direccion"));
            adoptante.setProvinciaId(datos.getInt("provincia_id"));
            adoptante.setMunicipioId(datos.getInt("municipio_id"));
            
            return adoptante;
            
        }
         //Buscar adoptante por correo
        public Adoptante buscarPorCorreo(String correo) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE correo = ?";
        List<Adoptante> lista = executeSelect(sql, correo);

             if(lista.isEmpty()){
             return null;
             }else{
             return lista.get(0);
             }
         }
     
     
}