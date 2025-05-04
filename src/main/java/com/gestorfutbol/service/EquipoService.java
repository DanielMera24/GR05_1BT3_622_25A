package com.gestorfutbol.service;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.dao.EquipoDAO;
import com.gestorfutbol.dto.EquipoDTO;
import com.gestorfutbol.entity.Equipo;

import java.util.ArrayList;
import java.util.List;

public class EquipoService {
    private final EquipoDAO equipoDAO;

    public EquipoService() {
        this.equipoDAO = new EquipoDAO(HibernateUtil.getSessionFactory());
    }

    public EquipoDTO guardarEquipo(EquipoDTO equipoDTO) {
        Equipo equipo = convertirDtoAEntity(equipoDTO);
        equipoDAO.guardarEquipo(equipo);
        return convertirEntityDto(equipo);
        // Pendiente si se requiere que se retorne algo al momento de crear partido
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

    public List<EquipoDTO> obtenerEquipos() {
        List<Equipo> equiposEntidad = equipoDAO.obtenerEquipos();
        return convertirListaEntityADto(equiposEntidad);

    }

    private List<EquipoDTO> convertirListaEntityADto(List<Equipo> equiposEntidad) {
        List<EquipoDTO> equiposDto = new ArrayList<>();
        for (Equipo equipo : equiposEntidad) {
            EquipoDTO equipoDTO = new EquipoDTO(equipo.getNombre(), equipo.getCiudad(), equipo.getEstadio(), equipo.getSiglas());
            equiposDto.add(equipoDTO);
        }
        return equiposDto;
    }

}
