<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gestorfutbol.dto.PartidoDTO" %>
<%@ page import="com.gestorfutbol.dto.EquipoDTO" %>
<%@ page import="com.gestorfutbol.dto.JugadorDTO" %>
<%@ page import="com.gestorfutbol.dto.TarjetaDTO" %>
<%@ page import="com.gestorfutbol.dto.GolDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gestorfutbol.dto.DetallePartidoDTO" %>

<%
  PartidoDTO partido = (PartidoDTO) request.getAttribute("partido");
  EquipoDTO equipoLocal = (EquipoDTO) request.getAttribute("equipoLocal");
  EquipoDTO equipoVisitante = (EquipoDTO) request.getAttribute("equipoVisitante");
  List<JugadorDTO> jugadoresLocal = (List<JugadorDTO>) request.getAttribute("jugadoresLocal");
  List<JugadorDTO> jugadoresVisitante = (List<JugadorDTO>) request.getAttribute("jugadoresVisitante");
  List<TarjetaDTO> tarjetas = (List<TarjetaDTO>) request.getAttribute("tarjetas");
  List<GolDTO> goles = (List<GolDTO>) request.getAttribute("goles");
  List<DetallePartidoDTO> detallesEquipoLocal = (List<DetallePartidoDTO>) request.getAttribute("detallesEquipoLocal");
  List<DetallePartidoDTO> detallesEquipoVisitante = (List<DetallePartidoDTO>) request.getAttribute("detallesEquipoVisitante");
%>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Editar Partido - FútbolManager</title>
  <link rel="stylesheet" href="/css/editar_partido.css" />
  <link href="https://fonts.googleapis.com/css2?family=Jaldi:wght@400;700&display=swap" rel="stylesheet">
</head>

