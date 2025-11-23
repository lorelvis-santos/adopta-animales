package com.stackmasters.adoptaanimales.repository;

import com.stackmasters.adoptaanimales.model.Veterinaria;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Bianca Parra
 */
public class VeterinariaRepository extends BaseRepository<Veterinaria> {
    
    @Override
    protected String getTableName(){
        return "Veterinaria";
    }
    
    @Override
    protected String getPk(){
        return "id_veterinaria";
    }
    
    @Override
    protected Veterinaria mapearTabla(ResultSet datos) throws SQLException{
        Veterinaria veterinaria = new Veterinaria();
        
        veterinaria.setIdVeterinaria(datos.getInt("id_veterinaria"));
        veterinaria.setNombre(datos.getString("nombre"));
        veterinaria.setProvinciaId(datos.getInt("provincica_id"));
        veterinaria.setMunicipioId(datos.getInt("municipio_id"));
        veterinaria.setDireccion(datos.getString("direccion"));
        veterinaria.setCorreo(datos.getString("correo"));
        veterinaria.setContraseña(datos.getString("contraseña"));
        veterinaria.setTelefono(datos.getString("telefono"));
        veterinaria.setFechaCreacion(datos.getDate("creado_en").toLocalDate());
        
        return veterinaria;
    }
    
    //Buscar veterinaria por provincia
    public List<Veterinaria> buscarPorProvincia(int idProvincia) throws SQLException{
    String sql = "SELECT * FROM " + getTableName() +" WHERE provincia_id = ?";
    return executeSelect(sql, idProvincia);
    }
    
    //Buscar veterinaria por municipio 
    public List<Veterinaria> buscarPorMunicipio(int idMunicipio) throws SQLException{
    String sql = "SELECT * FROM " + getTableName() +" WHERE municipio_id = ?";
    return executeSelect(sql, idMunicipio);
    }
     
     //Buscar veterinaria por correo
   public Veterinaria buscarPorCorreo(String correo) throws SQLException {
   String sql = "SELECT * FROM " + getTableName() + " WHERE correo = ?";
   List<Veterinaria> lista = executeSelect(sql, correo);

        if(lista.isEmpty()){
        return null;
        }else{
        return lista.get(0);
        }
    }
    
}
