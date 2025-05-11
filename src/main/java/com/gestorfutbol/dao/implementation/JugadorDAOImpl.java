package com.gestorfutbol.dao.implementation;

import com.gestorfutbol.dao.interfaces.JugadorDAO;
import com.gestorfutbol.entity.Jugador;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class JugadorDAOImpl implements JugadorDAO {
    private final SessionFactory sessionFactory;

    public JugadorDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean guardar(Jugador jugador) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(jugador);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Jugador jugador) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(jugador);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Jugador obtenerJugador(String cedula) {
        try (Session session = sessionFactory.openSession()) {
            Query<Jugador> query = session.createQuery(
                    "FROM Jugador WHERE cedula = :cedula", Jugador.class);
            query.setParameter("cedula", cedula);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Jugador> obtenerTodos() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Jugador", Jugador.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Jugador> obtenerPorEquipo(int idEquipo) {
        try (Session session = sessionFactory.openSession()) {
            Query<Jugador> query = session.createQuery(
                    "FROM Jugador WHERE equipo.idEquipo = :idEquipo", Jugador.class);
            query.setParameter("idEquipo", idEquipo);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}