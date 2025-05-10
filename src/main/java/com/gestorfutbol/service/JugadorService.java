package com.gestorfutbol.service;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.dao.interfaces.JugadorDAO;
import com.gestorfutbol.dao.implementations.JugadorDAOImp;
import com.gestorfutbol.dto.JugadorDTO;
import com.gestorfutbol.entity.Jugador;

import java.util.ArrayList;
import java.util.List;

public class JugadorService {
    private EquipoService equipoServices;
    private JugadorDAO jugadorDAO;
    public JugadorService(EquipoService equipoService) {
        this.equipoServices = equipoService;
        jugadorDAO = new JugadorDAOImp(HibernateUtil.getSessionFactory());
    }

    public void crearJugador(JugadorDTO jugadorDTO) {
        // Validar el DTO
        if (jugadorDTO == null || jugadorDTO.getNombre() == null || jugadorDTO.getDorsal() <= 0) {
            throw new IllegalArgumentException("Los datos del jugador son inválidos");
        }

        Jugador jugador = convertirDtoAEntity(jugadorDTO);
        jugadorDAO.guardarJugador(jugador);

    }

    private Jugador convertirDtoAEntity(JugadorDTO jugadorDTO) {
        //Definir la entidad Jugador
        Jugador jugador = new Jugador();
        jugador.setCedula(jugadorDTO.getCedula());
        jugador.setNombre(jugadorDTO.getNombre());
        jugador.setDorsal(jugadorDTO.getDorsal());
        jugador.setEquipo(equipoServices.obtenerEquipoPorId(jugadorDTO.getIdEquipo()));
        return jugador;
    }


    public List<JugadorDTO> listarJugadoresPorEquipo(int idEquipo) {

        List<Jugador> jugadores = jugadorDAO.obtenerJugadoresPorEquipo(idEquipo);
        return getJugadoresDTO(jugadores);
    }

    public List<JugadorDTO> listarJugadores() {
        List<Jugador> jugadores = jugadorDAO.obtenerTodos();
        return getJugadoresDTO(jugadores);
    }

    private List<JugadorDTO> getJugadoresDTO(List<Jugador> jugadores) {
        List<JugadorDTO> jugadoresDTO = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            // Crear JugadorDTO
            JugadorDTO jugadorDTO = new JugadorDTO(jugador.getCedula(), jugador.getNombre(), jugador.getDorsal(), jugador.getEquipo().getIdEquipo());
            jugadoresDTO.add(jugadorDTO);
        }
        return jugadoresDTO;
    }
}
