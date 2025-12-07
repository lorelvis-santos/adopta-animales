package com.stackmasters.adoptaanimales;

import com.stackmasters.adoptaanimales.service.impl.SolicitudServiceImpl;
import com.stackmasters.adoptaanimales.repository.*;
import com.stackmasters.adoptaanimales.dto.*;
import com.stackmasters.adoptaanimales.model.*;
import com.stackmasters.adoptaanimales.model.SolicitudAdopcion.EstadoSolicitud;

import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 *
 * @author Sky L.
 */
public class PruebaSolicitudService {

    public static void main(String[] args) {

        // ⚠️ SOLO PARA DEBUG LOCAL - NO SUBIR A PRODUCCIÓN
        try (java.sql.Connection con = java.sql.DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Adopcion_animales?serverTimezone=UTC",
                "root",
                "Victorino"
        )) {
            var rs = con.createStatement().executeQuery("SELECT DATABASE()");
            if (rs.next()) {
                System.out.println("DEBUG → NetBeans está usando la BD: " + rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ==========================
        // Inicialización
        // ==========================
        MascotaRepository mascotaRepo = new MascotaRepository();
        AdoptanteRepository adoptanteRepo = new AdoptanteRepository();
        SolicitudAdopcionRepository solicitudRepo = new SolicitudAdopcionRepository();
        CitaRepository citaRepo = new CitaRepository();

        SolicitudServiceImpl service = new SolicitudServiceImpl(
                mascotaRepo, adoptanteRepo, solicitudRepo, citaRepo
        );

        // ==========================
        // PRUEBA PRINCIPAL
        // ==========================
        try {
            System.out.println("==== CREAR SOLICITUD ====");

            CrearAdoptanteDTO dto = new CrearAdoptanteDTO(
                    "Sky",                     // nombre
                    "Andújar",                 // apellido
                    LocalDate.of(2005, 1, 1),  // fecha nacimiento
                    "8090000000",              // teléfono
                    "Mi dirección",           // dirección
                    "sky@example.com"         // correo
            );

            int mascotaId = 11; // Debe existir y estar EnAlbergue

            // Crear solicitud
            SolicitudAdopcion solicitud = service.crear(mascotaId, dto);

            // Validar creación
            if (solicitud == null || solicitud.getIdSolicitud() <= 0) {
                System.out.println("❌ ERROR: La solicitud no se creó correctamente.");
                return;
            }

            System.out.println("✅ Solicitud creada: ID = " + solicitud.getIdSolicitud());

            // ==========================
            // Aprobar solicitud
            // ==========================
            System.out.println("==== APROBAR SOLICITUD ====");

            boolean estadoActualizado = service.actualizarEstado(
                    solicitud.getIdSolicitud(),
                    EstadoSolicitud.Aprobada,
                    null
            );

            System.out.println("✅ Solicitud aprobada: " + estadoActualizado);

            // ==========================
            // Programar cita
            // ==========================
            System.out.println("==== PROGRAMAR CITA ====");

            CitaDTO citaDto = new CitaDTO(
                    LocalDateTime.now().plusDays(1),   // fecha y hora
                    Cita.EstadoCita.Programada,        // estado
                    "Primera visita",                  // notas
                    solicitud.getIdSolicitud(),        // solicitudId requerido por DTO
                    1                                  // albergueId
            );

            Cita cita = service.programarCita(
                    solicitud.getIdSolicitud(),
                    citaDto
            );

            System.out.println("✅ Cita creada: ID = " + cita.getIdCita());

            // ==========================
            // Consultar cita
            // ==========================
            System.out.println("==== OBTENER CITA ====");

            Cita citaObtenida = service.obtenerCita(solicitud.getIdSolicitud());
            System.out.println("✅ Cita encontrada → Estado: " + citaObtenida.getEstadoCita());

            // ==========================
            // Final
            // ==========================
            System.out.println("✅ Prueba ejecutada correctamente. Flujo completo validado.");

        } catch (Exception e) {
            System.out.println("❌ Hubo un error en la prueba, algo no salió como debería.");
            e.printStackTrace();
        }
    }
}