<body>
<div class="aplicacion">
  <!-- Sidebar idéntica a las otras páginas -->
  <aside class="barra_lateral">
    <h2>
      <img src="/imagenes/pelota.png" class="icono" />
      FútbolManager
    </h2>
    <nav>
      <a href="/listarTorneos">
        <img src="/imagenes/trofeo.png" class="icono" />
        <span class="opciones">Torneos</span>
      </a>
      <a href="/equipos">
        <img src="/imagenes/equipo.png" class="icono" />
        <span class="opciones">Equipos</span>
      </a>
      <a href="/mostrarTablaPosiciones">
        <img src="/imagenes/tabla.png" class="icono" />
        <span class="opciones">Tabla de Posiciones</span>
      </a>
      <a href="/partidos" class="activo">
        <img src="/imagenes/calendario.png" class="icono" />
        <span class="opciones">Partidos</span>
      </a>
      <a href="/jugadores">
        <img src="/imagenes/jugador.png" class="icono" />
        <span class="opciones">Jugadores</span>
      </a>
      <a href="/tarjetas">
        <img src="/imagenes/tarjetas.png" class="icono" />
        <span class="opciones">Tarjetas</span>
      </a>
    </nav>
  </aside>

  <main class="contenido_principal">
    <!-- Header con navegación de vuelta -->
    <div class="encabezado">
      <div class="navegacion">
        <a href="/partidos" class="btn_volver">
          <img src="/imagenes/flechaIzq.png" class="icono" />
          Volver a Partidos
        </a>
        <h1>Detalles del partido</h1>
      </div>
    </div>

    <!-- Información del partido -->
    <div class="info_partido_header">
      <div class="equipos_enfrentamiento">
        <div class="equipo_header">
          <img src="/imagenes/<%= equipoLocal.getSiglas() %>.png" class="escudo_grande" />
          <span class="nombre_equipo_header"><%= equipoLocal.getNombre() %></span>
        </div>
        <div class="vs_header">VS</div>
        <div class="equipo_header">
          <img src="/imagenes/<%= equipoVisitante.getSiglas() %>.png" class="escudo_grande" />
          <span class="nombre_equipo_header"><%= equipoVisitante.getNombre() %></span>
        </div>
      </div>
      <div class="detalles_partido">
        <span class="torneo_info"><%= partido.getTorneo() %> • Jornada <%= partido.getJornadaActual() %></span>
        <span class="fecha_info"><%= partido.getFechaPartido() %></span>
      </div>
    </div>

    <form id="formEditarPartido" class="formulario_partido" action="/actualizarPartido" method="post">
      <input type="hidden" name="idPartido" value="<%= partido.getIdPartido() %>" />

      <!-- Sección de datos básicos del partido -->
      <div class="seccion_formulario">
        <h2>Detalles del Encuentro</h2>
        <div class="campos_grupo">
          <div class="campo_formulario">
            <label for="estadoPartido">Estado del Partido</label>
            <input type="text" id="estadoPartido" name="estadoPartido" value="<%= partido.getEstado() %>" readonly />
          </div>
          <div class="campo_formulario">
            <label for="estadioPartido">Estadio</label>
            <input type="text" id="estadioPartido" name="estadioPartido" value="<%= equipoLocal.getEstadio() %>" readonly />
          </div>
        </div>
      </div>

      <!-- Sección de marcador -->
      <div class="seccion_formulario">
        <h2>Marcador</h2>
        <div class="marcador_section">
          <div class="marcador_equipo">
            <img src="/imagenes/<%= equipoLocal.getSiglas() %>.png" class="icono_marcador" />
            <span class="nombre_equipo_marcador"><%= equipoLocal.getNombre() %></span>
            <span class="select_goles"><%= partido.getGolesLocal() %></span>
          </div>

          <div class="separador_marcador">-</div>
          <div class="marcador_equipo">
            <span class="select_goles"><%= partido.getGolesVisita() %></span>
            <span class="nombre_equipo_marcador"><%= equipoVisitante.getNombre() %></span>
            <img src="/imagenes/<%= equipoVisitante.getSiglas() %>.png" class="icono_marcador" />
          </div>
        </div>
      </div>

      <!-- Sección de jugadores participantes -->
      <div class="seccion_formulario">
        <h2>Jugadores Participantes</h2>

        <div class="separador">
          <!-- Equipo Local -->
          <div class="equipo_participantes">
            <div class="header_equipo_participantes">
              <img src="/imagenes/<%= equipoLocal.getSiglas() %>.png" class="icono_equipo" />
              <h3><%= equipoLocal.getNombre() %></h3>
            </div>

            <div class="tabla_jugadores">
              <table class="tabla_participantes">
                <thead>
                <tr>
                  <th>Dorsal</th>
                  <th>Jugador</th>
                  <th>Capitán</th>
                </tr>
                </thead>
                <tbody id="jugadoresLocal">
                <% if (detallesEquipoLocal != null && !detallesEquipoLocal.isEmpty()) {
                  for (DetallePartidoDTO detalle : detallesEquipoLocal) { %>
                <tr data-jugador-id="<%= detalle.getIdJugador() %>">
                  <td><%= detalle.getDorsal() %></td>
                  <td><%= detalle.getNombreJugador() %></td>
                  <td>
                    <input type="radio" name="capitanLocal" value="<%= detalle.getIdJugador() %>"
                           <% if(detalle.isEsCapitan()) { %>checked<% } %> disabled />
                  </td>

                </tr>
                <% } } %>
                </tbody>
              </table>
            </div>
          </div>

          <!-- Equipo Visitante -->
          <div class="equipo_participantes">
            <div class="header_equipo_participantes">
              <img src="/imagenes/<%= equipoVisitante.getSiglas() %>.png" class="icono_equipo" />
              <h3><%= equipoVisitante.getNombre() %></h3>
            </div>

            <div class="tabla_jugadores">
              <table class="tabla_participantes">
                <thead>
                <tr>
                  <th>Dorsal</th>
                  <th>Jugador</th>
                  <th>Capitán</th>
                </tr>
                </thead>
                <tbody id="jugadoresVisitante">
                <% if (detallesEquipoVisitante != null && !detallesEquipoVisitante.isEmpty()) {
                  for (DetallePartidoDTO detalle : detallesEquipoVisitante) { %>
                <tr data-jugador-id="<%= detalle.getIdJugador() %>">
                  <td><%= detalle.getDorsal() %></td>
                  <td><%= detalle.getNombreJugador() %></td>
                  <td>
                    <input type="radio" name="capitanVisitante" value="<%= detalle.getIdJugador() %>"
                           <% if(detalle.isEsCapitan()) { %>checked<% } %> disabled />
                  </td>

                </tr>
                <% } } %>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>

      <!-- Sección de goles -->
      <div class="seccion_formulario">
        <h2>Goles del Partido</h2>
        <div class="contenedor_goles_partido" id="contenedorGoles">
          <!-- Mostrar goles existentes -->
          <% if (goles != null && !goles.isEmpty()) {
            for (GolDTO gol : goles) {
          %>
          <div class="gol_partido">
            <div class="indicador_gol">⚽</div>
            <div class="info_gol">
              <div class="nombre_jugador"><%= gol.getNombreJugador() %></div>
              <div class="equipo_jugador"><%= gol.getEquipoJugador() %></div>
              <div class="detalle_gol">Minuto <%= gol.getMinuto() %>'</div>
            </div>
          </div>
          <% } } %>
        </div>

      </div>

      <!-- Sección de tarjetas -->
      <div class="seccion_formulario">
        <h2>Tarjetas del Partido</h2>
        <div class="contenedor_tarjetas_partido" id="contenedorTarjetas">
          <!-- Mostrar tarjetas existentes -->
          <% if (tarjetas != null && !tarjetas.isEmpty()) {
            for (TarjetaDTO tarjeta : tarjetas) {
              String tipoClase = tarjeta.getTipoTarjeta().equalsIgnoreCase("AMARILLA") ? "amarilla" : "roja";
          %>
          <div class="tarjeta_partido <%= tipoClase %>">
            <div class="indicador_tarjeta <%= tipoClase %>"></div>
            <div class="info_tarjeta">
              <div class="nombre_jugador"><%= tarjeta.getNombreJugador() %></div>
              <div class="equipo_jugador"><%= tarjeta.getEquipoJugador() %></div>
              <div class="detalle_tarjeta">Minuto <%= tarjeta.getMinuto() %>' - <%= tarjeta.getMotivo() %></div>
            </div>
          </div>
          <% } } %>
        </div>

      </div>

      <!-- Botones de acción -->
      <div class="acciones_formulario">
        <button id="btnExportarPDF" class="btn_guardar">Exportar a PDF</button>
        <a href="#" class="btn_cancelar">Volver</a>
      </div>
    </form>
  </main>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.min.js"></script>

