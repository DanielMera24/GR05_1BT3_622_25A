package com.gestorfutbol.service;

import com.gestorfutbol.dao.TablaPosicionesDAO;
import com.gestorfutbol.dto.TablaPosicionesDTO;
import com.gestorfutbol.entity.TablaPosiciones;
import com.gestorfutbol.entity.Torneo;

import java.util.ArrayList;
import java.util.List;

public class TablaPosicionesService {

    private TablaPosicionesDAO dao = new TablaPosicionesDAO();

    public List<TablaPosicionesDTO> obtenerTablaPorTorneo(int idTorneo) {
        List<TablaPosiciones> entidades = dao.obtenerPorTorneo(idTorneo);
        List<TablaPosicionesDTO> dtoList = new ArrayList<>();

        for (TablaPosiciones tp : entidades) {
            TablaPosicionesDTO dto = new TablaPosicionesDTO();
            dto.setNombreEquipo(tp.getEquipo().getNombre());
            dto.setPuntosAcumulados(tp.getPuntosAcumulados());
            dto.setPartidosJugados(tp.getPartidosJugados());
            dto.setPartidosGanados(tp.getPartidosGanados());
            dto.setPartidosEmpatados(tp.getPartidosEmpatados());
            dto.setPartidosPerdidos(tp.getPartidosPerdidos());
            dto.setGolesAFavor(tp.getGolesAFavor());
            dto.setGolesEnContra(tp.getGolesEnContra());
            dto.setDiferenciaGoles(tp.getDiferenciaGoles());
            dto.setFechaActualizacion(tp.getFechaActualizacion());
            dtoList.add(dto);
        }

        return dtoList;
    }

    public List<Torneo> obtenerTorneos() {
        return dao.listarTorneos();
    }
}
