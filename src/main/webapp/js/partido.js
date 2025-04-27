document.addEventListener('DOMContentLoaded', function() {
    // —— REFERENCIAS A ELEMENTOS —————————————————————————————————————
    const modal           = document.getElementById('modalNuevoPartido');
    const btnAbrir        = document.getElementById('abrirModal');
    const btnCerrar       = document.querySelector('.cerrar-modal');
    const btnCancelar     = document.querySelector('.boton-secundario');
    const form            = document.getElementById('formNuevoPartido');
    const torneoSelect    = document.getElementById('torneo');
    const localSelect     = document.getElementById('equipoLocal');
    const visitanteSelect = document.getElementById('equipoVisitante');
    const jornadaInput    = document.getElementById('jornada');
    const fechaInput      = document.getElementById('fecha');
    const estadioInput    = document.getElementById('estadio');

    // —— POBLAR SELECT DE TORNEOS ——————————————————————————————————————
    function poblarTorneos() {
        torneoSelect.innerHTML = '<option value="">Seleccione un torneo</option>';
        window.torneos.forEach(t => {
            const opt = document.createElement('option');
            opt.value       = t.id;
            opt.textContent = t.nombre;
            torneoSelect.appendChild(opt);
        });
    }

    // —— POBLAR SELECTS DE EQUIPOS SEGÚN TORNEO ————————————————————————
    function repoblarEquipos() {
        const torneoId = torneoSelect.value;
        const lista = torneoId
            ? window.equipos.filter(e => e.torneoId === torneoId)
            : window.equipos;

        [localSelect, visitanteSelect].forEach(selectEl => {
            selectEl.innerHTML = '<option value="">Seleccione equipo</option>';
            lista.forEach(eq => {
                const opt = document.createElement('option');
                opt.value       = eq.id;
                opt.textContent = eq.nombre;
                selectEl.appendChild(opt);
            });
        });
    }

    // —— MODAL ——————————————————————————————————————————————————————
    btnAbrir.addEventListener('click',    () => modal.style.display = 'block');
    btnCerrar.addEventListener('click',   () => modal.style.display = 'none');
    btnCancelar.addEventListener('click', () => modal.style.display = 'none');
    window.addEventListener('click', e => {
        if (e.target === modal) modal.style.display = 'none';
    });

    // —— ENVÍO DE FORMULARIO ———————————————————————————————————————
    form.addEventListener('submit', function(event) {
        // 1) validación de campos obligatorios
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
        // 2) validar que no sean el mismo equipo
        if (localSelect.value === visitanteSelect.value) {
            event.preventDefault();
            alert('Los equipos no pueden ser iguales.');
            return;
        }
        // 3) si llega aquí, no llamamos preventDefault(),
        //    el formulario se envía de forma tradicional a /crearPartido
    });

    // —— INICIALIZACIÓN ———————————————————————————————————————————
    poblarTorneos();
    repoblarEquipos();
    torneoSelect.addEventListener('change', repoblarEquipos);
});