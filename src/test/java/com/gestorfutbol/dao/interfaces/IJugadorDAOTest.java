package com.gestorfutbol.dao.interfaces;

import com.gestorfutbol.entity.Jugador;
import com.gestorfutbol.service.JugadorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class IJugadorDAOTest {
    @Test
    public void testRegistroJugadorExitoso() {
        JugadorDAO     mockDAO  = Mockito.mock(JugadorDAO.class);
        JugadorService servicio = new JugadorService(mockDAO);

        Mockito.when(mockDAO.guardar(any(Jugador.class))).thenReturn(true);

        assertTrue(servicio.registrarJugador("1226086307", "Cesar", 25, "Delantero", 10));

    }

    @Test
    public void testRegistroJugadorFallido() {
        JugadorDAO mockDAO = Mockito.mock(JugadorDAO.class);
        JugadorService servicio = new JugadorService(mockDAO);

        Mockito.when(mockDAO.guardar(any(Jugador.class))).thenReturn(false);

        assertFalse(servicio.registrarJugador("1226086307", "Cesar", -1, "Delantero", 10));

    }

}