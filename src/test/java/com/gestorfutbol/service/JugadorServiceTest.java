package com.gestorfutbol.service;

import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.Jugador;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JugadorServiceTest {

    @Test
    public void dado_nombreJugador_cuando_esNulo_esVerdadero(){
        JugadorService jugadorService = new JugadorService();

        Jugador jugador = new Jugador("126086307", null, 2, "Delantero", 10);

        assertTrue(jugadorService.validarNombre(jugador.getNombre()));
    }

    @Test
    public void dada_cedulaJugador_cuando_noEstaRepetido_entonces_esNull() {
        JugadorService jugadorService = new JugadorService();

        Jugador jugador = new Jugador("99999", "Cesar", 2, "Delantero", 10);

        assertNull(jugadorService.validarCedula(jugador.getCedula()));
    }

    @Test
    public void dada_posicion_cuando_noEsValida_entonces_esVerdadero() {
        JugadorService jugadorService = new JugadorService();

        Jugador jugador = new Jugador("126086307", "Cesar", 2, "EjemploFalso", 10);

        assertTrue(jugadorService.posicionNoEsValida(jugador.getPosicion()));
    }


    /*
    @Test
    public void dado_jugadorExistente_porCedula_entonces_NoRetornarNulo() {
        JugadorService jugadorService = new JugadorService();

        String cedula = "0503867723";

        Jugador jugador = jugadorService.obtenerJugadorPorCedula(cedula);

        assertNotNull(jugador);
    }
*/


    @Test
    public void dado_dorsal_cuando_estaNoEstaRepetido_entonces_esFalso() {
        JugadorService jugadorService = new JugadorService();

        Jugador jugador = new Jugador("123456789", "Pedro", 2, "Delantero", 1);
        Equipo equipo = new Equipo(1, "Liga de Quito", "Quito", "Rodrig Paz", "LDU");

        assertFalse(jugadorService.validarDorsal(jugador.getDorsal(), equipo));
    }


    @Test
    public void cuando_nombreTieneDigitosCaracteres_entonces_lanzarExcepcion() {
        JugadorService jugadorService = new JugadorService();
        String nombre = "D1niel";

        assertThrows(IllegalArgumentException.class, () -> {
           jugadorService.verificarEstructuraNombre(nombre);
        });
    }

    @Test
    public void cuando_nombreNoTieneCaracteresEspeciales_entonces_nolanzarExcepcion() {
        JugadorService jugadorService = new JugadorService();
        String nombre = "Daniel";
        assertDoesNotThrow(()->{
            jugadorService.verificarEstructuraNombre(nombre);
        });
    }
}
