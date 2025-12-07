package com.stackmasters.adoptaanimales.repository;

import com.stackmasters.adoptaanimales.model.Cita;
import com.stackmasters.adoptaanimales.model.RespuestaBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author Bianca Parra
 */
public class CitaRepository extends BaseRepository<Cita> {
    
    @Override
    protected String getTableName(){
    return "Cita";
    }
    
    @Override
    protected String getPk(){
    return "id_cita";
    }
    
    @Override
    protected Cita mapearTabla(ResultSet datos) throws SQLException {
        Cita cita = new Cita();
        cita.setIdCita(datos.getInt("id_cita"));
    
        java.sql.Timestamp ts = datos.getTimestamp("fecha_hora");
        if (ts != null) {
            cita.setFechaHora(ts.toLocalDateTime());
        } else {
            cita.setFechaHora(null);
        }
    
        String estadoStr = datos.getString("estado");
        if (estadoStr != null) {
            cita.setEstadoCita(Cita.EstadoCita.valueOf(estadoStr));
        } else {
            cita.setEstadoCita(null);
        }
    
        cita.setNotas(datos.getString("notas"));
        cita.setSolicitudId(datos.getInt("solicitud_id"));
        cita.setAlbergueId(datos.getInt("albergue_id"));
    
    return cita;
}

    //Insertar cita
    public RespuestaBD insertCita(Cita cita){
        String sql = "INSERT INTO "+getTableName()+ " (fecha_hora, estado, notas, solicitud_id, albergue_id) VALUES (?,?,?,?,?)";
        return insert(sql, cita.getFechaHora(),
                             cita.getEstadoCita().db(),
                             cita.getNotas(),
                             cita.getSolicitudId(),
                             cita.getAlbergueId());
    }
    
    //Actualizar cita
     public boolean updateCita(Cita cita, int idCita ){
        String sql = "UPDATE "+getTableName()+ " SET fecha_hora = ?, estado = ?, notas = ?, solicitud_id = ?, albergue_id = ? WHERE "+getPk()+
                " = ?";
        return update(sql, cita.getFechaHora(),
                             cita.getEstadoCita().db(),
                             cita.getNotas(),
                             cita.getSolicitudId(),
                             cita.getAlbergueId(),
                             idCita);
    }
     
     //Metodo para buscar citas por solicitud de adopcion
     public Cita findBySolicitudId(int idSolicitud){
        try {
            String sql = "SELECT * FROM " + getTableName() + " WHERE solicitud_id = ?";
            List<Cita> lista = executeSelect(sql, idSolicitud);
            return lista.isEmpty() ? null : lista.get(0);
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
