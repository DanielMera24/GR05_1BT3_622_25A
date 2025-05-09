package com.gestorfutbol.service;

import com.gestorfutbol.dto.EquipoDTO;
import com.gestorfutbol.dto.JugadorDTO;
import com.gestorfutbol.entity.Equipo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class JugadorServiceTest {


    @Test
    public void dados_datosNulos_enCrearJugador_lanzarExcepcion() {
        EquipoDTO equipo = new EquipoDTO("Barcelona", "Guayas", "Colonnia", "BAR");

        JugadorService jugadorService = new JugadorService();
        JugadorDTO jugadorConNombreNulo = new JugadorDTO(null, 10, equipo);
        // Ejecución y verificación
        assertThrows(IllegalArgumentException.class, () -> {
            jugadorService.crearJugador(jugadorConNombreNulo);
        }, "Debería lanzar IllegalArgumentException cuando el nombre es nulo");
    }

    @Test
    public void dados_datosValidos_enCrearJugador_verificarNoRepeticion() {
        EquipoDTO equipo = new EquipoDTO("Barcelona", "Guayas", "Colonnia", "BAR");
        // Preparar datos
        JugadorService jugadorService = new JugadorService();
        JugadorDTO jugador1 = new JugadorDTO("Juan", 10, equipo);
        JugadorDTO jugador2 = new JugadorDTO("Juan", 10, equipo);
        // Ejecución
        jugadorService.crearJugador(jugador1);
        // Verificación
        assertThrows(IllegalArgumentException.class, () -> {
            jugadorService.crearJugador(jugador2);
        }, "Debería lanzar IllegalArgumentException cuando el jugador ya existe");
    }
    // Add more test methods as needed
}
