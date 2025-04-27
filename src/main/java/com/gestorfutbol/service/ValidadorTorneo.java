package com.gestorfutbol.service;

import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.Torneo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;

public class ValidadorTorneo {
    public boolean torneoYaExiste(String nombre, Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class); // Utilizamos Long para contar las coincidencias
        Root<Torneo> root = cq.from(Torneo.class);
        // Contamos los torneos con el nombre especificado
        cq.select(cb.count(root)).where(cb.equal(root.get("nombre"), nombre));
        // Ejecutamos la consulta y obtenemos el conteo de coincidencias
        Long count = session.createQuery(cq).getSingleResult();
        // Si el conteo es mayor que 0, existe al menos un torneo con ese nombre
        return count > 0;
    }
}
