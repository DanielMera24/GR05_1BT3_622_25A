package com.gestorfutbol.config;

import com.gestorfutbol.entity.Torneo;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;

public class InsertarTorneo {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Torneo torneo = new Torneo();
            torneo.setNombre("Ligue One 2023/2024");
            torneo.setFechaInicio(new Date()); // Fecha actual, puedes personalizarla
            torneo.setNumFechas(11); // Número de fechas o jornadas

            session.persist(torneo); // También puedes usar session.save(torneo);

            transaction.commit();
            System.out.println("¡Torneo insertado exitosamente!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
