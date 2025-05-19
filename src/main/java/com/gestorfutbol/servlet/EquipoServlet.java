package com.gestorfutbol.servlet;
import com.gestorfutbol.dto.EquipoDTO;
import com.gestorfutbol.dto.TorneoDTO;
import com.gestorfutbol.service.EquipoService;
import com.gestorfutbol.service.TablaPosicionesService;
import com.gestorfutbol.service.TorneoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@WebServlet("/equipos")
public class EquipoServlet extends HttpServlet {
    private EquipoService equipoService;
    private TorneoService torneoService;
    private TablaPosicionesService tablaPosicionesService;

    @Override
    public void init() throws ServletException {
        equipoService = new EquipoService();
        torneoService = new TorneoService();
        tablaPosicionesService = new TablaPosicionesService();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Iniciando doGet para mostrar equipos!!!!!!!!!!!!!!!!!!!!!!!!!");

        List<EquipoDTO> equiposDto = equipoService.listarEquipos();
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

        boolean fueCreadoEquipo = equipoService.guardarEquipo(
                nombreEquipo, ciudadEquipo, estadioEquipo, inicialesEquipo, idTorneo);
        if(!fueCreadoEquipo){
            request.setAttribute("errorMensaje", "Ya existe un equipo con ese nombre o siglas.");
            doGet(request, response);
            return;
        }
        try {
            int idEquipo = equipoService.obtenerIdEquipoPorNombre(nombreEquipo);

            // Si necesitas actualizar la tabla de posiciones:
            tablaPosicionesService.crearRegistro(idEquipo, idTorneo); // <-- Método directo

        } catch (Exception e) {
            e.printStackTrace();
            // Manejar error
        }

        response.sendRedirect(request.getContextPath() + "/equipos");
    }


}
