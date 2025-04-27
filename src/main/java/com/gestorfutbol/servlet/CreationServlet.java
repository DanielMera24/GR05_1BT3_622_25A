package com.gestorfutbol.servlet;

import com.gestorfutbol.config.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/crearBase")
public class CreationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = null;
        Transaction transaction = null;
        try {
            // Obtener la sesión de Hibernate
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Aquí podrías crear las tablas si la configuración de Hibernate lo permite
            // Este paso se realiza automáticamente si tienes 'hibernate.hbm2ddl.auto' configurado
            System.out.println("Creando las tablas en la base de datos...");

            // Si deseas forzar la creación de las tablas, puedes usar un SchemaExport
            // Example of schema export (descomenta si deseas hacerlo manualmente)
            // new SchemaExport().create(true, true); // true = drop & create

            // Confirmar la transacción
            transaction.commit();
            response.setStatus(HttpServletResponse.SC_NO_CONTENT); // No Content Status

        } catch (HibernateException e) {
            // Manejo de errores
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            if (session != null) session.close();
        }
    }
}
