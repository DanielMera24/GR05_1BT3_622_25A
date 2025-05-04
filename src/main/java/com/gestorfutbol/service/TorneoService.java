package com.gestorfutbol.service;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.dao.TorneoDAO;
import com.gestorfutbol.dto.TorneoDTO;
import com.gestorfutbol.entity.Torneo;
import org.hibernate.SessionFactory;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TorneoService {

    private final TorneoDAO torneoDAO;

    public TorneoService() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        this.torneoDAO = new TorneoDAO(sessionFactory);
    }

    public List<TorneoDTO> listarTorneos() {
        List<Torneo> torneos = torneoDAO.obtenerTodos();
        List<TorneoDTO> resultado = new ArrayList<>();
        for (Torneo torneo : torneos) {
            resultado.add(new TorneoDTO(
                    torneo.getIdTorneo(),
                    torneo.getNombre(),
                    torneo.getFechaInicio()));
        }
        return resultado;
    }

    public boolean crearTorneo(String nombre, String fechaInicioStr) {
        List<Torneo> torneos = torneoDAO.obtenerTodos();
        for (Torneo torneo : torneos) {
            if (torneo.getNombre().equalsIgnoreCase(nombre.trim())) {
                return false;
            }
        }

        Torneo torneo = new Torneo();
        torneo.setNombre(nombre.trim());
        torneo.setNumFechas(0);
        torneo.setFechaInicio(Date.valueOf(fechaInicioStr));

        torneoDAO.guardar(torneo);
        return true;
    }

}
