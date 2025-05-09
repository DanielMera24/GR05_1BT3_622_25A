package com.gestorfutbol.service;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.dao.implementation.EquipoDAO;
import com.gestorfutbol.dao.implementation.TorneoDAO;
import com.gestorfutbol.dto.EquipoDTO;
import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.Torneo;

import java.util.ArrayList;
import java.util.List;

public class EquipoService {
    private final EquipoDAO equipoDAO;
    private final TorneoDAO torneoDAO;

    public EquipoService() {
        this.equipoDAO = new EquipoDAO(HibernateUtil.getSessionFactory());
        this.torneoDAO = new TorneoDAO(HibernateUtil.getSessionFactory());
    }

    public boolean guardarEquipo(String nombre, String ciudad, String estadio, String siglas, int idTorneo) {

        // Verificar si el equipo ya existe
        List<Equipo> equipos = equipoDAO.obtenerEquipos();
        for (Equipo equipo : equipos) {
            if (equipo.getNombre().equalsIgnoreCase(nombre.trim()) || equipo.getSiglas().equalsIgnoreCase(siglas.trim())){
                return false; // El equipo ya existe, no se crea uno nuevo
            }
        }
        // Crear un nuevo equipo
        Equipo equipo = new Equipo();
        equipo.setNombre(nombre.trim());
        equipo.setCiudad(ciudad.trim());
        equipo.setCiudad(ciudad.trim());
        equipo.setEstadio(estadio.trim());
        equipo.setSiglas(siglas.trim());
        // Buscar Torneo por ID

        Torneo torneo = extraerTorneo(idTorneo);
        equipo.setTorneo(torneo); // Asignar el torneo correspondiente si es necesario

        // Guardar el equipo en la base de datos
        equipoDAO.guardarEquipo(equipo);
        return true;
    }

    private Torneo extraerTorneo(int idTorneo) {

        List<Torneo> torneos = torneoDAO.obtenerTodos();
        for (Torneo torneo : torneos) {
            if (torneo.getIdTorneo() == idTorneo) {
                return torneo;
            }
        }
        return null; // Si no se encuentra el torneo, se puede manejar el error según sea necesario
    }

    private EquipoDTO convertirEntityDto(Equipo equipo) {
        return new EquipoDTO(equipo.getNombre(), equipo.getCiudad(), equipo.getEstadio(), equipo.getSiglas());
    }

    private Equipo convertirDtoAEntity(EquipoDTO equipoDTO) {
        Equipo equipo = new Equipo();
        equipo.setIdEquipo(equipoDTO.getIdEquipo());
        equipo.setNombre(equipoDTO.getNombre());
        equipo.setCiudad(equipoDTO.getCiudad());
        equipo.setEstadio(equipoDTO.getEstadio());
        return equipo;
    }

    public List<EquipoDTO> listarEquipos() {
        List<Equipo> equiposEntidad = equipoDAO.obtenerEquipos();
        return convertirListaEntityADto(equiposEntidad);
    }

    private List<EquipoDTO> convertirListaEntityADto(List<Equipo> equiposEntidad) {
        List<EquipoDTO> equiposDto = new ArrayList<>();
        for (Equipo equipo : equiposEntidad) {
            EquipoDTO equipoDTO = new EquipoDTO(equipo.getNombre(), equipo.getCiudad(), equipo.getEstadio(), equipo.getSiglas());
            equipoDTO.setIdTorneo(equipo.getTorneo().getIdTorneo());
            equipoDTO.setIdEquipo(equipo.getIdEquipo());
            equiposDto.add(equipoDTO);
        }
        return equiposDto;
    }

    public int obtenerIdEquipoPorNombre(String nombre) {
        List<Equipo> equipos = equipoDAO.obtenerEquipos();
        for (Equipo e : equipos){
            if (e.getNombre().equalsIgnoreCase(nombre)){
                return e.getIdEquipo();
            }
        }

        return -1;
    }
    public Equipo obtenerEquipoPorId(int idEquipo) {
        return equipoDAO.obtenerEquipoPorId(idEquipo);
    }


}
