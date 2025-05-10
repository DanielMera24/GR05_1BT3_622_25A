package com.gestorfutbol.dao.implementation;

import com.gestorfutbol.entity.JugadorPartido;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class JugadorPartidoDAO {

    private SessionFactory sessionFactory;

    public JugadorPartidoDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<JugadorPartido> listarJugadorPartidos(int idJugador) {


        try(Session session = sessionFactory.openSession()) {

        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

}