<!-- Variables JavaScript para los datos -->
<script>
  window.partidoData = {
    id: <%= partido.getIdPartido() %>,
    equipoLocal: {
      id: <%= equipoLocal.getIdEquipo() %>,
      nombre: "<%= equipoLocal.getNombre() %>",
      siglas: "<%= equipoLocal.getSiglas() %>"
    },
    equipoVisitante: {
      id: <%= equipoVisitante.getIdEquipo() %>,
      nombre: "<%= equipoVisitante.getNombre() %>",
      siglas: "<%= equipoVisitante.getSiglas() %>"
    }
  };

  window.jugadoresLocal = [
    <% if (jugadoresLocal != null) {
        for (int i = 0; i < jugadoresLocal.size(); i++) {
            JugadorDTO jugador = jugadoresLocal.get(i);
    %>
    {
      id: <%= jugador.getIdJugador() %>,
      nombre: "<%= jugador.getNombre() %>",
      dorsal: <%= jugador.getDorsal() %>,
      posicion: "<%= jugador.getPosicion() %>"
    }<%= (i < jugadoresLocal.size() - 1) ? "," : "" %>
    <% } } %>
  ];

  window.jugadoresVisitante = [
    <% if (jugadoresVisitante != null) {
        for (int i = 0; i < jugadoresVisitante.size(); i++) {
            JugadorDTO jugador = jugadoresVisitante.get(i);
    %>
    {
      id: <%= jugador.getIdJugador() %>,
      nombre: "<%= jugador.getNombre() %>",
      dorsal: <%= jugador.getDorsal() %>,
      posicion: "<%= jugador.getPosicion() %>"
    }<%= (i < jugadoresVisitante.size() - 1) ? "," : "" %>
    <% } } %>
  ];
  // Inicializar jugadores participantes con los detalles existentes
  window.jugadoresParticipantes = [
    <% if (detallesEquipoLocal != null) {
        for (int i = 0; i < detallesEquipoLocal.size(); i++) {
            DetallePartidoDTO detalle = detallesEquipoLocal.get(i);
    %>
    {
      id: <%= detalle.getIdJugador() %>,
      nombre: "<%= detalle.getNombreJugador() %>",
      dorsal: <%= detalle.getDorsal() %>,
      equipoId: <%= detalle.getIdEquipo() %>,
      equipo: "local",
      esCapitan: <%= detalle.isEsCapitan() %>
    }<%= (i < detallesEquipoLocal.size() - 1 || detallesEquipoVisitante != null && !detallesEquipoVisitante.isEmpty()) ? "," : "" %>
    <% } } %>
    <% if (detallesEquipoVisitante != null) {
        for (int i = 0; i < detallesEquipoVisitante.size(); i++) {
            DetallePartidoDTO detalle = detallesEquipoVisitante.get(i);
    %>
    {
      id: <%= detalle.getIdJugador() %>,
      nombre: "<%= detalle.getNombreJugador() %>",
      dorsal: <%= detalle.getDorsal() %>,
      equipoId: <%= detalle.getIdEquipo() %>,
      equipo: "visitante",
      esCapitan: <%= detalle.isEsCapitan() %>
    }<%= (i < detallesEquipoVisitante.size() - 1) ? "," : "" %>
    <% } } %>
  ];

  // Inicializar goles con los existentes
  window.golesPartido = [
    <% if (goles != null) {
        for (int i = 0; i < goles.size(); i++) {
            GolDTO gol = goles.get(i);
    %>
    {
      jugadorId: <%= gol.getIdJugador() %>,
      jugador: "<%= gol.getNombreJugador() %>",
      dorsal: <%= gol.getDorsalJugador() %>,
      equipo: "<%= gol.getEquipoJugador() %>",
      minuto: <%= gol.getMinuto() %>
    }<%= (i < goles.size() - 1) ? "," : "" %>
    <% } } %>
  ];

  // Inicializar tarjetas con las existentes
  window.tarjetasPartido = [
    <% if (tarjetas != null) {
        for (int i = 0; i < tarjetas.size(); i++) {
            TarjetaDTO tarjeta = tarjetas.get(i);
    %>
    {
      tipo: "<%= tarjeta.getTipoTarjeta() %>",
      jugadorId: <%= tarjeta.getIdJugador() %>,
      jugador: "<%= tarjeta.getNombreJugador() %>",
      dorsal: <%= tarjeta.getDorsalJugador() %>,
      equipo: "<%= tarjeta.getEquipoJugador() %>",
      minuto: <%= tarjeta.getMinuto() %>,
      motivo: "<%= tarjeta.getMotivo() %>"
    }<%= (i < tarjetas.size() - 1) ? "," : "" %>
    <% } } %>
  ];
</script>

<script src="/js/verPartido.js"></script>

</body>
</html>