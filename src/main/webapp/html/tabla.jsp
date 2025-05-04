<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gestorfutbol.entity.Torneo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gestorfutbol.dto.TablaPosicionesDTO" %>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Tabla de Posiciones</title>
  <link rel="stylesheet" href="/css/tabla.css" />
  <link href="https://fonts.googleapis.com/css2?family=Jaldi:wght@400;700&display=swap" rel="stylesheet">
</head>

<body>
<div class="aplicacion">
  <aside class="barra_lateral">
    <h2>
      <img src="/imagenes/pelota.png" class="icono" />
      FútbolManager
    </h2>
    <nav>
      <a href="/listarTorneos"><img src="/imagenes/trofeo.png" class="icono" /><span class="opciones">Torneos</span></a>
      <a href="/equipos"><img src="/imagenes/equipo.png" class="icono" /><span class="opciones">Equipos</span></a>
      <a href="/mostrarTablaPosiciones" class="activo"><img src="/imagenes/tabla.png" class="icono" /><span class="opciones">Tabla de Posiciones</span></a>
      <a href="/partidos"><img src="/imagenes/calendario.png" class="icono" /><span class="opciones">Partidos</span></a>
    </nav>
  </aside>

  <main class="contenido_principal">
    <div class="encabezado">
      <h1>Tabla de Posiciones</h1>
      <div class="selector_torneo">
        <form action="/mostrarTablaPosiciones" method="get">
          <select name="torneoId" onchange="this.form.submit()">
            <option value="">Seleccione un Torneo</option>
            <%
              List<Torneo> torneos = (List<Torneo>) request.getAttribute("torneos");
              Integer torneoSeleccionado = (Integer) request.getAttribute("torneoSeleccionado");

              for (Torneo t : torneos) {
            %>
            <option value="<%= t.getIdTorneo() %>" <%= (torneoSeleccionado != null && torneoSeleccionado == t.getIdTorneo()) ? "selected" : "" %> >
              <%= t.getNombre() %>
            </option>
            <% } %>
          </select>
        </form>
      </div>
    </div>

    <div class="tabla_posiciones">
      <table class="tabla_posiciones_equipo">
        <thead>
        <tr>
          <th>#</th>
          <th>Equipo</th>
          <th>PTS</th>
          <th>PJ</th>
          <th>PG</th>
          <th>PE</th>
          <th>PP</th>
          <th>DG</th>
          <th>GF</th>
          <th>GC</th>
        </tr>
        </thead>
        <tbody>
        <%
          List<TablaPosicionesDTO> posiciones = (List<TablaPosicionesDTO>) request.getAttribute("tablaPosiciones");
          String[] imagenes = {"barcelona.png", "madrid.png", "atleti.png", "argentina.png", "colombia.png", "ecuador.png"};
          int contador = 0;

          if (posiciones != null && !posiciones.isEmpty()) {
            int posicion = 1;
            for (TablaPosicionesDTO tp : posiciones) {
              String imagenActual = imagenes[contador % imagenes.length];
        %>
        <tr>
          <td><%= posicion %></td>
          <td>
            <div class="equipo_info">
              <img src="/imagenes/<%= imagenActual %>" class="icono_escudo" />
              <span><%= tp.getNombreEquipo() %></span>
            </div>
          </td>
          <td><%= tp.getPuntosAcumulados() %></td>
          <td><%= tp.getPartidosJugados() %></td>
          <td><%= tp.getPartidosGanados() %></td>
          <td><%= tp.getPartidosEmpatados() %></td>
          <td><%= tp.getPartidosPerdidos() %></td>
          <td><%= tp.getDiferenciaGoles() %></td>
          <td><%= tp.getGolesAFavor() %></td>
          <td><%= tp.getGolesEnContra() %></td>
        </tr>
        <%
            posicion++;
            contador++;
          }
        } else {
        %>
        <tr>
          <td colspan="10">No hay datos disponibles para este torneo.</td>
        </tr>
        <% } %>
        </tbody>
      </table>
    </div>

  </main>
</div>
</body>
</html>
