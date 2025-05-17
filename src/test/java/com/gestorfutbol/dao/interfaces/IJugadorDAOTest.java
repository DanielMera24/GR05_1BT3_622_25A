package com.gestorfutbol.dao.interfaces;

import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.Jugador;
import com.gestorfutbol.service.JugadorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

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
    public void dado_Jugador_cuando_existe_entonces_actualizaCorrectamente() {
        // 1. Configuración
        JugadorDAO mockDAO = Mockito.mock(JugadorDAO.class);
        JugadorService servicio = new JugadorService(mockDAO);

        String cedula = "1104567890";
        Equipo equipo = new Equipo(2, "Liga de Quito", "Quito", "Rodrigo Paz", "LDU");

        // 2. Simular comportamiento
        Jugador jugadorExistente = new Jugador(cedula, "Cesar Original", 25, "Delantero", 9);
        jugadorExistente.setEquipo(equipo); // Asignar el equipo al jugador existente

        // Cambiar a obtenerJugador si ese es el método real que usa tu servicio
        Mockito.when(mockDAO.obtenerJugador(cedula)).thenReturn(jugadorExistente);
        Mockito.when(mockDAO.actualizar(any(Jugador.class))).thenReturn(true);

        // 3. Ejecutar
        boolean resultado = servicio.actualizarJugador(cedula, "Cesar Actualizado", 26, "Delantero", 9, equipo);

        // 4. Verificar
        assertTrue(resultado);

        // Verificar llamadas (actualizado para 2 invocaciones)
        Mockito.verify(mockDAO, times(2)).obtenerJugador(cedula); // Ahora esperamos 2 llamadas
        Mockito.verify(mockDAO).actualizar(any(Jugador.class));
    }


}