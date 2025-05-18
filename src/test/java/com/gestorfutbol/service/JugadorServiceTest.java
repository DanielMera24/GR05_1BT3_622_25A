package com.gestorfutbol.service;

import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.Jugador;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JugadorServiceTest {
    private static JugadorService jugadorService;

    @BeforeAll
    public static void init() {
        jugadorService = new JugadorService();
    }

    @Test
    public void dado_nombreJugador_cuando_esNulo_esVerdadero() {
        Jugador jugador = new Jugador("126086307", null, 2, "Delantero", 10);
        assertTrue(jugadorService.validarNombre(jugador.getNombre()));
    }

    @Test
    public void dada_cedulaJugador_cuando_noEstaRepetido_entonces_esNull() {
        Jugador jugador = new Jugador("99999", "Cesar", 2, "Delantero", 10);
        assertNull(jugadorService.validarCedula(jugador.getCedula()));
    }

    @Test
    public void dada_posicion_cuando_noEsValida_entonces_esVerdadero() {
        Jugador jugador = new Jugador("126086307", "Cesar", 2, "EjemploFalso", 10);
        assertTrue(jugadorService.posicionNoEsValida(jugador.getPosicion()));
    }

    @Test
    public void dado_dorsal_cuando_estaNoEstaRepetido_entonces_esFalso() {
        Jugador jugador = new Jugador("123456789", "Pedro", 2, "Delantero", 1);
        Equipo equipo = new Equipo(1, "Liga de Quito", "Quito", "Rodrig Paz", "LDU");
        assertFalse(jugadorService.validarDorsal(jugador.getDorsal(), equipo));
    }

    @Test
    public void cuando_nombreTieneDigitos_entonces_lanzarExcepcion() {
        String nombre = "D1niel";
        assertThrows(IllegalArgumentException.class, () -> {
            jugadorService.verificarEstructuraNombre(nombre);
        });
    }

    @Test
    public void cuando_nombreTieneCaracteresEspeciales_entonces_lanzarExcepcion() {
        String nombre = "D@niel";
        assertThrows(IllegalArgumentException.class, () -> {
            jugadorService.verificarEstructuraNombre(nombre);
        });
    }

    @Test
    public void jugador_CedulaInmutable_NoTieneMetodoSetter() throws Exception {
        assertThrows(NoSuchMethodException.class, () -> {
            Jugador.class.getMethod("setCedula", String.class);
        }, "La clase Jugador no debe tener un método setCedula()");
    }

    @Test
    public void dado_cedula_cuando_tieneMenosDeDiezDigitos_entonces_retornarFalso() {
        boolean resultado = jugadorService.validarSintaxisCedula("123456789");
        assertFalse(resultado);
    }

    @Test
    public void dado_cedula_cuando_tieneMasDeDiezDigitos_entonces_retornarFalso() {
        boolean resultado = jugadorService.validarSintaxisCedula("171543523411");
        assertFalse(resultado);
    }

    @Test
    public void dado_cedula_cuando_tieneDiezDigitos_entonces_retornarVerdadero() {
        boolean resultado = jugadorService.validarSintaxisCedula("0503867734");
        assertTrue(resultado);
    }

    @Test
    public void dado_cedula_cuando_tieneLetras_entonces_retornarFalso() {
        boolean resultado = jugadorService.validarSintaxisCedula("050hjh0051");
        assertFalse(resultado);
    }

    @Test
    public void dado_cedula_cuando_esNulo_retornarFalso() {
        boolean resultado = jugadorService.validarSintaxisCedula(null);
        assertFalse(resultado);
    }
}
