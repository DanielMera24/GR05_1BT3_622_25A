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
        jugadores.add(new Jugador("Cesar", 2, "Delantero"));
        jugadores.add(new Jugador("Juan", 10, "Defensa"));

        JugadorService jugadorService = new JugadorService();

        Jugador jugadorAgregar = new Jugador("Juan", 2, "Delantero");
        assertThrows(IllegalArgumentException.class, () -> {
            jugadorService.validarJugadorRepetido(jugadores, jugadorAgregar);
        }, "Debería lanzar IllegalArgumentException cuando el jugador ya existe");
    }

    @Test
    public void dado_Jugador_cuando_posicionNoExiste_entonces_esFalso(){

        JugadorService jugadorService = new JugadorService();

        List<String> posicionesValidas = new ArrayList<>();
        posicionesValidas.add("Portero");
        posicionesValidas.add("Defensa");
        posicionesValidas.add("Centrocampista");
        posicionesValidas.add("Delantero");

        String posicion = "gfhdsjakl";
        assertFalse(jugadorService.validarPosicion(posicionesValidas,posicion));
    }


    @Test
    public void dado_dorsal_cuando_dorsalExisteEnElEquipo_entonces_esFalso() {
        JugadorService jugadorService = new JugadorService();

        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador("Cesar", 2, "Delantero"));
        jugadores.add(new Jugador("Juan", 10, "Defensa"));

        int dorsal = 2;
        assertFalse(jugadorService.validarDorsal(jugadores, dorsal));
    }

}
