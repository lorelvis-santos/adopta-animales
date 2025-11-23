package com.stackmasters.adoptaanimales.repository;

import com.stackmasters.adoptaanimales.model.Publicacion;
import com.stackmasters.adoptaanimales.model.Publicacion.EstadoPublicacion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Bianca Parra
 */
public class PublicacionRepository extends BaseRepository<Publicacion>{
        
    @Override
    protected String getTableName(){
        return "publicacion";    
    }
    
    @Override
    protected String getPk(){
        return "id_publicacion";
    }
    
    @Override
    public Publicacion mapearTabla(ResultSet datos) throws SQLException{
    Publicacion publicacion = new Publicacion();
    
    publicacion.setIdPublicacion(datos.getInt("id_publicacion"));
    publicacion.setTitulo(datos.getString("titulo"));
    publicacion.setDescripcion(datos.getString("descripcion"));
    publicacion.setContadorLikes(datos.getInt("contador_likes"));
    publicacion.setFechaPublicacion(datos.getDate("fecha").toLocalDate());
    publicacion.setEstadoPublicacion(EstadoPublicacion.valueOf(datos.getString("estado")));
    publicacion.setMascotaId(datos.getInt("mascota_id"));
    publicacion.setAlbergueId(datos.getInt("albergue_id"));
    publicacion.setAdminId(datos.getInt("admin_id"));
    
    return publicacion;
    }
    
    //Buscar la publicacion con mas likes
    public List<Publicacion> buscarMasLikes() throws SQLException{
        
        String sql = "SELECT * FROM " + getTableName() +
                     " WHERE contador_likes = (" +
                     "SELECT MAX(contador_likes) FROM " + getTableName() +
                     ")";
        return executeSelect(sql);
    }
    
    //Dar like a una publicacion
    public int darLike(int idPublicacion) throws SQLException{
           String sql = "UPDATE publicacion SET contador_likes = contador_likes + 1 WHERE id_publicacion = ?";
           return executeUpdate(sql, idPublicacion);
    }
    
// Ver publicaciones por albergue


}
    

