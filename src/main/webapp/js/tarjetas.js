document.addEventListener('DOMContentLoaded', function() {
    // Selectores para filtrado
    const filtroTipo = document.getElementById('filtroTipo');
    const buscarTarjeta = document.getElementById('buscarTarjeta');

    // Filtrado por tipo de tarjeta
    if (filtroTipo) {
        filtroTipo.addEventListener('change', function() {
            aplicarFiltros();
        });
    }

    // Búsqueda de tarjetas por texto
    if (buscarTarjeta) {
        buscarTarjeta.addEventListener('input', function() {
            aplicarFiltros();
        });
    }

    // Función para aplicar todos los filtros combinados
    function aplicarFiltros() {
        const terminoBusqueda = buscarTarjeta ? buscarTarjeta.value.toLowerCase().trim() : '';
        const tipoSeleccionado = filtroTipo ? filtroTipo.value.toLowerCase() : '';
        const tarjetas = document.querySelectorAll('.tarjeta_sancion');

        // Eliminar mensajes previos si existen
        const mensajeExistente = document.querySelector('.mensaje-no-resultados');
        if (mensajeExistente) {
            mensajeExistente.remove();
        }

        let contadorVisibles = 0;

        tarjetas.forEach(tarjeta => {
            const nombreJugador = tarjeta.querySelector('.nombre_jugador').textContent.toLowerCase();
            const equipoJugador = tarjeta.querySelector('.equipo_jugador').textContent.toLowerCase();
            const motivoTarjeta = tarjeta.querySelector('.motivo_tarjeta').textContent.toLowerCase();

            const cumpleTipo = tipoSeleccionado === '' || tarjeta.classList.contains(tipoSeleccionado);
            const cumpleBusqueda = terminoBusqueda === '' ||
                nombreJugador.includes(terminoBusqueda) ||
                equipoJugador.includes(terminoBusqueda) ||
                motivoTarjeta.includes(terminoBusqueda);

            if (cumpleBusqueda && cumpleTipo) {
                tarjeta.style.display = 'block';
                contadorVisibles++;
            } else {
                tarjeta.style.display = 'none';
            }
        });

        // Mostrar mensaje cuando no hay resultados
        if (contadorVisibles === 0 && (terminoBusqueda || tipoSeleccionado)) {
            mostrarMensajeNoResultados(terminoBusqueda, tipoSeleccionado);
        }
    }

    // Función para mostrar mensaje cuando no hay resultados
    function mostrarMensajeNoResultados(terminoBusqueda, tipoSeleccionado) {
        const contenedorTarjetas = document.querySelector('.contenedor_tarjetas');
        const mensaje = document.createElement('p');
        mensaje.className = 'mensaje-no-resultados';
        mensaje.style.width = '100%';
        mensaje.style.textAlign = 'center';
        mensaje.style.padding = '20px';

        if (terminoBusqueda && tipoSeleccionado) {
            mensaje.textContent = `No se encontraron tarjetas que coincidan con "${terminoBusqueda}" y sean de tipo "${tipoSeleccionado}"`;
        } else if (terminoBusqueda) {
            mensaje.textContent = `No se encontraron tarjetas que coincidan con "${terminoBusqueda}"`;
        } else if (tipoSeleccionado) {
            mensaje.textContent = `No hay tarjetas de tipo "${tipoSeleccionado}"`;
        }
        contenedorTarjetas.appendChild(mensaje);
    }
});