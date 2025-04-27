package com.gestorfutbol.config;


import com.gestorfutbol.entity.Torneo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class TorneoDAO {

    public static void main(String[] args) {
        // Obtener la sesión de Hibernate
        SessionFactory factory = com.gestorfutbol.config.HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        try {
            // Iniciar la transacción
            session.beginTransaction();

            // Consulta HQL para obtener todos los torneos
            String hql = "FROM Torneo";  // Consulta simple para obtener todos los torneos
            Query<Torneo> query = session.createQuery(hql, Torneo.class);
            List<Torneo> torneos = query.list(); // Ejecutar consulta

            // Imprimir resultados en consola
            for (Torneo torneo : torneos) {
                System.out.println("ID: " + torneo.getIdTorneo() +
                        ", Nombre: " + torneo.getNombre() +
                        ", Fecha de Inicio: " + torneo.getFechaInicio() +
                        ", Número de Fechas: " + torneo.getNumFechas());
            }

            // Confirmar la transacción
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            // Cerrar la sesión
            session.close();
        }

        // Cerrar la fábrica de sesiones
        factory.close();
    }
}