document.addEventListener('DOMContentLoaded', function() {
    // Variables para el modal
    const modalEditarTarjeta = document.getElementById('modalEditarTarjeta');
    const cerrarModalEditar = document.querySelector('.cerrar-modal-editar');
    const btnCancelarEditar = document.getElementById('btnCancelarEditar');

    // Selectores para filtrado
    const filtroTipo = document.getElementById('filtroTipo');
    const buscarTarjeta = document.getElementById('buscarTarjeta');

    // Eventos para modal de editar
    if (cerrarModalEditar) {
        cerrarModalEditar.addEventListener('click', function() {
            cerrarModalEditarTarjeta();
        });
    }

    if (btnCancelarEditar) {
        btnCancelarEditar.addEventListener('click', function() {
            cerrarModalEditarTarjeta();
        });
    }

    // Cerrar modal al hacer clic fuera
    window.addEventListener('click', function(event) {
        if (event.target === modalEditarTarjeta) {
            cerrarModalEditarTarjeta();
        }
    });

    function cerrarModalEditarTarjeta() {
        if (modalEditarTarjeta) {
            modalEditarTarjeta.style.display = 'none';
            document.body.style.overflow = 'auto';
        }
        if (document.getElementById('formEditarTarjeta')) {
            document.getElementById('formEditarTarjeta').reset();
        }
    }

    // Manejadores de botones editar
    document.addEventListener('click', function(event) {
        if (event.target.classList.contains('editar')) {
            event.preventDefault();
            event.stopPropagation();
            abrirModalEditar(event.target);
        }
    });

    function abrirModalEditar(boton) {
        const tarjetaData = {
            id: boton.getAttribute('data-id'),
            tipo: boton.getAttribute('data-tipo'),
            jugador: boton.getAttribute('data-jugador'),
            equipo: boton.getAttribute('data-equipo'),
            minuto: boton.getAttribute('data-minuto'),
            motivo: boton.getAttribute('data-motivo'),
            partido: boton.getAttribute('data-partido')
        };

        document.getElementById('idTarjetaEditar').value = tarjetaData.id || '';
        document.getElementById('tipoTarjetaEditar').value = tarjetaData.tipo || '';
        document.getElementById('minutoEditar').value = tarjetaData.minuto || '';
        document.getElementById('motivoEditar').value = tarjetaData.motivo || '';
        document.getElementById('partidoIdEditar').value = tarjetaData.partido || '';

        // Mostrar el modal
        modalEditarTarjeta.style.display = 'block';
        document.body.style.overflow = 'hidden';
    }

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