package com.gestorfutbol.service;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.dao.implementation.GolDAOImpl;
import com.gestorfutbol.dao.interfaces.GolDAO;
import com.gestorfutbol.dto.GolDTO;
import com.gestorfutbol.entity.Gol;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GolService {

    private final GolDAO golDAO;

    public GolService(GolDAO golDAO) {
        this.golDAO = golDAO;
    }

    public GolService() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        this.golDAO = new GolDAOImpl(sessionFactory);
    }

    // ===== MÉTODOS DE PASO PARA EL DAO =====

    /**
     * Guarda un gol (las validaciones están en DetallePartidoService)
     */

    // ===== MÉTODOS PARA CONVERTIR A DTO =====

    /**
     * Convierte una lista de entidades Gol a DTOs
     */
    private List<GolDTO> convertirADTOs(List<Gol> goles) {
        if (goles == null) {
            return new ArrayList<>();
        }

        return goles.stream()
                .map(this::crearGolDTO)
                .collect(Collectors.toList());
    }

    /**
     * Crea un DTO a partir de una entidad Gol
     */
    private GolDTO crearGolDTO(Gol gol) {
        return new GolDTO(
                gol.getIdGol(),
                gol.getMinuto(),
                gol.getJugador() != null ? gol.getJugador().getNombre() : "Desconocido",
                gol.getEquipo() != null ? gol.getEquipo().getNombre() : "Desconocido",
                gol.getDorsal(),
                gol.getDetallePartido().getPartido().getIdPartido(),
                gol.getJugador() != null ? gol.getJugador().getIdJugador() : 0
        );
    }

    /**
     * Lista goles de un partido como DTOs
     */
    public List<GolDTO> listarGolesPorPartido(int idPartido) {
        List<Gol> goles = obtenerPorPartido(idPartido);
        return convertirADTOs(goles);
    }

    /**
     * Obtiene goles por partido
     */
    public List<Gol> obtenerPorPartido(int idPartido) {
        List<Gol> goles = golDAO.obtenerPorPartido(idPartido);
        return goles != null ? goles : new ArrayList<>();
    }
}