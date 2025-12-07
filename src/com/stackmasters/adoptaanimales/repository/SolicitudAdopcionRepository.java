package com.stackmasters.adoptaanimales.repository;

import com.stackmasters.adoptaanimales.model.RespuestaBD;
import com.stackmasters.adoptaanimales.model.SolicitudAdopcion;
import com.stackmasters.adoptaanimales.model.SolicitudAdopcion.EstadoSolicitud;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Bianca Parra
 */
public class SolicitudAdopcionRepository extends BaseRepository<SolicitudAdopcion> {
    
    @Override
    protected String getTableName(){
        return "SolicitudAdopcion";
    }
    
    @Override
    protected String getPk(){
        return "id_solicitud";
    }
    
    @Override
    protected SolicitudAdopcion mapearTabla(ResultSet datos) throws SQLException{
        SolicitudAdopcion solicitud = new SolicitudAdopcion();

        solicitud.setIdSolicitud(datos.getInt("id_solicitud"));
        solicitud.setEstado(EstadoSolicitud.valueOf(datos.getString("estado")));
        solicitud.setFechaSolicitud(datos.getDate("fecha_solicitud").toLocalDate());
        java.sql.Date fechaRespuesta = datos.getDate("fecha_respuesta");
        solicitud.setFechaRespuesta(fechaRespuesta != null ? fechaRespuesta.toLocalDate() : null);
        solicitud.setMotivoRechazo(datos.getString("motivo_rechazo"));
        solicitud.setAdoptanteId(datos.getInt("adoptante_id"));
        solicitud.setMascotaId(datos.getInt("mascota_id"));

        return solicitud;
    }
    
    //Insertar solicitud
    public RespuestaBD insertSolicitud(SolicitudAdopcion solicitud){
        String sql = "INSERT INTO "+getTableName() +" (estado, fecha_solicitud, fecha_respuesta, motivo_rechazo,"
                + "adoptante_id, mascota_id) VALUES (?,?,?,?,?,?)";
    
        return insert(sql, solicitud.getEstado().db(),
                               solicitud.getFechaSolicitud(),
                               solicitud.getFechaRespuesta(),
                               solicitud.getMotivoRechazo(),
                               solicitud.getAdoptanteId(),
                               solicitud.getMascotaId());
    }
    
    //Actualizar solicitud
    public boolean updateSolicitud(SolicitudAdopcion solicitud, int idSolicitud){
        String sql = "UPDATE "+getTableName() +" SET estado = ? , fecha_solicitud = ?, fecha_respuesta = ?, motivo_rechazo = ?,"
                + "adoptante_id = ?, mascota_id = ? WHERE " + getPk()+ " = ?";
    
        return update(sql, 
                solicitud.getEstado().db(),
                solicitud.getFechaSolicitud(),
                solicitud.getFechaRespuesta(),
                solicitud.getMotivoRechazo(),
                solicitud.getAdoptanteId(),
                solicitud.getMascotaId(),
                idSolicitud
        );
    }
    //Total mascotas por estado en el albergue
    public int totalSolicitudPendiente(String estado){
     String sql = "SELECT COUNT(*) FROM " + getTableName() + " WHERE estado = ? ";
     return count(sql, estado);
    } 
}
