package com.gestorfutbol.dao.implementations;

import com.gestorfutbol.dao.interfaces.JugadorDAO;
import com.gestorfutbol.entity.Jugador;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class JugadorDAOImp {

    private SessionFactory sessionFactory;

    public JugadorDAOImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}