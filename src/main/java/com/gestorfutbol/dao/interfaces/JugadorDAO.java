package com.gestorfutbol.dao.interfaces;

import com.gestorfutbol.entity.Jugador;
import java.util.List;

public interface JugadorDAO {
    boolean guardar(Jugador jugador);
    boolean actualizar(Jugador jugador);
    Jugador obtenerJugador(String cedula);
    List<Jugador> obtenerTodos();
    List<Jugador> obtenerPorEquipo(int idEquipo);

    Jugador obtenerJugadorPorId(int id);
}