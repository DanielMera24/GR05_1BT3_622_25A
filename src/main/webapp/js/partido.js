document.addEventListener('DOMContentLoaded', function() {
    const modalNuevo = document.getElementById('modalNuevoPartido');
    const modalDetalle = document.getElementById('modalDetallePartido');

    const btnAbrirNuevo = document.getElementById('abrirModal');
    const cerrarModalButtons = document.querySelectorAll('.cerrar-modal');
    const cerrarDetalleButton = document.querySelector('.cerrar-modal-detalle');

    const formNuevo = document.getElementById('formNuevoPartido');

    const torneoSelect = document.getElementById('torneo');
    const localSelect = document.getElementById('equipoLocal');
    const visitanteSelect = document.getElementById('equipoVisitante');

    const jornadaInput = document.getElementById('jornada');
    const fechaInput = document.getElementById('fecha');
    const estadioInput = document.getElementById('estadio');

    const botonesDetalles = document.querySelectorAll('.accion_enlace');

    if (btnAbrirNuevo && modalNuevo) {
        btnAbrirNuevo.addEventListener('click', () => modalNuevo.style.display = 'block');
    }

    cerrarModalButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            modalNuevo.style.display = 'none';
        });
    });

    botonesDetalles.forEach(btn => {
        btn.addEventListener('click', function(event) {
            event.preventDefault();
            modalDetalle.style.display = 'block';

            const nombreLocal = btn.getAttribute('data-local');
            const nombreVisitante = btn.getAttribute('data-visitante');
            const golesLocal = btn.getAttribute('data-goles-local');
            const golesVisitante = btn.getAttribute('data-goles-visitante');
            const nombreTorneo = btn.getAttribute('data-torneo');
            const jornada = btn.getAttribute('data-jornada');
            const estado = btn.getAttribute('data-estado');

            document.querySelector('#formDetallePartido p:nth-child(1)').innerHTML = `<strong>${nombreLocal}</strong> vs <strong>${nombreVisitante}</strong>`;
            document.querySelector('#formDetallePartido p:nth-child(2)').innerText = `${nombreTorneo} · Jornada ${jornada}`;

            const selectGolesLocal = document.querySelector('select[name="golesLocal"]');
            const selectGolesVisitante = document.querySelector('select[name="golesVisitante"]');
            const selectEstado = document.getElementById('estadoPartido');

            if (selectGolesLocal && selectGolesVisitante && selectEstado) {
                selectGolesLocal.value = golesLocal;
                selectGolesVisitante.value = golesVisitante;
                selectEstado.value = estado;
            }
        });
    });

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

    poblarTorneos();
    repoblarEquipos();
    torneoSelect.addEventListener('change', repoblarEquipos);
});
