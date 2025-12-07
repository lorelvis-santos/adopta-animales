package com.stackmasters.adoptaanimales.repository;

import com.stackmasters.adoptaanimales.model.Mascota;
import com.stackmasters.adoptaanimales.model.Mascota.Especie;
import com.stackmasters.adoptaanimales.model.Mascota.Sexo;
import com.stackmasters.adoptaanimales.model.Mascota.Tamaño;
import com.stackmasters.adoptaanimales.model.RespuestaBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Bianca Parra
 */
public class MascotaRepository extends BaseRepository<Mascota>{
        
    @Override
    protected String getTableName(){
        return "Mascota";
    }
    @Override
    protected String getPk(){
        return  "id_mascota";
    }
    
    @Override
    protected Mascota mapearTabla(ResultSet datos) throws SQLException{
        Mascota mascota = new Mascota();
        
        mascota.setIdMascota(datos.getInt("id_mascota"));
        mascota.setNombre(datos.getString("nombre"));
        mascota.setFechaNacimiento(datos.getDate("fecha_nacimiento").toLocalDate());
        mascota.setRaza(datos.getString("raza"));
        mascota.setTamaño(Tamaño.valueOf(datos.getString("tamaño")));
        mascota.setPeso(datos.getDouble("peso"));
        mascota.setEspecie(Especie.valueOf(datos.getString("especie")));
        mascota.setSexo(Sexo.valueOf(datos.getString("sexo")));
        mascota.setDescripcion(datos.getString("descripcion"));
        mascota.setEstaCastrado(datos.getBoolean("esta_castrado"));
        mascota.setEstaVacunado(datos.getBoolean("esta_vacunado"));
        mascota.setCondicionEspecial(datos.getString("condicion_especial"));
        
        
        String estadoBd = datos.getString("estado");
        Mascota.EstadoMascota estadoEnum = null;
        switch(estadoBd){
            
            case "En albergue" -> estadoEnum =Mascota.EstadoMascota.EnAlbergue;
            case "En proceso de adopción" -> estadoEnum = Mascota.EstadoMascota.EnProcesoDeAdopcion;
            case "Adoptada" -> estadoEnum = Mascota.EstadoMascota.Adoptada;
            default  -> estadoEnum = Mascota.EstadoMascota.EnAlbergue;
        }
        
        mascota.setEstado(estadoEnum);
        mascota.setAlbergueId(datos.getInt("albergue_id"));
        
        return mascota;
    }
    
    //Metodos especiales 
    
    //Buscar mascota por albergue   
    public List<Mascota> buscarPorAlbergue(int albergueId) throws SQLException{
        String sql = "SELECT * FROM "+ getTableName() + " WHERE albergue_id = ?";
        return executeSelect(sql, albergueId);
    }
    
    //Buscar por mascota por raza
    public List<Mascota> buscarPorRaza(String raza) throws SQLException{
        String sql = "SELECT * FROM " + getTableName() + " WHERE raza = ?";
        return executeSelect(sql, raza);
    }
    
    //Buscar mascota por especie
    public List<Mascota> buscarPorEspecie(String especie) throws SQLException{
        String sql = "SELECT * FROM "+ getTableName() + " WHERE especie = ?";
        return executeSelect(sql, especie);
    }
    
    //Buscar mascota por tamaño
    public List<Mascota> buscarPorTamaño(String tamaño) throws SQLException{
        String sql = "SELECT * FROM " +getTableName() +" WHERE tamaño = ?";
        return executeSelect(sql, tamaño);
    }
    
    //Buscar mascota por sexo
    public List<Mascota> buscarPorSexo(String sexo) throws SQLException{
        String sql = "SELECT * FROM "+getTableName()+" WHERE sexo = ?";
        return executeSelect(sql, sexo);
    }
    
    //Buscar mascotas que llegaron al albergue a traves de una veterinaria
    public List<Mascota> buscarPorVeterinaria() throws SQLException{
        String sql = "SELECT * FROM " + getTableName() + " WHERE veterinaria_id IS NOT NULL";
        return executeSelect(sql);
    }
    
    //Buscar mascota por estado (En albergue, En proceso de adopcion o Adoptada )
    public List<Mascota> buscarPorEstado(Mascota.EstadoMascota estado)throws SQLException{
        String sql = "SELECT * FROM " + getTableName() + " WHERE estado = ?";
        return executeSelect(sql, estado.toString());
    }   
    
    
   //insertar mascota 
    public RespuestaBD insertMascota(Mascota mascota){
        String sql = "INSERT INTO " + getTableName() + 
        " (nombre, fecha_nacimiento, raza, tamaño, peso, especie, sexo, descripcion, esta_castrado, esta_vacunado, condicion_especial, estado, albergue_id) " +
        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return insert(sql, mascota.getNombre(),
                    mascota.getFechaNacimiento(),
                    mascota.getRaza(),
                    mascota.getTamaño().db(),
                    mascota.getPeso(),
                    mascota.getEspecie().db(),
                    mascota.getSexo().db(),
                    mascota.getDescripcion(),
                    mascota.isEstaCastrado(),
                    mascota.isEstaVacunado(),
                    mascota.getCondicionEspecial(),
                    mascota.getEstado().db(),
                    mascota.getAlbergueId());
        }
    
    //Actualizar mascota
    public boolean updateMascota(Mascota mascota, int idMascota){
        String sql = "UPDATE  " +getTableName()+ "  SET nombre = ?, fecha_nacimiento = ?, raza = ?, tamaño = ?, peso = ?,"
                + "especie = ?, sexo = ?, descripcion = ?, esta_castrado = ?, esta_vacunado= ?, condicion_especial = ?, estado = ?, albergue_id = ? "
                + " WHERE " +getPk()+" = ?";
        return update(sql, mascota.getNombre(),
                    mascota.getFechaNacimiento(),
                    mascota.getRaza(),
                    mascota.getTamaño().db(),
                    mascota.getPeso(),
                    mascota.getEspecie().db(),
                    mascota.getSexo().db(),
                    mascota.getDescripcion(),
                    mascota.isEstaCastrado(),
                    mascota.isEstaVacunado(),
                    mascota.getCondicionEspecial(),
                    mascota.getEstado().db(),
                    mascota.getAlbergueId(),
                    idMascota);
        }
}
