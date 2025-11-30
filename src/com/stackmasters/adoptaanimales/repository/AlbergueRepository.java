package com.stackmasters.adoptaanimales.repository;

import com.stackmasters.adoptaanimales.model.Albergue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Bianca Parra
 */
public class AlbergueRepository extends BaseRepository<Albergue> {
        
    @Override
    protected String getTableName(){
        return "albergue";    
    }
    
    @Override
    protected String getPk(){
        return "id_albergue";
    }
    
    @Override
    protected Albergue mapearTabla(ResultSet datos) throws SQLException{
        Albergue albergue = new Albergue();
        
        albergue.setIdAlbergue(datos.getInt("id_albergue"));
        albergue.setNombre(datos.getString("nombre"));
        albergue.setProvinciaId(datos.getInt("provincia_id"));
        albergue.setMunicipioId(datos.getInt("municipio_id"));
        albergue.setDireccion(datos.getString("direccion"));
        albergue.setCorreo(datos.getString("correo"));
        albergue.setTelefono(datos.getString("telefono"));
        
        return albergue;  
        }
    
    //Busca albergue por provincia
    public List<Albergue> buscarPorProvincia(int provinciaId) throws SQLException{
        String sql=  "SELECT * FROM "+ getTableName() + " Where provincia_id = ?";
         return executeSelect(sql, provinciaId);
    }
    
    
    //Busca albergue por municipio
    public List<Albergue> buscarPorMunicipio(int municipioId) throws SQLException{    
        String sql=  "SELECT * FROM "+ getTableName() + " Where municipio_id = ?";
         return executeSelect(sql, municipioId);
    }
    
    // Ver publicaciones por albergue
    public List<Albergue> publicacionPorAlbergue(int idAlbergue ) throws SQLException{
        String sql = "SELECT * FROM " +getTableName() + " WHERE albergue_id = ? ";
        return executeSelect(sql, idAlbergue);
    }
    
    //Buscar albergue por correo
    public Albergue buscarPorCorreo(String correo) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE correo = ?";
        List<Albergue> lista = executeSelect(sql, correo);

            if(lista.isEmpty()){
            return null;
                }else{
            return lista.get(0);
        }
    }
}
