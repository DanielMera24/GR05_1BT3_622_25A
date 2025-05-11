package com.gestorfutbol.dao.implementations;

import com.gestorfutbol.dao.interfaces.JugadorDAO;
import com.gestorfutbol.entity.Jugador;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class JugadorDAOImp implements JugadorDAO {

    private SessionFactory sessionFactory;

    public JugadorDAOImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean guardar(Jugador jugador) {
        return false;
    }

    @Override
    public boolean actualizar(Jugador jugador) {
        return false;
    }

    @Override
    public Jugador obtenerJugador(String cedula) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            Jugador jugador = (Jugador) session.createQuery("FROM Jugador WHERE Jugador.cedula = :cedula");
            return jugador;
        } catch (Exception e){
            return null;
        }
    }
}