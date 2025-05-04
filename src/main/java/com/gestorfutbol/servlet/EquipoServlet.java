package com.gestorfutbol.servlet;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.dto.EquipoDTO;
import com.gestorfutbol.dto.TorneoDTO;
import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.TablaPosiciones;
import com.gestorfutbol.entity.Torneo;
import com.gestorfutbol.service.EquipoService;
import com.gestorfutbol.service.TorneoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

@WebServlet("/equipos")
public class EquipoServlet extends HttpServlet {

    private EquipoService equipoService;
    private TorneoService torneoService;

    @Override
    public void init() throws ServletException {
        equipoService = new EquipoService();
        torneoService = new TorneoService();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Iniciando doGet para mostrar equipos!!!!!!!!!!!!!!!!!!!!!!!!!");

        List<EquipoDTO> equiposDto = equipoService.obtenerEquipos();
        List<TorneoDTO> torneosDto = torneoService.listarTorneos();

        request.setAttribute("equipos", equiposDto);
        request.setAttribute("torneos", torneosDto);
        request.getRequestDispatcher("/html/equipos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("Creando Equipo");
        String nombreEquipo = request.getParameter("nombreNuevoEquipo");
        String inicialesEquipo = request.getParameter("inicialesNuevoEquipo");
        String ciudadEquipo = request.getParameter("ciudadNuevoEquipo");
        String estadioEquipo = request.getParameter("estadioNuevoEquipo");
        int idTorneo = Integer.parseInt(request.getParameter("torneoPerteneciente"));

        boolean fueCreadoEquipo = equipoService.guardarEquipo(nombreEquipo, ciudadEquipo, estadioEquipo, inicialesEquipo, idTorneo);
        if(!fueCreadoEquipo){
            request.setAttribute("errorMensaje", "Ya existe un equipo con ese nombre o siglas.");
            doGet(request, response);
            return;
        }
        try {
            // Recuperar ID del nuevo equipo
            int idEquipo = equipoService.obtenerIdEquipoPorNombre(nombreEquipo);

            String url = request.getScheme() + "://" + request.getServerName() + ":" +
                    request.getServerPort() + request.getContextPath() + "/tablaPosiciones";

            String parametros = "idEquipo=" + idEquipo + "&idTorneo=" + idTorneo;

            URL obj;
            obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (OutputStream os = con.getOutputStream()) {
                os.write(parametros.getBytes());
                os.flush();
            }

            int responseCode = con.getResponseCode();
            System.out.println("POST a tablaPosiciones respondió con código: " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
            // Opcional: registrar error o mostrar mensaje al usuario
        }

        response.sendRedirect(request.getContextPath() + "/equipos");

    }

}
