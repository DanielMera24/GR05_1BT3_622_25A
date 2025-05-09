package com.gestorfutbol.dao;

import com.gestorfutbol.dto.PartidoDTO;
import com.gestorfutbol.entity.Partido;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class PartidoDAO {
    private SessionFactory sessionFactory;

    public PartidoDAO(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public void guardar(Partido partido) {
        System.out.println("gurdando partido");
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.persist(partido);
            tx.commit();
            System.out.println("partido correctamente guardado");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public List<Partido> extraerTodos() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Partido", Partido.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void actualizar (Partido partido) {
        Partido partidoEncontrado = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            partidoEncontrado = (Partido) session.get(Partido.class, partido.getIdPartido());

            if(partidoEncontrado != null) {
                System.out.println("partido no es nulo");
                partidoEncontrado.setGolesLocal(partido.getGolesLocal());
                partidoEncontrado.setGolesVisita(partido.getGolesVisita());
                partidoEncontrado.setEstado(partido.getEstado());
                session.update(partidoEncontrado);
                tx.commit();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
