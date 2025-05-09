<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gestorfutbol.entity.Jugador" %>
<%@ page import="com.gestorfutbol.entity.Equipo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gestorfutbol.dto.EquipoDTO" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Jugadores</title>
    <link rel="stylesheet" href="/css/jugadores.css" />
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
            <a href="/mostrarTablaPosiciones"><img src="/imagenes/tabla.png" class="icono" /><span class="opciones">Tabla de Posiciones</span></a>
            <a href="/partidos"><img src="/imagenes/calendario.png" class="icono" /><span class="opciones">Partidos</span></a>
            <a href="/jugadores" class="activo"><img src="/imagenes/jugador.png" class="icono" /><span class="opciones">Jugadores</span></a>
        </nav>
    </aside>

    <main class="contenido_principal">
        <div class="encabezado">
            <h1>Jugadores</h1>
            <div class="acciones_jugadores">
                <div class="selector_equipo">
                    <form action="/jugadores" method="get">
                        <select name="equipoId" onchange="this.form.submit()">
                            <option value="">Todos los equipos</option>
                            <!-- Aquí iría un bucle para mostrar los equipos disponibles -->
                        </select>
                    </form>
                </div>
                <button class="boton_nuevo" >
                    + Nuevo Jugador
                </button>
            </div>
        </div>

        <div class="filtros_jugadores">
            <div class="buscador">
                <input type="text" placeholder="Buscar jugador..." id="buscarJugador">
                <img src="/imagenes/buscar.png" class="icono_buscar" />
            </div>
            <div class="filtro_posicion">
                <select id="filtroPosicion">
                    <option value="">Todas las posiciones</option>
                    <option value="Portero">Portero</option>
                    <option value="Defensa">Defensa</option>
                    <option value="Centrocampista">Centrocampista</option>
                    <option value="Delantero">Delantero</option>
                </select>
            </div>
        </div>

        <div class="contenedor_tarjetas">
            <!-- Aquí irían las tarjetas de jugadores generadas dinámicamente -->

            <!-- Ejemplo de tarjeta de jugador -->
            <div class="tarjeta_jugador">
                <div class="cabecera_tarjeta">
                    <div class="numero_dorsal">10</div>
                    <div class="equipo_badge">
                        <img src="/imagenes/barcelona.png" class="icono_escudo" />
                        <span>FC Barcelona</span>
                    </div>
                </div>
                <div class="cuerpo_tarjeta">
                    <div class="avatar_jugador">
                        <img src="/imagenes/jugador.png" alt="Avatar del jugador" />
                    </div>
                    <h3 class="nombre_jugador">Lionel Messi</h3>
                    <p class="posicion_jugador">Delantero</p>
                </div>
                <div class="pie_tarjeta">
                    <button class="accion editar">Editar</button>
                    <button class="accion eliminar">Eliminar</button>
                </div>
            </div>

            <!-- Ejemplo de otra tarjeta de jugador -->
            <div class="tarjeta_jugador">
                <div class="cabecera_tarjeta">
                    <div class="numero_dorsal">4</div>
                    <div class="equipo_badge">
                        <img src="/imagenes/madrid.png" class="icono_escudo" />
                        <span>Real Madrid</span>
                    </div>
                </div>
                <div class="cuerpo_tarjeta">
                    <div class="avatar_jugador">
                        <img src="/imagenes/jugador.png" alt="Avatar del jugador" />
                    </div>
                    <h3 class="nombre_jugador">Sergio Ramos</h3>
                    <p class="posicion_jugador">Defensa</p>
                </div>
                <div class="pie_tarjeta">
                    <button class="accion editar">Editar</button>
                    <button class="accion eliminar">Eliminar</button>
                </div>
            </div>

            <!-- Más tarjetas de ejemplo -->
            <div class="tarjeta_jugador">
                <div class="cabecera_tarjeta">
                    <div class="numero_dorsal">1</div>
                    <div class="equipo_badge">
                        <img src="/imagenes/atleti.png" class="icono_escudo" />
                        <span>Atlético Madrid</span>
                    </div>
                </div>
                <div class="cuerpo_tarjeta">
                    <div class="avatar_jugador">
                        <img src="/imagenes/jugador.png" alt="Avatar del jugador" />
                    </div>
                    <h3 class="nombre_jugador">Jan Oblak</h3>
                    <p class="posicion_jugador">Portero</p>
                </div>
                <div class="pie_tarjeta">
                    <button class="accion editar">Editar</button>
                    <button class="accion eliminar">Eliminar</button>
                </div>
            </div>

            <div class="tarjeta_jugador">
                <div class="cabecera_tarjeta">
                    <div class="numero_dorsal">8</div>
                    <div class="equipo_badge">
                        <img src="/imagenes/argentina.png" class="icono_escudo" />
                        <span>Argentina</span>
                    </div>
                </div>
                <div class="cuerpo_tarjeta">
                    <div class="avatar_jugador">
                        <img src="/imagenes/jugador.png" alt="Avatar del jugador" />
                    </div>
                    <h3 class="nombre_jugador">Enzo Fernández</h3>
                    <p class="posicion_jugador">Centrocampista</p>
                </div>
                <div class="pie_tarjeta">
                    <button class="accion editar">Editar</button>
                    <button class="accion eliminar">Eliminar</button>
                </div>
            </div>

        </div>



    </main>
</div>
</body>
</html>