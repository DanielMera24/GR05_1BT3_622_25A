package com.gestorfutbol.service;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.dao.TablaPosicionesDAO;
import com.gestorfutbol.dao.TorneoDAO;
import com.gestorfutbol.dto.TablaPosicionesDTO;
import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.TablaPosiciones;
import com.gestorfutbol.entity.Torneo;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TablaPosicionesService {
    private final TablaPosicionesDAO tablaPosicionesDAO;

    public TablaPosicionesService() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        this.tablaPosicionesDAO = new TablaPosicionesDAO(sessionFactory);
    }

    public List<TablaPosicionesDTO> obtenerTablaPorTorneo(int idTorneo) {
        List<TablaPosiciones> registros = tablaPosicionesDAO.obtenerPorTorneo(idTorneo);
        List<TablaPosicionesDTO> registrosDTO = new ArrayList<>();

        for (TablaPosiciones tp : registros) {
            TablaPosicionesDTO dto = new TablaPosicionesDTO(
                    tp.getIdTablaPosicion(),
                    tp.getEquipo(),
                    tp.getTorneo(),
                    tp.getPuntosAcumulados(),
                    tp.getPartidosJugados(),
                    tp.getPartidosGanados(),
                    tp.getPartidosEmpatados(),
                    tp.getPartidosPerdidos(),
                    tp.getGolesAFavor(),
                    tp.getGolesEnContra(),
                    tp.getDiferenciaGoles(),
                    tp.getFechaActualizacion()
            );
            registrosDTO.add(dto);
        }

        return registrosDTO;
    }

    public void crearRegistro(int idEquipo, int idTorneo) {
        Torneo torneo = new Torneo();
        torneo.setIdTorneo(idTorneo);

        Equipo equipo = new Equipo();
        equipo.setIdEquipo(idEquipo);

        TablaPosiciones tp = new TablaPosiciones();
        tp.setTorneo(torneo);
        tp.setEquipo(equipo);
        tp.setPuntosAcumulados(0);
        tp.setPartidosJugados(0);
        tp.setPartidosGanados(0);
        tp.setPartidosEmpatados(0);
        tp.setPartidosPerdidos(0);
        tp.setGolesAFavor(0);
        tp.setGolesEnContra(0);
        tp.setDiferenciaGoles(0);
        tp.setFechaActualizacion(new Date());

        tablaPosicionesDAO.guardar(tp);
    }
    public List<Torneo> obtenerTorneos() {
        return tablaPosicionesDAO.listarTorneos();
    }


}
