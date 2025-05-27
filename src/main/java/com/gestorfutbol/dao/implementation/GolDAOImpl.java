package com.gestorfutbol.dao.implementation;

import com.gestorfutbol.dao.interfaces.GolDAO;
import com.gestorfutbol.entity.Gol;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class GolDAOImpl implements GolDAO {
    private final SessionFactory sessionFactory;

    public GolDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean guardarGol(Gol gol) {
        try (var session = sessionFactory.openSession()) {
            var tx = session.beginTransaction();
            session.persist(gol);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Gol> obtenerPorPartido(int idPartido) {
        try (Session session = sessionFactory.openSession()) {
            Query<Gol> query = session.createQuery(
                    "FROM Gol g WHERE g.detallePartido.partido.idPartido = :idPartido ORDER BY g.minuto ASC",
                    Gol.class);
            query.setParameter("idPartido", idPartido);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}