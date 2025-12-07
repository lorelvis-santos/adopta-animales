package com.stackmasters.adoptaanimales.service;

import com.stackmasters.adoptaanimales.model.Mascota;
import com.stackmasters.adoptaanimales.dto.CrearMascotaDTO;
import com.stackmasters.adoptaanimales.dto.ActualizarMascotaDTO;
import com.stackmasters.adoptaanimales.dto.FiltroMascotaDTO;
import com.stackmasters.adoptaanimales.exception.DatosInvalidosException;
import java.util.List;

/**
 *
 * @author Lorelvis Santos
 */
public interface MascotaService {
    Mascota crear(CrearMascotaDTO dto) throws DatosInvalidosException;
    
    boolean actualizar(int mascotaId, ActualizarMascotaDTO dto) throws DatosInvalidosException;
    
    boolean eliminar(int mascotaId);
    
    boolean marcarAdoptada(int mascotaId);
    
    Mascota obtenerPorId(int mascotaId);
    
    List<Mascota> buscar(FiltroMascotaDTO filtro);
    
    int totalMascotas();
    
    int totalMascotasPorEstado(Mascota.EstadoMascota estado);
}
