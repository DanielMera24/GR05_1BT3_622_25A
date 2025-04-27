<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gestorfutbol.entity.Equipo" %>
<%@ page import="java.util.List" %>


<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Equipos</title>
  <link rel="stylesheet" href="/css/equipos.css" />
</head>
<body>
<div class="aplicacion">
  <aside class="barra_lateral">
    <h2>
      <img src="/imagenes/pelota.png" class="icono" />
      FútbolManager
    </h2>
    <nav>
      <a href="/listarTorneos">
        <img src="/imagenes/trofeo.png" class="icono" />
        Torneos
      </a>
      <a href="/mostrarEquipos" class="activo">
        <img src="/imagenes/equipo.png" class="icono" />
        Equipos
      </a>
      <a href="/html/tabla.html">
        <img src="/imagenes/tabla.png" class="icono" />
        Tabla de Posiciones
      </a>
      <a href="/html/partidos.html">
        <img src="/imagenes/calendario.png" class="icono" />
        Partidos
      </a>
    </nav>
  </aside>

  <%
    List<Equipo> equipos = (List<Equipo>) request.getAttribute("equipos");
    String test = "";
    if (equipos == null){
      test = "Nulo";
    }else{
      test = "No nulo";
    }
  %>
  <h1><%=test %></h1>




  <main class="contenido_principal">
    <div class="encabezado">
      <h1>Equipos</h1>
      <button class="boton_nuevo" id="btnNuevoEquipo">+ Nuevo Equipo</button>
    </div>

    <div class="tabla_contenedor">
      <table class="tabla_equipos">
        <thead>
        <tr>
          <th>Nombre</th>
          <th>Ciudad</th>
          <th>Estadio</th>
          <th>Acciones</th>
        </tr>
        </thead>
        <tbody>

        <% for(Equipo e: equipos) {%>
        <tr>
          <td>
            <div class="equipo_info">
              <img src="/imagenes/barcelona.png" class="icono_escudo" />
              <div>
                <strong>FCB</strong><br>
                <%= e.getNombre()%>
              </div>
            </div>
          </td>
          <td> <%= e.getCiudad()%></td>
          <td><%= e.getEstadio()%> </td>
          <td>
            <button class="accion_enlace ver-equipo">Ver</button>
            <button class="accion_enlace editar-equipo">Editar</button>
          </td>
        </tr>
        <%}%>


        </tbody>
      </table>
    </div>
  </main>
</div>

<!-- Modal para nuevo equipo -->
<div id="modalNuevoEquipo" class="modal">
  <div class="modal_contenido">
    <span class="cerrar" id="cerrarNuevoEquipo">&times;</span>
    <h2 id="tituloNuevoEquipo">Nuevo Equipo</h2>
    <form id="formNuevoEquipo" class="formulario_equipo" action = "/crearEquipo" method="post">
      <div class="campo">
        <label for="nombreNuevoEquipo">Nombre del Equipo</label>
        <input type="text" id="nombreNuevoEquipo" name="nombreNuevoEquipo" required />
      </div>
      <div class="campo">
        <label for="inicialesNuevoEquipo">Iniciales</label>
        <input type="text" id="inicialesNuevoEquipo" name="inicialesNuevoEquipo" maxlength="3" required />
      </div>
      <div class="campo">
        <label for="ciudadNuevoEquipo">Ciudad</label>
        <input type="text" id="ciudadNuevoEquipo" name="ciudadNuevoEquipo" required />
      </div>
      <div class="campo">
        <label for="estadioNuevoEquipo">Estadio/Cancha</label>
        <input type="text" id="estadioNuevoEquipo" name="estadioNuevoEquipo" required />
      </div>
      <button type="submit" class="boton_submit">Guardar Equipo</button>
    </form>
  </div>
</div>


<!-- Modal Editar Jugadores -->
<div id="modalEditarJugadores" class="modal">
  <div class="modal_contenido">
    <span id="cerrarEditarJugadores" class="cerrar">&times;</span>
    <h2>Editar Jugadores</h2>
    <form id="formJugadores">
      <div id="contenedorJugadores">
        <!-- Aquí se agregarán dinámicamente los jugadores -->
      </div>
      <div class="form_agregar">
        <input type="text" id="nombreJugador" placeholder="Nombre del jugador" name="nombreJugador" required>
        <input type="number" id="numeroJugador" placeholder="Número de camiseta" name="numeroJugador" required>
        <button type="button" id="agregarJugador">Agregar Jugador</button>
      </div>
      <button type="submit" class="boton_guardar">Guardar Cambios</button>
    </form>
  </div>
</div>


<script src="/js/equipo.js"></script>

</body>
</html>