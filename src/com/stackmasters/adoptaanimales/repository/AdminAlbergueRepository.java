package com.stackmasters.adoptaanimales.repository;

import com.stackmasters.adoptaanimales.model.AdminAlbergue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Bianca Parra
 */
public class AdminAlbergueRepository extends BaseRepository<AdminAlbergue> {
    
    @Override
    protected String getTableName(){
        return "AdminAlbergue";
    }
    
    protected String getPk(){
        return "id_admin";
    }
    
    @Override
    protected AdminAlbergue mapearTabla(ResultSet datos) throws SQLException{
    
            AdminAlbergue admin = new AdminAlbergue();
            
            admin.setIdAdmin(datos.getInt("id_admin"));
            admin.setNombre(datos.getString("nombre"));
            admin.setApellido(datos.getString("apellido"));
            admin.setCorreo(datos.getString("correo"));
            admin.setContraseña(datos.getString("contraseña"));
            admin.setAlbergueId(datos.getInt("albergue_id"));
            
            return admin;
    }
    
    //Buscar admins por albergue
    
    public List<AdminAlbergue> buscarPorAlbergue(int idAlbergue) throws SQLException{
    
            String sql = "SELECT * FROM "+ getTableName() + " WHERE albergue_id = ? ";
            return executeSelect(sql, idAlbergue);
    }
}

