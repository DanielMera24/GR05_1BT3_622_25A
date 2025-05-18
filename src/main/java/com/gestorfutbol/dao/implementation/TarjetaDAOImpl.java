package com.gestorfutbol.dao.implementation;

import com.gestorfutbol.dao.interfaces.TarjetaDAO;
import com.gestorfutbol.entity.Tarjeta;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class TarjetaDAOImpl implements TarjetaDAO {
    private final SessionFactory sessionFactory;

    public TarjetaDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public boolean guardarTarjeta(Tarjeta tarjeta) {
        try (var session = sessionFactory.openSession()) {
            var tx = session.beginTransaction();
            session.persist(tarjeta);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Tarjeta> obtenerTodas() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Tarjeta", Tarjeta.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Tarjeta> obtenerPorPartido(int idPartido) {
        try (Session session = sessionFactory.openSession()) {
            Query<Tarjeta> query = session.createQuery(
                    "FROM Tarjeta WHERE partido.idPartido = :idPartido", Tarjeta.class);
            query.setParameter("idPartido", idPartido);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}