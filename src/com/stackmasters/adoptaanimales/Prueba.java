package com.stackmasters.adoptaanimales;

import com.stackmasters.adoptaanimales.service.impl.MascotaServiceImpl;
import com.stackmasters.adoptaanimales.service.MascotaService;
import com.stackmasters.adoptaanimales.dto.CrearMascotaDTO;
import com.stackmasters.adoptaanimales.model.Mascota.Especie;
import com.stackmasters.adoptaanimales.model.Mascota.Sexo;
import com.stackmasters.adoptaanimales.model.Mascota.Tamaño;
import com.stackmasters.adoptaanimales.model.Mascota;
import com.stackmasters.adoptaanimales.repository.MascotaRepository;
import java.time.LocalDate;

/**
 *
 * @author Lorelvis Santos
 */
public class Prueba {
    public static void main(String[] args) {
        MascotaService service = new MascotaServiceImpl(new MascotaRepository());

        try {
            CrearMascotaDTO dto = new CrearMascotaDTO(
                "Luna",
                Especie.Gato,
                "Criolla",
                Sexo.Hembra,
                Tamaño.Mediano,
                4.5,
                LocalDate.of(2007, 5, 12),
                "Gata con asma",
                true,
                false,
                1  // <-- ID del albergue (asegúrate que exista en tu BD)
            );
            
            
            Mascota m = service.crear(dto);
                        
            System.out.println("Mascota creada con ID: " + m.getIdMascota());
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

