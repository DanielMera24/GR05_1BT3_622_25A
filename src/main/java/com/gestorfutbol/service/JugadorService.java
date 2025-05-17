package com.gestorfutbol.service;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.dao.implementation.JugadorDAOImpl;
import com.gestorfutbol.dao.interfaces.JugadorDAO;
import com.gestorfutbol.dto.JugadorDTO;
import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.Jugador;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class JugadorService {
    private JugadorDAO jugadorDAO;
    private SessionFactory sessionFactory;

    public JugadorService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.jugadorDAO = new JugadorDAOImpl(sessionFactory);
    }

    public JugadorService(JugadorDAO jugadorDAO) {
        this.jugadorDAO = jugadorDAO;
    }

    public List<JugadorDTO> listarJugadores() {
        List<Jugador> jugadores = jugadorDAO.obtenerTodos();
        return getJugadoresDTO(jugadores);
    }

    public List<JugadorDTO> listarJugadoresPorEquipo(int idEquipo) {
        List<Jugador> jugadores = jugadorDAO.obtenerPorEquipo(idEquipo);
        return getJugadoresDTO(jugadores);
    }

    private List<JugadorDTO> getJugadoresDTO(List<Jugador> jugadores) {
        List<JugadorDTO> jugadoresDTO = new ArrayList<>();

        if (jugadores != null) {
            for (Jugador jugador : jugadores) {
                String nombreEquipo = jugador.getEquipo() != null ?
                        jugador.getEquipo().getNombre() : "Sin equipo";

                JugadorDTO dto = getJugadorDTO(jugador, nombreEquipo);
                jugadoresDTO.add(dto);
            }
        }

        return jugadoresDTO;
    }

    private static JugadorDTO getJugadorDTO(Jugador jugador, String nombreEquipo) {
        String abreviaturaEquipo = jugador.getEquipo() != null ?
                jugador.getEquipo().getSiglas() : "";

        return new JugadorDTO(
                jugador.getIdJugador(),
                jugador.getCedula(),
                jugador.getNombre(),
                jugador.getDorsal(),
                jugador.getEdad(),
                jugador.getPosicion(),
                nombreEquipo,
                abreviaturaEquipo
        );
    }

    public boolean registrarJugador(String cedula, String nombre, int edad, String posicion, int dorsal, Equipo equipo) {
        if (validarNombre(nombre) || validarCedula(cedula) != null || posicionNoEsValida(posicion) || validarDorsal(dorsal, equipo)) {
            System.out.println("Error en los datos");
            return false;
        }

        Jugador jugador = new Jugador(cedula, nombre, edad, posicion, dorsal);
        jugador.setEquipo(equipo);
        System.out.println("Todo correcto");
        return jugadorDAO.guardar(jugador);
    }


    public boolean actualizarJugador(String cedula, String nombre, int edad, String posicion, int dorsal, Equipo equipo) {
        if (!datosValidosParaActualizar(cedula, nombre, posicion, dorsal, equipo)) {
            return false;
        }
        Jugador jugador = new Jugador(cedula, nombre, edad, posicion, dorsal);
        jugador.setEquipo(equipo);

        Jugador existente = jugadorDAO.obtenerJugador(cedula);
        if (existente != null) {
            jugador.setIdJugador(existente.getIdJugador());
        }

        System.out.println("Todo correcto para actualizar");
        return jugadorDAO.actualizar(jugador);
    }

    private boolean datosValidosParaActualizar(String cedula, String nombre,
                                               String posicion, int dorsal, Equipo equipo) {
        if (validarNombre(nombre) || posicionNoEsValida(posicion)) {
            System.out.println("Error en los datos para actualizar");
            return false;
        }
        if (validarCedula(cedula) == null) {
            System.out.println("Error: cédula no existe");
            return false;
        }
        if (validarDorsalParaActualizar(dorsal, equipo, cedula)) {
            System.out.println("Error: dorsal ya existe en otro jugador");
            return false;
        }
        return true;
    }






    public boolean validarNombre(String nombre) {
        return nombre == null || nombre.isEmpty();
    }

    public Jugador validarCedula(String cedula) {
        return jugadorDAO.obtenerJugador(cedula);
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
        List<Jugador> jugadores = jugadorDAO.obtenerPorEquipo(equipo.getIdEquipo());

        for (Jugador jugador : jugadores) {
            if (jugador.getDorsal() == dorsal) {
                System.out.println("El dorsal ya existe");
                return true;
            }
        }
        return false;
    }

    public boolean validarDorsalParaActualizar(int dorsal, Equipo equipo, String cedulaJugadorActual) {
        List<Jugador> jugadores = jugadorDAO.obtenerPorEquipo(equipo.getIdEquipo());
        for (Jugador jugador : jugadores) {
            if (jugador.getDorsal() == dorsal && !jugador.getCedula().equals(cedulaJugadorActual)) {
                System.out.println("El dorsal ya existe en otro jugador");
                return true;
            }
        }
        return false;
    }

    public void verificarEstructuraNombre(String nombre) {
        if (nombre == null || !nombre.matches("[A-Za-z ]+")) {
            throw new IllegalArgumentException("Nombre inválido: " + nombre);
        }
    }

    public boolean validarSintaxisCedula(String cedula) {
        if (cedula == null || cedula.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[0-9]{10}$");
        return pattern.matcher(cedula).matches();
    }

    public Jugador buscarJugadorEnEquipoPorCedula(String cedula, Equipo equipo) {
        if (equipo != null && equipo.getJugadores() != null) {
            for (Jugador jugador : equipo.getJugadores()) {
                if (jugador.getCedula().equals(cedula)) {
                    return jugador;
                }
            }
        }
        return null;
    }

    public Jugador obtenerJugadorPorCedula(String cedula) {
        return jugadorDAO.obtenerJugador(cedula);
    }
}