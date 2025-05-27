package com.gestorfutbol.dao.implementation;

import com.gestorfutbol.dao.interfaces.DetallePartidoDAO;
import com.gestorfutbol.entity.DetallePartido;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class DetallePartidoDAOImpl implements DetallePartidoDAO {
    private final SessionFactory sessionFactory;

    public DetallePartidoDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean guardar(DetallePartido detallePartido) {
        try (var session = sessionFactory.openSession()) {
            var tx = session.beginTransaction();
            session.persist(detallePartido);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<DetallePartido> listarPorPartido(int idPartido) {
        try (Session session = sessionFactory.openSession()) {
            // Usamos JOIN FETCH para cargar la colección de goles de manera temprana
            return session.createQuery(
                            "FROM DetallePartido dp LEFT JOIN FETCH dp.goles WHERE dp.partido.idPartido = :idPartido",
                            DetallePartido.class)
                    .setParameter("idPartido", idPartido)
                    .getResultList();
        }
    }

    @Override
    public List<DetallePartido> listarPorPartidoYEquipo(int idPartido, int idEquipo) {
        try (Session session = sessionFactory.openSession()) {
            // Usamos JOIN FETCH para cargar la colección de goles de manera temprana
            return session.createQuery(
                            "FROM DetallePartido dp LEFT JOIN FETCH dp.goles WHERE dp.partido.idPartido = :idPartido AND dp.equipo.idEquipo = :idEquipo",
                            DetallePartido.class)
                    .setParameter("idPartido", idPartido)
                    .setParameter("idEquipo", idEquipo)
                    .getResultList();
        }
    }
}