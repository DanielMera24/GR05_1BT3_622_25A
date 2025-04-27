package com.gestorfutbol.service;

import com.gestorfutbol.entity.Equipo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;

public class ValidadorEquipo {
    public boolean equipoYaExiste(String nombre, Session session) {
        // Crear un CriteriaBuilder
        CriteriaBuilder cb = session.getCriteriaBuilder();

        // Crear una consulta para contar las coincidencias del nombre del equipo
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Equipo> root = cq.from(Equipo.class);

        // Contar los equipos con el nombre especificado
        cq.select(cb.count(root)).where(cb.equal(root.get("nombre"), nombre));

        // Ejecutar la consulta y obtener el conteo de coincidencias
        Long count = session.createQuery(cq.toString(), Long.class).getSingleResult();

        // Si el conteo es mayor que 0, ya existe un equipo con ese nombre
        return count > 0;
    }

    public boolean jugadorYaRegistrado(String nombre, Session session){
        return false;
    }
}
