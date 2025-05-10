package com.gestorfutbol.service;

import com.gestorfutbol.dto.EquipoDTO;
import com.gestorfutbol.dto.JugadorDTO;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class JugadorServiceTest {
    private static JugadorService jugadorService;

    @Test
    public void dados_datosNulos_enCrearJugador_lanzarExcepcion() {

        JugadorDTO jugadorConNombreNulo = new JugadorDTO("0503867723", null, 0, 4);
        // Ejecución y verificación
        assertThrows(IllegalArgumentException.class, () -> {
            jugadorService.crearJugador(jugadorConNombreNulo);
        }, "Debería lanzar IllegalArgumentException cuando el nombre es nulo");
    }

    @BeforeAll
    public static void setUpClass() {
        jugadorService = new JugadorService(new EquipoService());
    }


    @Test
    public void dados_datosValidos_enCrearJugador_verificarNoRepeticion() {

        // Preparar datos
        JugadorDTO jugador1 = new JugadorDTO("0503867723", "Fernando" , 10, 5);
        JugadorDTO jugador2 = new JugadorDTO("0503867723",  "Fernando" , 10, 5);
        // Ejecución
        jugadorService.crearJugador(jugador1);
        // Verificación
        assertThrows(PersistenceException.class, () -> {
            jugadorService.crearJugador(jugador2);
        }, "Debería lanzar PersistenceException cuando el jugador ya existe");
    }


}
