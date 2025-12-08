package com.stackmasters.adoptaanimales.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FechaUtils {

    // Definimos el formato una sola vez. 
    // "EEEE" = Día semana, "dd" = día mes, "MMMM" = mes texto, "hh:mm a" = hora AM/PM
    private static final DateTimeFormatter FORMATO_BONITO = 
        DateTimeFormatter.ofPattern("EEEE dd 'de' MMMM 'a las' hh:mm a", new Locale("es", "DO"));

    private static final DateTimeFormatter FORMATO_CORTO = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static String formatearLargo(LocalDateTime fecha, String porDefecto) {
        if (fecha == null) {
            return porDefecto;
        }
        // Capitalizamos la primera letra porque Java devuelve "lunes" en minúscula
        String fechaStr = fecha.format(FORMATO_BONITO);
        return fechaStr.substring(0, 1).toUpperCase() + fechaStr.substring(1);
    }
    
    public static String formatearCorto(LocalDateTime fecha, String porDefecto) {
         if (fecha == null) return porDefecto;
         return fecha.format(FORMATO_CORTO);
    }
}