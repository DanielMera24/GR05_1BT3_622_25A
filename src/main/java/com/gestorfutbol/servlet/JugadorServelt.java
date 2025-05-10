package com.gestorfutbol.servlet;

import com.gestorfutbol.dto.JugadorDTO;
import com.gestorfutbol.service.EquipoService;
import com.gestorfutbol.service.JugadorService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/jugador")
public class JugadorServelt extends HttpServlet {

    // Servicios para guardar jugadores

    private JugadorService jugadorService;
    private EquipoService equipoService;

    @Override
    public void init() {
        jugadorService = new JugadorService(new EquipoService());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Obtener el id del equipo desde la solicitud
        JugadorDTO jugadorDTO = new JugadorDTO(request.getParameter("cedulaJugador"),
                request.getParameter("nombreJugador"), Integer.parseInt(request.getParameter("dorsal")), Integer.parseInt(request.getParameter("idEquipo")));

        // Crear un nuevo jugador utilizando el DTO
        jugadorService.crearJugador(jugadorDTO);

        response.sendRedirect(request.getContextPath() + "/jugador");
    }
@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Obtener la lista de jugadores por Equipo
        int idEquipo = Integer.parseInt(request.getParameter("idEquipo"));


        List<JugadorDTO> jugadoresDeEquipo = jugadorService.listarJugadoresPorEquipo(idEquipo);


        // Aquí puedes manejar la lógica para mostrar la lista de jugadores o cualquier otra acción
        response.sendRedirect(request.getContextPath() + "/html/jugador.jsp");


    }

}
