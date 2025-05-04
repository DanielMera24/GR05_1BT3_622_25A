package com.gestorfutbol.dao;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.entity.TablaPosiciones;
import com.gestorfutbol.entity.Torneo;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TablaPosicionesDAO {

    public List<TablaPosiciones> obtenerPorTorneo(int idTorneo) {
        List<TablaPosiciones> lista = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            lista = session.createQuery(
                            "FROM TablaPosiciones WHERE torneo.idTorneo = :idTorneo ORDER BY puntosAcumulados DESC",
                            TablaPosiciones.class)
                    .setParameter("idTorneo", idTorneo)
                    .list();

            tx.commit();
        }
        return lista;
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
}
