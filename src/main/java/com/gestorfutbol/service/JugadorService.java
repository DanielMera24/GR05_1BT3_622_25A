/*package com.gestorfutbol.service;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.dao.interfaces.JugadorDAO;
import com.gestorfutbol.dao.implementacion.JugadorDAOImp;
import com.gestorfutbol.dto.JugadorDTO;
import com.gestorfutbol.entity.Jugador;

public class JugadorService {
    private JugadorDAO jugadorDAO;
    public JugadorService() {
        jugadorDAO = new JugadorDAOImp(HibernateUtil.getSessionFactory());
    }


    public void crearJugador(JugadorDTO jugadorDTO) {
        jugadorDAO.guardarJugador(convertirDtoAEntity(jugadorDTO));


    }

    private Jugador convertirDtoAEntity(JugadorDTO jugadorDTO) {


        Jugador jugador = new Jugador();

        jugador.setNombre(jugadorDTO.getNombre());
        jugador.setDorsal(jugadorDTO.getDorsal());
        //jugador.setIdEquipo(jugadorDTO.getIdEquipo());
        return jugador;
    }


}
*/