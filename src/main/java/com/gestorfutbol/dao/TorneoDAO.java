package com.gestorfutbol.dao;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.entity.Torneo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class TorneoDAO {
    private final SessionFactory sessionFactory;

    public TorneoDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Torneo> obtenerTodos() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Torneo", Torneo.class).list();
        }
    }

    public void guardar(Torneo torneo) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(torneo);
            tx.commit();
        }
    }


}

