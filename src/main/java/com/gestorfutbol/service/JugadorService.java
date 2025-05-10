package com.gestorfutbol.service;
import com.gestorfutbol.dao.interfaces.JugadorDAO;
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

    public boolean registrarJugador(String cedula, String nombre, int edad, String posicion, int dorsal, Equipo equipo) {

        if (validarNombre(nombre) || validarCedula(cedula) || posicionNoEsValida(posicion) || validarDorsal(dorsal, equipo)) {
            System.out.println("Error en los datos");
            return false;
        }


        Jugador jugador = new Jugador(cedula, nombre, edad, posicion, dorsal);
        System.out.println("Todo correcto");
        return true;
    }

    public boolean validarNombre(String nombre) {
        return nombre == null || nombre.isEmpty();
    }

    public boolean validarCedula(String cedula) {

        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador("126086307", "Cesar", 2, "Delantero", 9));
        jugadores.add(new Jugador("18329323", "Juan", 2, "Delantero", 10));

        for (Jugador jugador : jugadores) {
            if (jugador.getCedula().equals(cedula)) {
                System.out.println("Cedula ya existe");
                return true;
            }
        }
        return false;
    }

    public boolean posicionNoEsValida(String posicion) {
        List<String> posicionesValidas = new ArrayList<>();
        posicionesValidas.add("Portero");
        posicionesValidas.add("Defensa");
        posicionesValidas.add("Centrocampista");
        posicionesValidas.add("Delantero");


        for (String posicionValida : posicionesValidas) {
            if (posicion.equalsIgnoreCase(posicionValida)) {
                return false;
            }
        }
        System.out.println("Posicion no valida");
        return true;
    }


    public boolean validarDorsal(int dorsal, Equipo equipo) {
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador("126086307", "Cesar", 2, "Delantero", 10));
        jugadores.add(new Jugador("18329323", "Juan", 2, "Portero", 12));

        jugadores.get(0).setEquipo(equipo);
        jugadores.get(1).setEquipo(equipo);

        equipo.setJugadores(jugadores);

        for (Jugador jugador : equipo.getJugadores()) {
            if (jugador.getDorsal() == dorsal) {
                System.out.println("El dorsal ya existe");
                return true;
            }
        }
        return false;
    }
}
