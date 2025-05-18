package com.gestorfutbol.dao.implementation;

import com.gestorfutbol.dao.interfaces.TarjetaDAO;
import com.gestorfutbol.entity.Tarjeta;
import org.hibernate.SessionFactory;

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
}
