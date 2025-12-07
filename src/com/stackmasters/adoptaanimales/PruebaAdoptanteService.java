package com.stackmasters.adoptaanimales;

import com.stackmasters.adoptaanimales.dto.RegistroAdoptanteDTO;
import com.stackmasters.adoptaanimales.exception.DatosInvalidosException;
import com.stackmasters.adoptaanimales.model.Adoptante;
import com.stackmasters.adoptaanimales.service.AdoptanteService;
import com.stackmasters.adoptaanimales.service.impl.AdoptanteServiceImpl;
import com.stackmasters.adoptaanimales.repository.AdoptanteRepository;
import java.time.LocalDate;

/**
 *
 * @author LENOVO
 */

public class PruebaAdoptanteService {
    public static void main(String[] args) {

        AdoptanteRepository repo = new AdoptanteRepository();
        AdoptanteService service = new AdoptanteServiceImpl(repo);
        
      
        // Probar Registro
        RegistroAdoptanteDTO dto = new RegistroAdoptanteDTO(
                "Cielo",
                "Una gente",
                LocalDate.of(2004, 4, 10),
                "8095551234",
                "sky@example.com",   
                "1234",               
                "Calle X #10",
                0,
                0
        );

        try {
            Adoptante nuevo = service.registrar(dto);
            System.out.println("Adoptante registrado correctamente:");
            System.out.println("Nombre: " + nuevo.getNombre());
            System.out.println("Apellido: " + nuevo.getApellido());
            System.out.println("----------------------------------");
        } catch (Exception e) {
            System.out.println("Error registrando adoptante:");
            e.printStackTrace();
        }
        
        //  Probar Adoptante por Id
        Adoptante encontrado = service.obtenerPorId(3);

        if (encontrado != null) {
            System.out.println("Adoptante encontrado:");
            System.out.println("ID: " + encontrado.getIdAdoptante());
            System.out.println("Nombre: " + encontrado.getNombre());
            System.out.println("Apellido: " + encontrado.getApellido());
        } else {
            System.out.println("No se encontró adoptante con ID 1");
        }

        // Probar Actualizar
        if (encontrado != null) {
            encontrado.setTelefono("888888888");
            encontrado.setDireccion("Nueva dirección actualizada");

            try {
                boolean actualizado = service.actualizarPerfil(encontrado);

                if (actualizado) {
                    System.out.println("Adoptante actualizado correctamente");
                } else {
                    System.out.println("No se actualizó el adoptante");
                }

            } catch (DatosInvalidosException e) {
                System.out.println("Error actualizando adoptante:");
                e.printStackTrace();
            }
        }

    }
}
