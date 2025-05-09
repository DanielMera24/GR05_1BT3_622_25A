package com.gestorfutbol.dao.interfaces;

import com.gestorfutbol.dto.JugadorDTO;
import com.gestorfutbol.entity.Jugador;

import java.util.List;

public interface JugadorDAO {
    // Define the methods that you want to implement in the DAO
    // For example:
    void guardarJugador(Jugador jugador);
    void actualizarJugador(Jugador jugador);
    void eliminarJugador(int idJugador);
    Jugador obtenerPorId(int idJugador);
    List<Jugador> obtenerTodos();
    List<Jugador> obtenerJugadoresPorEquipo(int idEquipo);
}
