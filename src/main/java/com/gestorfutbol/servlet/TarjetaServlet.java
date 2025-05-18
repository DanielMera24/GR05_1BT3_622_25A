package com.gestorfutbol.servlet;

import com.gestorfutbol.dto.PartidoDTO;
import com.gestorfutbol.dto.TarjetaDTO;
import com.gestorfutbol.service.PartidoService;
import com.gestorfutbol.service.TarjetaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/tarjetas")
public class TarjetaServlet extends HttpServlet {

    private TarjetaService tarjetaService;
    private PartidoService partidoService;

    @Override
    public void init() throws ServletException {
        super.init();
        tarjetaService = new TarjetaService();
        partidoService = new PartidoService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener el parámetro de filtro por partido
            String partidoIdStr = request.getParameter("partidoId");

            // Obtener los partidos para el selector
            List<PartidoDTO> partidos = partidoService.listarPartidos();

            // Obtener tarjetas según el filtro
            List<TarjetaDTO> tarjetas;
            if (partidoIdStr != null && !partidoIdStr.isEmpty()) {
                int partidoId = Integer.parseInt(partidoIdStr);
                tarjetas = tarjetaService.listarTarjetasPorPartido(partidoId);
            } else {
                tarjetas = tarjetaService.listarTarjetas();
            }

            // Establecer atributos para la vista
            request.setAttribute("tarjetas", tarjetas);
            request.setAttribute("partidos", partidos);
            request.setAttribute("partidoSeleccionado", partidoIdStr);

            // Reenviar a la vista JSP
            request.getRequestDispatcher("/html/tarjetas.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error al procesar la solicitud", e);
        }
    }
}