package com.gestorfutbol.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            Properties properties = configuration.getProperties();

            // Imprimir la URL de conexión (ya con la variable sustituida)
            String dbUrl = properties.getProperty("hibernate.connection.url");
            System.out.println("URL de la base de datos: " + dbUrl); // jdbc:sqlite:./data/GestorTorneos.db

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Error al inicializar SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }


}