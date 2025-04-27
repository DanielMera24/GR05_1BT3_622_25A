package com.gestorfutbol.config;

public class TestCreacionTablas {
    public static void main(String[] args) {
        try {
            HibernateUtil.getSessionFactory().openSession();
            System.out.println("Las tablas se han creado correctamente.");
        } catch (Exception e) {
            System.err.println("Error al crear las tablas: " + e.getMessage());
        } finally {
            HibernateUtil.getSessionFactory().close();
        }
    }
}
