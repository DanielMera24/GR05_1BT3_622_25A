package com.gestorfutbol.dao.implementations;

import com.gestorfutbol.dao.interfaces.JugadorDAO;
import com.gestorfutbol.dto.JugadorDTO;
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
     public void guardarJugador(Jugador jugador) {
          try (Session session = sessionFactory.openSession()) {
               Transaction tx = session.beginTransaction();
               session.persist(jugador);
               tx.commit();
          } catch (Exception e) {
               e.printStackTrace();
          }
          System.out.println("Guardando jugador: " + jugador.getNombre());
     }

     @Override
     public void actualizarJugador(Jugador jugador) {

     }

     @Override
     public void eliminarJugador(int idJugador) {

     }

     @Override
     public Jugador obtenerPorId(int idJugador) {
          return null;
     }

     @Override
     public List<Jugador> obtenerTodos() {
          try (Session session = sessionFactory.openSession()) {
               Transaction tx = session.beginTransaction();
               List<Jugador> jugadores = session.createQuery("FROM Jugador", Jugador.class).list();
               tx.commit();
               return jugadores;
          } catch (Exception e) {
               e.printStackTrace();
               return null;
          }
     }

     @Override
        public List<Jugador> obtenerJugadoresPorEquipo(int idEquipo) {
            try(Session session = sessionFactory.openSession()) {
                Transaction tx = session.beginTransaction();
                List<Jugador> jugadores = session.createQuery("FROM Jugador WHERE equipo.idEquipo = :idEquipo", Jugador.class)
                        .setParameter("idEquipo", idEquipo)
                        .list();
                tx.commit();
                return jugadores;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
}
