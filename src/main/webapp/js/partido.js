document.addEventListener('DOMContentLoaded', function() {
    const modalNuevo = document.getElementById('modalNuevoPartido');
    const modalDetalle = document.getElementById('modalDetallePartido');

    const btnAbrirNuevo = document.getElementById('abrirModal');
    const cerrarModalButtons = document.querySelectorAll('.cerrar-modal');
    const cerrarDetalleButton = document.querySelector('.cerrar-modal-detalle');

    const formNuevo = document.getElementById('formNuevoPartido');
    const formDetalle = document.getElementById('formDetallePartido');

    const torneoSelect = document.getElementById('torneo');
    const localSelect = document.getElementById('equipoLocal');
    const visitanteSelect = document.getElementById('equipoVisitante');

    const jornadaInput = document.getElementById('jornada');
    const fechaInput = document.getElementById('fecha');
    const estadioInput = document.getElementById('estadio');

    // Obtener botones de editar y ver detalles
    const botonesEditar = document.querySelectorAll('.accion_enlace');
    const botonesVer = document.querySelectorAll('.boton_ver');

    // Almacena las tarjetas por partido durante la sesión
    let tarjetasPorPartido = {};

    // Variable para controlar si el modal está en modo "solo lectura"
    let modoSoloLectura = false;

    if (btnAbrirNuevo && modalNuevo) {
        btnAbrirNuevo.addEventListener('click', () => modalNuevo.style.display = 'block');
    }

    cerrarModalButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            modalNuevo.style.display = 'none';
        });
    });

    // Función para abrir el modal de detalle
    function abrirModalDetalle(event, soloLectura) {
        event.preventDefault();
        modoSoloLectura = soloLectura;
        modalDetalle.style.display = 'block';

        const btn = event.currentTarget;
        const nombreLocal = btn.getAttribute('data-local');
        const nombreVisitante = btn.getAttribute('data-visitante');
        const golesLocal = btn.getAttribute('data-goles-local');
        const golesVisitante = btn.getAttribute('data-goles-visitante');
        const nombreTorneo = btn.getAttribute('data-torneo');
        const jornada = btn.getAttribute('data-jornada');
        const estado = btn.getAttribute('data-estado');
        const idPartido = btn.getAttribute('data-id');

        document.getElementById('idPartido').value = idPartido;

        document.querySelector('#formDetallePartido p:nth-child(1)').innerHTML =
            `<strong>${nombreLocal}</strong> vs <strong>${nombreVisitante}</strong>`;
        document.querySelector('#formDetallePartido p:nth-child(2)').innerText =
            `${nombreTorneo} · Jornada ${jornada}`;

        const siglaLocal = obtenerSiglaEquipo(nombreLocal);
        const siglaVisitante = obtenerSiglaEquipo(nombreVisitante);

        document.getElementById('foto-local').src = `/imagenes/${siglaLocal}.png`;
        document.getElementById('foto-visitante').src = `/imagenes/${siglaVisitante}.png`;

        // Actualizar selectores
        const selectGolesLocal = document.querySelector('select[name="golesLocal"]');
        const selectGolesVisitante = document.querySelector('select[name="golesVisitante"]');
        const selectEstado = document.getElementById('estadoPartido');

        if (selectGolesLocal && selectGolesVisitante && selectEstado) {
            selectGolesLocal.value = golesLocal;
            selectGolesVisitante.value = golesVisitante;
            selectEstado.value = estado;

            // Deshabilitar controles en modo solo lectura
            // Deshabilitar controles en modo solo lectura
            if (soloLectura) {
                selectGolesLocal.disabled = true;
                selectGolesVisitante.disabled = true;
                selectEstado.disabled = true;

                // Ocultar botón de añadir tarjeta y el div de acciones completo
                const btnAgregarTarjeta = document.getElementById('agregarTarjeta');
                const divAcciones = document.querySelector('#formDetallePartido .acciones');

                if (btnAgregarTarjeta) btnAgregarTarjeta.style.display = 'none';
                if (divAcciones) divAcciones.style.display = 'none';

                // Cambiar el título del modal
                const tituloModal = document.querySelector('.modal-contenido-detalle h2');
                if (tituloModal) tituloModal.textContent = 'Información del Partido';
            } else {
                selectGolesLocal.disabled = false;
                selectGolesVisitante.disabled = false;
                selectEstado.disabled = false;

                // Mostrar botón de añadir tarjeta y el div de acciones
                const btnAgregarTarjeta = document.getElementById('agregarTarjeta');
                const divAcciones = document.querySelector('#formDetallePartido .acciones');

                if (btnAgregarTarjeta) btnAgregarTarjeta.style.display = 'block';
                if (divAcciones) divAcciones.style.display = 'flex';

                // Restaurar el título original
                const tituloModal = document.querySelector('.modal-contenido-detalle h2');
                if (tituloModal) tituloModal.textContent = 'Detalles de encuentro';
            }
        }

        // Inicializar las referencias para tarjetas
        btnAgregarTarjeta = document.getElementById('agregarTarjeta');
        modalNuevaTarjeta = document.getElementById('modalNuevaTarjeta');
        btnCancelarTarjeta = document.getElementById('cancelarTarjeta');
        btnGuardarTarjeta = document.getElementById('guardarTarjeta');
        contenedorTarjetas = document.getElementById('contenedorTarjetas');

        // Asegurarse de que el modal de tarjetas esté cerrado inicialmente
        if (modalNuevaTarjeta) {
            modalNuevaTarjeta.style.display = 'none';
        }

        // Configurar el botón de agregar tarjeta
        if (btnAgregarTarjeta && !soloLectura) {
            btnAgregarTarjeta.onclick = function() {
                modalNuevaTarjeta.style.display = 'block';
                cargarJugadoresDePartido();
            };
        }

        // Configurar el botón de cancelar
        if (btnCancelarTarjeta) {
            btnCancelarTarjeta.onclick = function() {
                modalNuevaTarjeta.style.display = 'none';
                limpiarFormularioTarjeta();
            };
        }

        // Configurar el botón de guardar tarjeta
        if (btnGuardarTarjeta) {
            btnGuardarTarjeta.onclick = function() {
                guardarTarjeta();
            };
        }

        // Cargar tarjetas previas si existen para este partido
        if (contenedorTarjetas) {
            // Limpiar contenedor primero
            contenedorTarjetas.innerHTML = '';

            // Recrear cada tarjeta visual si hay tarjetas guardadas para este partido
            if (tarjetasPorPartido[idPartido]) {
                tarjetasPorPartido[idPartido].forEach(tarjeta => {
                    const tarjetaDiv = document.createElement('div');
                    tarjetaDiv.className = `tarjeta-partido ${tarjeta.tipo.toLowerCase()}`;
                    tarjetaDiv.id = `tarjeta-${tarjeta.idVisual}`;

                    // En modo solo lectura, no mostrar botón de eliminar
                    let btnEliminarHTML = soloLectura ? '' : `<button type="button" class="eliminar-tarjeta" data-id="${tarjeta.idVisual}">×</button>`;

                    tarjetaDiv.innerHTML = `
                        <div class="indicador-tarjeta ${tarjeta.tipo.toLowerCase()}"></div>
                        <div class="info-tarjeta">
                            <div class="nombre-jugador">${tarjeta.nombreJugador} #${tarjeta.dorsalJugador}</div>
                            <div class="equipo-jugador">${tarjeta.equipoJugador}</div>
                            <div class="detalle-tarjeta">Min: ${tarjeta.minuto}' - ${tarjeta.motivo}</div>
                        </div>
                        ${btnEliminarHTML}
                    `;

                    contenedorTarjetas.appendChild(tarjetaDiv);

                    // Agregar evento al botón de eliminar (solo si no estamos en modo solo lectura)
                    if (!soloLectura) {
                        const btnEliminar = tarjetaDiv.querySelector('.eliminar-tarjeta');
                        if (btnEliminar) {
                            btnEliminar.addEventListener('click', function() {
                                const idTarjeta = this.getAttribute('data-id');
                                if (confirm('¿Estás seguro de que deseas eliminar esta tarjeta?')) {
                                    document.getElementById(`tarjeta-${idTarjeta}`).remove();

                                    // También eliminar de nuestro objeto
                                    tarjetasPorPartido[idPartido] = tarjetasPorPartido[idPartido].filter(t => t.idVisual != idTarjeta);
                                }
                            });
                        }
                    }
                });
            }
        }
    }

    // Asignar evento de click a los botones de editar
    botonesEditar.forEach(btn => {
        btn.addEventListener('click', function(event) {
            abrirModalDetalle(event, false); // Modo edición
        });
    });

    // Asignar evento de click a los botones de ver detalles
    botonesVer.forEach(btn => {
        btn.addEventListener('click', function(event) {
            abrirModalDetalle(event, true); // Modo solo lectura
        });
    });

    function obtenerSiglaEquipo(nombreEquipo) {
        if (!window.equipos || !nombreEquipo) return '';

        const equipo = window.equipos.find(e => e.nombre === nombreEquipo);
        return equipo ? equipo.siglas : '';
    }

    if (cerrarDetalleButton && modalDetalle) {
        cerrarDetalleButton.addEventListener('click', () => {
            modalDetalle.style.display = 'none';
        });
    }

    window.addEventListener('click', (e) => {
        if (e.target === modalNuevo) {
            modalNuevo.style.display = 'none';
        }
        if (e.target === modalDetalle) {
            modalDetalle.style.display = 'none';
        }
    });

    function poblarTorneos() {
        if (!window.torneos) return;
        torneoSelect.innerHTML = '<option value="">Seleccione un torneo</option>';
        window.torneos.forEach(t => {
            const opt = document.createElement('option');
            opt.value = t.nombre; // Enviar nombre en el formulario
            opt.setAttribute('data-id', t.id); // Guardar ID para filtrado
            opt.textContent = t.nombre;
            torneoSelect.appendChild(opt);
        });
    }

    function repoblarEquipos() {
        if (!window.equipos) return;
        const selectedOption = torneoSelect.selectedOptions[0];
        const torneoId = selectedOption ? selectedOption.getAttribute('data-id') : null;
        const lista = torneoId ? window.equipos.filter(e => e.torneoId === torneoId) : window.equipos;

        [localSelect, visitanteSelect].forEach(selectEl => {
            selectEl.innerHTML = '<option value="">Seleccione equipo</option>';
            lista.forEach(eq => {
                const opt = document.createElement('option');
                opt.value = eq.nombre; // Enviar nombre
                opt.setAttribute('data-id', eq.id); // Usado para validación
                opt.textContent = eq.nombre;
                selectEl.appendChild(opt);
            });
        });
    }

    if (formNuevo) {
        formNuevo.addEventListener('submit', function(event) {
            if (!torneoSelect.value ||
                !jornadaInput.value ||
                !localSelect.value ||
                !visitanteSelect.value ||
                !fechaInput.value ||
                !estadioInput.value) {
                event.preventDefault();
                alert('Por favor, complete todos los campos.');
                return;
            }

            const idLocal = localSelect.selectedOptions[0].getAttribute('data-id');
            const idVisitante = visitanteSelect.selectedOptions[0].getAttribute('data-id');

            if (idLocal === idVisitante) {
                event.preventDefault();
                alert('Los equipos no pueden ser iguales.');
                return;
            }
        });
    }

    // Añadir listener al formulario de detalles para incluir las tarjetas al enviar
    if (formDetalle) {
        formDetalle.addEventListener('submit', function(event) {
            // Si estamos en modo solo lectura, evitar el envío del formulario
            if (modoSoloLectura) {
                event.preventDefault();
                return;
            }

            // Obtener ID del partido
            const idPartido = document.getElementById('idPartido').value;

            // Si hay tarjetas para este partido, agregarlas al formulario
            if (tarjetasPorPartido[idPartido] && tarjetasPorPartido[idPartido].length > 0) {
                // Añadir cantidad de tarjetas
                const inputCantidad = document.createElement('input');
                inputCantidad.type = 'hidden';
                inputCantidad.name = 'cantidadTarjetas';
                inputCantidad.value = tarjetasPorPartido[idPartido].length;
                this.appendChild(inputCantidad);

                // Añadir cada tarjeta como campos individuales
                tarjetasPorPartido[idPartido].forEach((tarjeta, index) => {
                    // Crear campo para cada propiedad de la tarjeta
                    const propiedades = [
                        { name: `tarjeta_${index}_tipo`, value: tarjeta.tipo },
                        { name: `tarjeta_${index}_jugadorId`, value: tarjeta.jugadorId },
                        { name: `tarjeta_${index}_minuto`, value: tarjeta.minuto },
                        { name: `tarjeta_${index}_motivo`, value: tarjeta.motivo }
                    ];

                    // Añadir cada campo al formulario
                    propiedades.forEach(prop => {
                        const input = document.createElement('input');
                        input.type = 'hidden';
                        input.name = prop.name;
                        input.value = prop.value;
                        this.appendChild(input);
                    });
                });

                console.log('Se han añadido ' + tarjetasPorPartido[idPartido].length + ' tarjetas al formulario');
            }
        });
    }

    poblarTorneos();
    repoblarEquipos();
    torneoSelect.addEventListener('change', repoblarEquipos);

    // Modal de tarjetas
    let btnAgregarTarjeta;
    let modalNuevaTarjeta;
    let btnCancelarTarjeta;
    let btnGuardarTarjeta;
    let contenedorTarjetas;

    // Contador para IDs únicos de las tarjetas (solo frontend)
    let contadorTarjetas = 0;

    function cargarJugadoresDePartido() {
        const jugadorSelect = document.getElementById('jugadorTarjeta');
        if (!jugadorSelect) return;

        // Limpiar el selector
        jugadorSelect.innerHTML = '<option value="">Seleccione un jugador</option>';

        // Obtener datos del partido
        const idPartido = document.getElementById('idPartido').value;
        // Buscar el botón que tenga el mismo data-id
        const btnDetalleActivo = document.querySelector(`a[data-id="${idPartido}"]`);
        if (!btnDetalleActivo) return;

        const nombreLocal = btnDetalleActivo.getAttribute('data-local');
        const nombreVisitante = btnDetalleActivo.getAttribute('data-visitante');

        // Buscar los IDs de los equipos
        let idEquipoLocal = null;
        let idEquipoVisitante = null;

        if (window.equipos) {
            window.equipos.forEach(equipo => {
                if (equipo.nombre === nombreLocal) idEquipoLocal = equipo.id;
                if (equipo.nombre === nombreVisitante) idEquipoVisitante = equipo.id;
            });
        }

        if (!idEquipoLocal || !idEquipoVisitante || !window.jugadoresPorEquipo) return;

        // Cargar jugadores del equipo local
        if (window.jugadoresPorEquipo[idEquipoLocal]) {
            const grupoLocal = document.createElement('optgroup');
            grupoLocal.label = nombreLocal;

            window.jugadoresPorEquipo[idEquipoLocal].forEach(jugador => {
                const opcion = document.createElement('option');
                opcion.value = jugador.id;
                opcion.textContent = `${jugador.nombre} (#${jugador.dorsal})`;
                opcion.setAttribute('data-nombre', jugador.nombre);
                opcion.setAttribute('data-equipo', nombreLocal);
                opcion.setAttribute('data-dorsal', jugador.dorsal);
                grupoLocal.appendChild(opcion);
            });

            jugadorSelect.appendChild(grupoLocal);
        }

        // Cargar jugadores del equipo visitante
        if (window.jugadoresPorEquipo[idEquipoVisitante]) {
            const grupoVisitante = document.createElement('optgroup');
            grupoVisitante.label = nombreVisitante;

            window.jugadoresPorEquipo[idEquipoVisitante].forEach(jugador => {
                const opcion = document.createElement('option');
                opcion.value = jugador.id;
                opcion.textContent = `${jugador.nombre} (#${jugador.dorsal})`;
                opcion.setAttribute('data-nombre', jugador.nombre);
                opcion.setAttribute('data-equipo', nombreVisitante);
                opcion.setAttribute('data-dorsal', jugador.dorsal);
                grupoVisitante.appendChild(opcion);
            });

            jugadorSelect.appendChild(grupoVisitante);
        }
    }

    // Función para validar y guardar una tarjeta
    function guardarTarjeta() {
        // Obtener valores del formulario
        const tipoTarjeta = document.getElementById('tipoTarjeta');
        const jugadorSelect = document.getElementById('jugadorTarjeta');
        const minutoTarjeta = document.getElementById('minutoTarjeta');
        const motivoTarjeta = document.getElementById('motivoTarjeta');

        // Validar que todos los campos estén completos
        if (!tipoTarjeta.value || !jugadorSelect.value || !minutoTarjeta.value || !motivoTarjeta.value) {
            alert('Por favor, complete todos los campos.');
            return;
        }

        // Obtener datos del jugador seleccionado
        const jugadorOption = jugadorSelect.options[jugadorSelect.selectedIndex];
        const nombreJugador = jugadorOption.getAttribute('data-nombre');
        const equipoJugador = jugadorOption.getAttribute('data-equipo');
        const dorsalJugador = jugadorOption.getAttribute('data-dorsal');

        // Incrementar contador para ID único
        contadorTarjetas++;

        // Crear elemento visual de la tarjeta
        const tarjetaDiv = document.createElement('div');
        tarjetaDiv.className = `tarjeta-partido ${tipoTarjeta.value.toLowerCase()}`;
        tarjetaDiv.id = `tarjeta-${contadorTarjetas}`;

        tarjetaDiv.innerHTML = `
            <div class="indicador-tarjeta ${tipoTarjeta.value.toLowerCase()}"></div>
            <div class="info-tarjeta">
                <div class="nombre-jugador">${nombreJugador} #${dorsalJugador}</div>
                <div class="equipo-jugador">${equipoJugador}</div>
                <div class="detalle-tarjeta">Min: ${minutoTarjeta.value}' - ${motivoTarjeta.value}</div>
            </div>
            <button type="button" class="eliminar-tarjeta" data-id="${contadorTarjetas}">×</button>
        `;

        // Añadir al contenedor
        contenedorTarjetas.appendChild(tarjetaDiv);

        // Agregar evento al botón de eliminar
        const btnEliminar = tarjetaDiv.querySelector('.eliminar-tarjeta');
        btnEliminar.addEventListener('click', function() {
            const idTarjeta = this.getAttribute('data-id');
            if (confirm('¿Estás seguro de que deseas eliminar esta tarjeta?')) {
                document.getElementById(`tarjeta-${idTarjeta}`).remove();

                // También eliminar de nuestro objeto de almacenamiento
                const idPartido = document.getElementById('idPartido').value;
                if (tarjetasPorPartido[idPartido]) {
                    tarjetasPorPartido[idPartido] = tarjetasPorPartido[idPartido].filter(t => t.idVisual != idTarjeta);
                }
            }
        });

        // Almacenar la información de la tarjeta en memoria por partidoId
        const idPartido = document.getElementById('idPartido').value;
        if (!tarjetasPorPartido[idPartido]) {
            tarjetasPorPartido[idPartido] = [];
        }
        tarjetasPorPartido[idPartido].push({
            tipo: tipoTarjeta.value,
            jugadorId: jugadorSelect.value,
            nombreJugador: nombreJugador,
            equipoJugador: equipoJugador,
            dorsalJugador: dorsalJugador,
            minuto: minutoTarjeta.value,
            motivo: motivoTarjeta.value,
            idVisual: contadorTarjetas
        });

        // Crear un log con los datos guardados
        const datosParaLog = {
            tipo: tipoTarjeta.value,
            jugadorId: jugadorSelect.value,
            minuto: minutoTarjeta.value,
            motivo: motivoTarjeta.value,
            partidoId: document.getElementById('idPartido').value
        };

        console.log('Tarjeta guardada:', datosParaLog);

        // Cerrar el modal y limpiar el formulario
        modalNuevaTarjeta.style.display = 'none';
        limpiarFormularioTarjeta();
    }

    // Función para limpiar el formulario
    function limpiarFormularioTarjeta() {
        const tipoTarjeta = document.getElementById('tipoTarjeta');
        const jugadorSelect = document.getElementById('jugadorTarjeta');
        const minutoTarjeta = document.getElementById('minutoTarjeta');
        const motivoTarjeta = document.getElementById('motivoTarjeta');

        if (tipoTarjeta) tipoTarjeta.value = '';
        if (jugadorSelect) jugadorSelect.innerHTML = '<option value="">Seleccione un jugador</option>';
        if (minutoTarjeta) minutoTarjeta.value = '';
        if (motivoTarjeta) motivoTarjeta.value = '';
    }

    // Cerrar el modal de tarjetas cuando se cierra el modal de detalles
    if (cerrarDetalleButton) {
        const originalOnClick = cerrarDetalleButton.onclick;
        cerrarDetalleButton.onclick = function() {
            if (modalNuevaTarjeta) {
                modalNuevaTarjeta.style.display = 'none';
            }
            if (originalOnClick) {
                originalOnClick();
            } else {
                modalDetalle.style.display = 'none';
            }
        };
    }
});