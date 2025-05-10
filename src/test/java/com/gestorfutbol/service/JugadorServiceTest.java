package com.gestorfutbol.service;

import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.Jugador;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JugadorServiceTest {

    @Test
    public void dado_nombre_cuando_EsNulo_esVerdadero(){
        JugadorService jugadorService = new JugadorService();

        Jugador jugador = new Jugador("126086307", null, 2, "Delantero", 10);

        assertTrue(jugadorService.validarNombre(jugador.getNombre()));
    }

    @Test
    public void dada_cedulaJugador_cuando_noEstaRepetido_entonces_esFalso() {
        JugadorService jugadorService = new JugadorService();

        Jugador jugador = new Jugador("183293231", "Cesar", 2, "Delantero", 10);

        assertFalse(jugadorService.validarCedula(jugador.getCedula()));
    }

    @Test
    public void dada_posicion_cuando_noEsValida_entonces_esVerdadero() {
        JugadorService jugadorService = new JugadorService();

        Jugador jugador = new Jugador("126086307", "Cesar", 2, "EjemploFalso", 10);

        assertTrue(jugadorService.posicionNoEsValida(jugador.getPosicion()));
    }




    @Test
    public void dado_dorsal_cuando_estaRepetido_entonces_esVerdadero() {
        JugadorService jugadorService = new JugadorService();

        Jugador jugador = new Jugador("123456789", "Pedro", 2, "Delantero", 10);
        Equipo equipo = new Equipo(1, "Liga de Quito", "Quito", "Rodrig Paz", "LDU");

        assertTrue(jugadorService.validarDorsal(jugador.getDorsal(), equipo));
    }



}
