package com.gestorfutbol.dao;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.entity.TablaPosiciones;
import com.gestorfutbol.entity.Torneo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class TablaPosicionesDAO {
    private final SessionFactory sessionFactory;

    public TablaPosicionesDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public List<TablaPosiciones> obtenerPorTorneo(int idTorneo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            List<TablaPosiciones> lista = session.createQuery(
                            "FROM TablaPosiciones WHERE torneo.idTorneo = :idTorneo ORDER BY puntosAcumulados DESC",
                            TablaPosiciones.class)
                    .setParameter("idTorneo", idTorneo)
                    .list();

            tx.commit();
            return lista;
        }
    }

    public List<Torneo> listarTorneos() {
        List<Torneo> torneos = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            torneos = session.createQuery("FROM Torneo", Torneo.class).list();
            tx.commit();
        }
        return torneos;
    }

    public void guardar(TablaPosiciones tablaPosiciones) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(tablaPosiciones);
            tx.commit();
        }
    }

}
