package com.gestorfutbol.dao.interfaces;

import com.gestorfutbol.dto.JugadorDTO;
import com.gestorfutbol.entity.Jugador;

import java.util.List;

public interface JugadorDAO {
    boolean guardar(Jugador jugador);
    boolean actualizar(Jugador jugador);

}
