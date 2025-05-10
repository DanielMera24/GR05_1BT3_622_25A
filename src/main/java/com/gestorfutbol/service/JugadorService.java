package com.gestorfutbol.service;
import com.gestorfutbol.dao.interfaces.JugadorDAO;
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

    public boolean registrarJugador(String cedula, String nombre, int edad, String posicion, int dorsal) {

        if (nombre == null || nombre.isEmpty() || edad <= 0 || posicion == null || posicion.isEmpty()) {
            return false;
        }

        validarPosicion(posicion);


        Jugador jugador = new Jugador(cedula, nombre, edad, posicion, dorsal);
        return jugadorDAO.guardar(jugador);
    }

    public void validarJugadorRepetido(List<Jugador> jugadores, Jugador jugadorAgregar) {
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equalsIgnoreCase(jugadorAgregar.getNombre())) {
                throw new IllegalArgumentException("El jugador ya existe en la lista");
            }
        }
    }

    public boolean validarPosicion(String posicion) {
        List<String> posicionesValidas = new ArrayList<>();
        posicionesValidas.add("Portero");
        posicionesValidas.add("Defensa");
        posicionesValidas.add("Centrocampista");
        posicionesValidas.add("Delantero");

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
