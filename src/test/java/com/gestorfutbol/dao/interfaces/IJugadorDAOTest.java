package com.gestorfutbol.dao.interfaces;

import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.Jugador;
import com.gestorfutbol.service.JugadorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class IJugadorDAOTest {
    @Test
    public void dado_Jugador_correcto_entonces_esVerdadero() {
        JugadorDAO     mockDAO  = Mockito.mock(JugadorDAO.class);
        JugadorService servicio = new JugadorService(mockDAO);

        Mockito.when(mockDAO.guardar(any(Jugador.class))).thenReturn(true);

        Equipo equipo = new Equipo(1, "Liga de Quito", "Quito", "Rodrig Paz", "LDU");
        assertTrue(servicio.registrarJugador("99999", "Cesar", 25, "Delantero", 11, equipo));
    }

    @Test
    public void dado_Jugador_incorrecto_entonces_esFalso() {
        JugadorDAO     mockDAO  = Mockito.mock(JugadorDAO.class);
        JugadorService servicio = new JugadorService(mockDAO);

        Mockito.when(mockDAO.guardar(any(Jugador.class))).thenReturn(false);

        Equipo equipo = new Equipo(1, "Liga de Quito", "Quito", "Rodrig Paz", "LDU");
        assertFalse(servicio.registrarJugador("1234", "Cesar", 25, "Delantero", 10, equipo));
    }

    @Test
    public void dado_Jugador_existente_correcto_entonces_actualiza_correctamente() {
        JugadorDAO     mockDAO  = Mockito.mock(JugadorDAO.class);
        JugadorService servicio = new JugadorService(mockDAO);

        Mockito.when(mockDAO.actualizar(any(Jugador.class))).thenReturn(true);

        Equipo equipo = new Equipo(1, "Liga de Quito", "Quito", "Rodrig Paz", "LDU");
        assertTrue(servicio.actualizarJugador("126086307", "Cesar Actualizado", 26, "Delantero", 9, equipo));
    }


}