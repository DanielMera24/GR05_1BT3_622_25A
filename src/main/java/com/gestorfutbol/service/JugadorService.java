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
        jugadorDAO = new JugadorDAOImp(HibernateUtil.getSessionFactory());
    }

    public void crearJugador(JugadorDTO jugadorDTO) {
        jugadorDAO.guardarJugador(convertirDtoAEntity(jugadorDTO));
    }

    private Jugador convertirDtoAEntity(JugadorDTO jugadorDTO) {

        EquipoDTO equipoDTO = jugadorDTO.getIdEquipo();
        Equipo equipo = new Equipo();
        equipo.setIdEquipo(equipoDTO.getIdEquipo());
        equipo.setCiudad(equipoDTO.getCiudad());
        equipo.setEstadio(equipoDTO.getEstadio());
        equipo.setNombre(equipoDTO.getNombre());

        //Definir la entidad Jugador

        Jugador jugador = new Jugador();
        jugador.setNombre(jugadorDTO.getNombre());
        jugador.setDorsal(jugadorDTO.getDorsal());
        jugador.setEquipo(equipo);

        return jugador;
    }


    public List<JugadorDTO> listarJugadoresPorEquipo(EquipoDTO equipoDTO) {

        List<Jugador> jugadores = jugadorDAO.obtenerJugadoresPorEquipo(equipoDTO.getIdEquipo());
        List<JugadorDTO> jugadoresDTO = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            JugadorDTO jugadorDTO = new JugadorDTO(jugador.getNombre(), jugador.getDorsal(), equipoDTO);
            jugadoresDTO.add(jugadorDTO);
        }
    return jugadoresDTO;
    }

    public List<JugadorDTO> listarJugadores() {
        List<Jugador> jugadores = jugadorDAO.obtenerTodos();
        List<JugadorDTO> jugadoresDTO = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            // Crear EquipoDTO
            EquipoDTO equipoDTO = new EquipoDTO(
            jugador.getEquipo().getNombre(), jugador.getEquipo().getEstadio(), jugador.getEquipo().getCiudad(), jugador.getEquipo().getSiglas());
            // Crear JugadorDTO
            JugadorDTO jugadorDTO = new JugadorDTO(jugador.getNombre(), jugador.getDorsal(), equipoDTO);
            jugadoresDTO.add(jugadorDTO);
        }
        return jugadoresDTO;
    }
}
