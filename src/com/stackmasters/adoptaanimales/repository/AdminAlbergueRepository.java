package com.stackmasters.adoptaanimales.repository;

import com.stackmasters.adoptaanimales.model.AdminAlbergue;
import com.stackmasters.adoptaanimales.model.RespuestaBD;
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
    
    @Override
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
    
    //Buscar admin por correo
    public AdminAlbergue buscarPorCorreo(String correo) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE correo = ?";
        List<AdminAlbergue> lista = executeSelect(sql, correo);

            if(lista.isEmpty()){
            return null;
            }else{
            return lista.get(0);
            }
    }
    
    //Insertar admin
    public RespuestaBD insertAdmin(AdminAlbergue admin){
        String sql = "INSERT INTO "+getTableName()+" (nombre, apellido, correo, contraseña, albergue_id) VALUES (?,?,?,?,?)";
        return insert(sql, admin.getNombre(),
                            admin.getApellido(),
                            admin.getCorreo(),
                            admin.getContraseña(),
                            admin.getAlbergueId());
    }
    
    //Actualizar Admin 
     public boolean updateAdmin(AdminAlbergue admin, int idAdmin){
        String sql = "UPDATE "+getTableName()+" SET nombre = ?, apellido = ?, correo = ? , contraseña = ? , albergue_id = ?  WHERE  " +getPk()+ " = ?";
        return update(sql, admin.getNombre(),
                              admin.getApellido(),
                              admin.getCorreo(),
                              admin.getContraseña(),
                              admin.getAlbergueId(),
                              idAdmin);
    }

    public boolean actualizarContraseña(String correo, String nuevaHasheada) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
