<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gestorfutbol.dto.PartidoDTO" %>
<%@ page import="com.gestorfutbol.dto.EquipoDTO" %>
<%@ page import="com.gestorfutbol.dto.JugadorDTO" %>
<%@ page import="com.gestorfutbol.dto.TarjetaDTO" %>
<%@ page import="java.util.List" %>

<%
  PartidoDTO partido = (PartidoDTO) request.getAttribute("partido");
  EquipoDTO equipoLocal = (EquipoDTO) request.getAttribute("equipoLocal");
  EquipoDTO equipoVisitante = (EquipoDTO) request.getAttribute("equipoVisitante");
  List<JugadorDTO> jugadoresLocal = (List<JugadorDTO>) request.getAttribute("jugadoresLocal");
  List<JugadorDTO> jugadoresVisitante = (List<JugadorDTO>) request.getAttribute("jugadoresVisitante");
  List<TarjetaDTO> tarjetas = (List<TarjetaDTO>) request.getAttribute("tarjetas");
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
        <h1>Editar Encuentro</h1>
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
            <select id="estadoPartido" name="estadoPartido">
              <option value="Pendiente" <%= "Pendiente".equals(partido.getEstado()) ? "selected" : "" %>>Pendiente</option>
              <option value="En curso" <%= "En curso".equals(partido.getEstado()) ? "selected" : "" %>>En curso</option>
              <option value="Finalizado" <%= "Finalizado".equals(partido.getEstado()) ? "selected" : "" %>>Finalizado</option>
            </select>
          </div>
          <div class="campo_formulario">
            <label for="estadioPartido">Estadio</label>
            <input type="text" id="estadioPartido" name="estadioPartido" value="<%= equipoLocal.getEstadio() %>" />
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
            <select class="select_goles" name="golesLocal">
              <% for(int i = 0; i <= 5; i++) { %>
              <option value="<%= i %>" <%= partido.getGolesLocal() == i ? "selected" : "" %>><%= i %></option>
              <% } %>
            </select>
          </div>
          <div class="separador_marcador">-</div>
          <div class="marcador_equipo">
            <select class="select_goles" name="golesVisitante">
              <% for(int i = 0; i <= 5; i++) { %>
              <option value="<%= i %>" <%= partido.getGolesVisita() == i ? "selected" : "" %>><%= i %></option>
              <% } %>
            </select>
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
              <button type="button" class="btn_agregar_jugador" data-equipo="local">
                + Agregar Jugador
              </button>
            </div>

            <div class="tabla_jugadores">
              <table class="tabla_participantes">
                <thead>
                <tr>
                  <th>Dorsal</th>
                  <th>Jugador</th>
                  <th>Goles</th>
                  <th>Capitán</th>
                  <th>Acciones</th>
                </tr>
                </thead>
                <tbody id="jugadoresLocal">
                <!-- Los jugadores se cargarán dinámicamente aquí -->
                </tbody>
              </table>
            </div>
          </div>

          <!-- Equipo Visitante -->
          <div class="equipo_participantes">
            <div class="header_equipo_participantes">
              <img src="/imagenes/<%= equipoVisitante.getSiglas() %>.png" class="icono_equipo" />
              <h3><%= equipoVisitante.getNombre() %></h3>
              <button type="button" class="btn_agregar_jugador" data-equipo="visitante">
                + Agregar Jugador
              </button>
            </div>

            <div class="tabla_jugadores">
              <table class="tabla_participantes">
                <thead>
                <tr>
                  <th>Dorsal</th>
                  <th>Jugador</th>
                  <th>Goles</th>
                  <th>Capitán</th>
                  <th>Acciones</th>
                </tr>
                </thead>
                <tbody id="jugadoresVisitante">
                <!-- Los jugadores se cargarán dinámicamente aquí -->
                </tbody>
              </table>
            </div>
          </div>
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
            <button type="button" class="eliminar_tarjeta">×</button>
          </div>
          <% } } %>
        </div>
        <button type="button" class="btn_agregar_tarjeta" id="agregarTarjeta">
          + Nueva Tarjeta
        </button>
      </div>

      <!-- Botones de acción -->
      <div class="acciones_formulario">
        <a href="/partidos" class="btn_cancelar">Cancelar</a>
        <button type="submit" class="btn_guardar">Guardar Cambios</button>
      </div>
    </form>
  </main>
</div>

<!-- Modal para agregar jugador -->
<div class="modal" id="modalAgregarJugador">
  <div class="modal_contenido">
    <span class="cerrar_modal">&times;</span>
    <h2>Agregar Jugador al Partido</h2>
    <form id="formAgregarJugador">
      <div class="campo_modal">
        <label for="jugadorSelect">Seleccionar Jugador</label>
        <select id="jugadorSelect" required>
          <option value="">Seleccione un jugador</option>
        </select>
      </div>
      <div class="campo_modal">
        <label for="golesJugador">Goles</label>
        <input type="number" id="golesJugador" min="0" max="10" value="0" />
      </div>
      <div class="acciones_modal">
        <button type="button" class="btn_cancelar_modal">Cancelar</button>
        <button type="submit" class="btn_agregar_modal">Agregar</button>
      </div>
    </form>
  </div>
</div>

<!-- Modal para agregar tarjeta -->
<div class="modal" id="modalAgregarTarjeta">
  <div class="modal_contenido">
    <span class="cerrar_modal">&times;</span>
    <h2>Nueva Tarjeta</h2>
    <form id="formAgregarTarjeta">
      <div class="campo_modal">
        <label for="tipoTarjeta">Tipo de Tarjeta</label>
        <select id="tipoTarjeta" required>
          <option value="">Seleccione un tipo</option>
          <option value="AMARILLA">Amarilla</option>
          <option value="ROJA">Roja</option>
        </select>
      </div>
      <div class="campo_modal">
        <label for="jugadorTarjeta">Jugador</label>
        <select id="jugadorTarjeta" required>
          <option value="">Seleccione un jugador</option>
        </select>
      </div>
      <div class="campo_modal">
        <label for="minutoTarjeta">Minuto</label>
        <input type="number" id="minutoTarjeta" min="1" max="90" required />
      </div>
      <div class="campo_modal">
        <label for="motivoTarjeta">Motivo</label>
        <textarea id="motivoTarjeta" rows="3" required></textarea>
      </div>
      <div class="acciones_modal">
        <button type="button" class="btn_cancelar_modal">Cancelar</button>
        <button type="submit" class="btn_agregar_modal">Añadir Tarjeta</button>
      </div>
    </form>
  </div>
</div>

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
</script>

<script src="/js/editar-partido.js"></script>
</body>
</html>