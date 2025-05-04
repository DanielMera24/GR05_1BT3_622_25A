package com.gestorfutbol.dao;

import com.gestorfutbol.dto.EquipoDTO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.gestorfutbol.entity.Equipo;

import java.util.List;

public class EquipoDAO {
    private final SessionFactory sessionFactory;

    public EquipoDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void crearEquipo(EquipoDTO equipoDTO){
        try(Session sesion = this.sessionFactory.openSession()){
            Transaction tx = sesion.beginTransaction();
            Equipo equipo = convertirDtoAEntity(equipoDTO);
            sesion.persist(equipo);

        }
    }

    private Equipo convertirDtoAEntity(EquipoDTO equipoDTO) {
        Equipo equipo = new Equipo();
        equipo.setIdEquipo(equipoDTO.getIdEquipo());
        equipo.setNombre(equipoDTO.getNombre());
        equipo.setCiudad(equipoDTO.getCiudad());
        equipo.setEstadio(equipoDTO.getEstadio());
        return equipo;
    }

    public void guardarEquipo(Equipo equipo) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(equipo);
            tx.commit();
        }
    }

    public List<Equipo> obtenerEquipos() {
        try(Session sesion = this.sessionFactory.openSession()){
            return sesion.createQuery("FROM Equipo", Equipo.class).list();
        }
    }
}
