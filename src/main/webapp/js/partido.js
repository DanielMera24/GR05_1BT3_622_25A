document.addEventListener('DOMContentLoaded', function() {
    const modalNuevo = document.getElementById('modalNuevoPartido');
    const btnAbrirNuevo = document.getElementById('abrirModal');
    const cerrarModalButtons = document.querySelectorAll('.cerrar-modal');

    const formNuevo = document.getElementById('formNuevoPartido');
    const torneoSelect = document.getElementById('torneo');
    const localSelect = document.getElementById('equipoLocal');
    const visitanteSelect = document.getElementById('equipoVisitante');
    const jornadaInput = document.getElementById('jornada');
    const fechaInput = document.getElementById('fecha');
    const estadioInput = document.getElementById('estadio');

    if (btnAbrirNuevo && modalNuevo) {
        btnAbrirNuevo.addEventListener('click', () => {
            modalNuevo.style.display = 'block';
        });
    }

    cerrarModalButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            modalNuevo.style.display = 'none';
        });
    });

    // Cerrar modal al hacer clic fuera del contenido
    window.addEventListener('click', (e) => {
        if (e.target === modalNuevo) {
            modalNuevo.style.display = 'none';
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
