package com.gestorfutbol.service;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.dao.interfaces.JugadorDAO;
import com.gestorfutbol.dao.implementations.JugadorDAOImp;
import com.gestorfutbol.dto.EquipoDTO;
import com.gestorfutbol.dto.JugadorDTO;
import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.Jugador;

import java.util.ArrayList;
import java.util.List;

public class JugadorService {
    private JugadorDAO jugadorDAO;

    public JugadorService() {

    }

    public JugadorService(JugadorDAO mockDAO) {
        this.jugadorDAO = mockDAO;
    }




    public boolean registrarJugador(String nombre, int edad, String posicion) {

        if (nombre == null || nombre.isEmpty() || edad <= 0 || posicion == null || posicion.isEmpty()) {
            return false;
        }

        Jugador jugador = new Jugador(nombre, edad, posicion);
        return jugadorDAO.guardar(jugador);
    }

    public void validarJugadorRepetido(List<Jugador> jugadores, Jugador jugadorAgregar) {
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equalsIgnoreCase(jugadorAgregar.getNombre())) {
                throw new IllegalArgumentException("El jugador ya existe en la lista");
            }
        }
    }

    public boolean validarPosicion(List<String> posicionesValidas, String posicion) {
        for (String posicionValida : posicionesValidas) {
            if (posicion.equalsIgnoreCase(posicionValida)) {
                return true;
            }
        }
        return false;
    }

    public boolean validarDorsal(List<Jugador> jugadores, int dorsal) {
        for (Jugador jugador : jugadores) {
            if (jugador.getDorsal() == dorsal) {
                return false;
            }
        }
        return true;
    }
}
