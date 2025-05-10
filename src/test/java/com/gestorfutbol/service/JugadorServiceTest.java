package com.gestorfutbol.service;

import com.gestorfutbol.dao.interfaces.JugadorDAO;
import com.gestorfutbol.entity.Jugador;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class JugadorServiceTest {
    @Test
    public void give_Jugador_when_isRepeated_then_throw_exception() {
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador("126086307", "Cesar", 2, "Delantero", 9));
        jugadores.add(new Jugador("18329323", "Juan", 2, "Delantero", 10));

        JugadorService jugadorService = new JugadorService();

        Jugador jugadorAgregar = new Jugador("126086307", "Cesar", 2, "Delantero", 9);
        assertThrows(IllegalArgumentException.class, () -> {
            jugadorService.validarJugadorRepetido(jugadores, jugadorAgregar);
        }, "Debería lanzar IllegalArgumentException cuando el jugador ya existe");
    }

    @Test
    public void dado_Jugador_cuando_posicionNoExiste_entonces_esFalso(){

        JugadorService jugadorService = new JugadorService();

        String posicion = "gfhdsjakl";

        assertFalse(jugadorService.validarPosicion(posicion));

    }


    @Test
    public void dado_dorsal_cuando_dorsalRepetido_entonces_esFalso() {
        JugadorService jugadorService = new JugadorService();

        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador("126086307", "Cesar", 2, "Delantero", 10));
        jugadores.add(new Jugador("18329323", "Juan", 2, "Delantero", 10));

        Jugador jugador = new Jugador("123456789", "Pedro", 2, "Delantero", 10);




        assertFalse(jugadorService.validarDorsal(jugadores, jugador.getDorsal()));
    }

}
