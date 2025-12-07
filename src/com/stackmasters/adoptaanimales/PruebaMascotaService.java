package com.stackmasters.adoptaanimales;

import com.stackmasters.adoptaanimales.service.impl.MascotaServiceImpl;
import com.stackmasters.adoptaanimales.repository.MascotaRepository;
import com.stackmasters.adoptaanimales.dto.CrearMascotaDTO;
import com.stackmasters.adoptaanimales.model.Mascota.Especie;
import com.stackmasters.adoptaanimales.model.Mascota.Sexo;
import com.stackmasters.adoptaanimales.model.Mascota.Tamaño;
import com.stackmasters.adoptaanimales.model.Mascota;

import java.time.LocalDate;

public class PruebaMascotaService {

    public static void main(String[] args) {

        MascotaRepository mascotaRepo = new MascotaRepository();
        MascotaServiceImpl service = new MascotaServiceImpl(mascotaRepo);

        try {
            System.out.println("==== CREAR MASCOTA ====");

            CrearMascotaDTO dto = new CrearMascotaDTO(
                    "Luna QA",                     // nombre
                    Especie.Gato,                  // especie
                    "Criolla",                     // raza
                    Sexo.Hembra,                   // sexo
                    Tamaño.Mediano,                // tamaño
                    4.5,                           // peso
                    LocalDate.of(2023, 5, 12),     // fecha nacimiento
                    "Gatita de prueba QA",         // descripcion
                    true,                          // estaCastrado
                    false,                         // estaVacunado
                    1                              // albergueId (debe existir)
            );

            Mascota m = service.crear(dto);

            System.out.println("✅ Mascota creada con ID: " + m.getIdMascota());
            System.out.println("Nombre: " + m.getNombre());
            System.out.println("Especie: " + m.getEspecie());
            System.out.println("Estado: " + m.getEstado());

        } catch (Exception e) {
            System.out.println("❌ ERROR EN LA PRUEBA:");
            e.printStackTrace();
        }
    }
}
