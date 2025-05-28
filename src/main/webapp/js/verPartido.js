document.addEventListener('DOMContentLoaded', function() {
    console.log('🚀 Inicializando exportador PDF con validaciones...');

    const btnExportarPDF = document.getElementById('btnExportarPDF');

    if (!btnExportarPDF) {
        console.error('❌ Botón de exportar PDF no encontrado');
        return;
    }

    // REEMPLAZAR la funcionalidad del botón original con validaciones
    btnExportarPDF.addEventListener('click', function(event) {
        event.preventDefault();
        console.log('🖨️ Iniciando proceso de exportación a PDF...');

        // PASO 1: Validar estado del partido
        if (!validarEstadoPartido()) {
            return; // Detener si no pasa la validación
        }

        // PASO 2: Validar datos completos
        if (!validarDatosCompletos()) {
            return; // Detener si no pasa la validación
        }

        // PASO 3: Si todas las validaciones pasan, generar el PDF
        console.log('✅ Todas las validaciones pasaron - Generando acta oficial...');
        exportarActaOficial();
    });

    // ===== FUNCIÓN DE VALIDACIÓN DEL ESTADO DEL PARTIDO =====
    function validarEstadoPartido() {
        // Obtener el estado del partido desde el HTML
        const estadoElement = document.getElementById('estadoPartido');
        if (!estadoElement) {
            console.error('❌ No se pudo obtener el estado del partido');
            mostrarMensajeError('Error al obtener información del partido');
            return false;
        }

        const estadoPartido = estadoElement.value || estadoElement.textContent || '';
        console.log('📊 Estado del partido:', estadoPartido);

        // Validar que el partido esté finalizado
        if (estadoPartido.toLowerCase() !== 'finalizado') {
            let mensaje;
            if (estadoPartido.toLowerCase() === 'pendiente') {
                mensaje = 'No se puede generar acta oficial. El partido aún no ha iniciado.';
            } else if (estadoPartido.toLowerCase() === 'en curso') {
                mensaje = 'No se puede generar acta oficial. El partido debe estar finalizado para garantizar datos consistentes.';
            } else {
                mensaje = 'No se puede generar acta oficial. El partido debe estar finalizado.';
            }

            mostrarMensajeError(mensaje);
            return false;
        }

        return true;
    }

    // ===== FUNCIÓN DE VALIDACIÓN DE DATOS COMPLETOS =====
    function validarDatosCompletos() {
        console.log('🔍 Validando completitud de datos...');

        // Verificar jugadores participantes
        const jugadoresLocal = document.querySelectorAll('#jugadoresLocal tr[data-jugador-id]');
        const jugadoresVisitante = document.querySelectorAll('#jugadoresVisitante tr[data-jugador-id]');

        console.log('👥 Jugadores locales:', jugadoresLocal.length);
        console.log('👥 Jugadores visitantes:', jugadoresVisitante.length);

        if (jugadoresLocal.length === 0 || jugadoresVisitante.length === 0) {
            mostrarMensajeError('No se puede generar acta oficial. Faltan datos de jugadores participantes.');
            return false;
        }

        // Verificar que hay al menos un capitán por equipo
        const capitanLocal = document.querySelector('input[name="capitanLocal"]:checked');
        const capitanVisitante = document.querySelector('input[name="capitanVisitante"]:checked');

        if (!capitanLocal || !capitanVisitante) {
            mostrarMensajeError('No se puede generar acta oficial. Debe haber un capitán designado por cada equipo.');
            return false;
        }

        // Verificar consistencia del marcador
        if (!validarConsistenciaMarcador()) {
            return false;
        }

        return true;
    }

    // ===== FUNCIÓN DE VALIDACIÓN DE CONSISTENCIA DEL MARCADOR =====
    function validarConsistenciaMarcador() {
        // Obtener marcador oficial
        const golesLocalElement = document.querySelector('.select_goles');
        const golesVisitanteElement = document.querySelectorAll('.select_goles')[1];

        if (!golesLocalElement || !golesVisitanteElement) {
            console.warn('⚠️ No se pudo verificar consistencia del marcador');
            return true; // Continuar si no se puede verificar
        }

        const golesOficialLocal = parseInt(golesLocalElement.textContent) || 0;
        const golesOficialVisitante = parseInt(golesVisitanteElement.textContent) || 0;

        // Contar goles registrados en el acta
        const golesRegistradosLocal = contarGolesEquipo(window.partidoData.equipoLocal.nombre);
        const golesRegistradosVisitante = contarGolesEquipo(window.partidoData.equipoVisitante.nombre);

        console.log('🥅 Marcador oficial:', golesOficialLocal, '-', golesOficialVisitante);
        console.log('🥅 Goles registrados:', golesRegistradosLocal, '-', golesRegistradosVisitante);

        if (golesOficialLocal !== golesRegistradosLocal || golesOficialVisitante !== golesRegistradosVisitante) {
            mostrarMensajeAdvertencia(
                `Advertencia: El marcador oficial (${golesOficialLocal}-${golesOficialVisitante}) no coincide con los goles registrados (${golesRegistradosLocal}-${golesRegistradosVisitante}). ¿Desea continuar?`,
                () => exportarActaOficial() // Callback para continuar
            );
            return false; // Detener para mostrar advertencia
        }

        return true;
    }

    // ===== FUNCIÓN AUXILIAR PARA CONTAR GOLES POR EQUIPO =====
    function contarGolesEquipo(nombreEquipo) {
        const golesEquipo = document.querySelectorAll('.gol_partido');
        let contador = 0;

        golesEquipo.forEach(gol => {
            const equipoGol = gol.querySelector('.equipo_jugador').textContent;
            if (equipoGol === nombreEquipo) {
                contador++;
            }
        });

        return contador;
    }

    // ===== FUNCIÓN PARA MOSTRAR MENSAJES DE ERROR =====
    function mostrarMensajeError(mensaje) {
        // Eliminar mensaje anterior si existe
        const mensajeAnterior = document.querySelector('.mensaje-error-exportacion');
        if (mensajeAnterior) {
            mensajeAnterior.remove();
        }

        // Crear mensaje de error
        const mensajeError = document.createElement('div');
        mensajeError.className = 'mensaje-error-exportacion';
        mensajeError.innerHTML = `
            <div style="
                position: fixed;
                top: 20px;
                right: 20px;
                background-color: #fee2e2;
                border: 1px solid #fca5a5;
                color: #b91c1c;
                padding: 16px 20px;
                border-radius: 8px;
                font-weight: 500;
                box-shadow: 0 4px 12px rgba(0,0,0,0.15);
                z-index: 1000;
                max-width: 400px;
                display: flex;
                align-items: flex-start;
                gap: 10px;
            ">
                <span style="font-size: 20px; flex-shrink: 0;">❌</span>
                <div>
                    <div style="font-weight: 600; margin-bottom: 4px;">Error de exportación</div>
                    <div>${mensaje}</div>
                </div>
                <button onclick="this.parentElement.parentElement.remove()" style="
                    background: none;
                    border: none;
                    color: #b91c1c;
                    font-size: 18px;
                    cursor: pointer;
                    padding: 0;
                    margin-left: 10px;
                ">×</button>
            </div>
        `;

        document.body.appendChild(mensajeError);

        // Auto-remover después de 5 segundos
        setTimeout(() => {
            if (mensajeError.parentNode) {
                mensajeError.remove();
            }
        }, 5000);
    }

    // ===== FUNCIÓN PARA MOSTRAR MENSAJES DE ADVERTENCIA CON CONFIRMACIÓN =====
    function mostrarMensajeAdvertencia(mensaje, callbackContinuar) {
        // Eliminar mensaje anterior si existe
        const mensajeAnterior = document.querySelector('.mensaje-advertencia-exportacion');
        if (mensajeAnterior) {
            mensajeAnterior.remove();
        }

        // Crear mensaje de advertencia con opciones
        const mensajeAdvertencia = document.createElement('div');
        mensajeAdvertencia.className = 'mensaje-advertencia-exportacion';
        mensajeAdvertencia.innerHTML = `
            <div style="
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0,0,0,0.5);
                z-index: 2000;
                display: flex;
                align-items: center;
                justify-content: center;
            ">
                <div style="
                    background: white;
                    padding: 24px;
                    border-radius: 12px;
                    box-shadow: 0 10px 25px rgba(0,0,0,0.3);
                    max-width: 500px;
                    margin: 20px;
                ">
                    <div style="display: flex; align-items: flex-start; gap: 12px; margin-bottom: 20px;">
                        <span style="font-size: 24px; color: #f59e0b;">⚠️</span>
                        <div>
                            <div style="font-weight: 600; font-size: 18px; margin-bottom: 8px; color: #1f2937;">
                                Advertencia de consistencia
                            </div>
                            <div style="color: #6b7280; line-height: 1.5;">
                                ${mensaje}
                            </div>
                        </div>
                    </div>
                    <div style="display: flex; gap: 12px; justify-content: flex-end;">
                        <button onclick="document.querySelector('.mensaje-advertencia-exportacion').remove()" style="
                            background: #e5e7eb;
                            color: #374151;
                            border: none;
                            padding: 10px 20px;
                            border-radius: 6px;
                            cursor: pointer;
                            font-weight: 500;
                        ">Cancelar</button>
                        <button onclick="
                            document.querySelector('.mensaje-advertencia-exportacion').remove();
                            (${callbackContinuar.toString()})();
                        " style="
                            background: #f59e0b;
                            color: white;
                            border: none;
                            padding: 10px 20px;
                            border-radius: 6px;
                            cursor: pointer;
                            font-weight: 500;
                        ">Continuar de todos modos</button>
                    </div>
                </div>
            </div>
        `;

        document.body.appendChild(mensajeAdvertencia);
    }

    // ===== FUNCIÓN PARA EXPORTAR ACTA OFICIAL (MEJORADA) =====
    function exportarActaOficial() {
        console.log('📋 Generando acta oficial del partido...');

        const mainElement = document.querySelector('main.contenido_principal');
        if (!mainElement) {
            mostrarMensajeError('No se encontró el contenido a exportar');
            return;
        }

        // Obtener datos del partido
        const equipoLocal = window.partidoData?.equipoLocal?.nombre || 'Local';
        const equipoVisitante = window.partidoData?.equipoVisitante?.nombre || 'Visitante';
        const fechaActual = new Date().toLocaleDateString('es-ES');
        const horaActual = new Date().toLocaleTimeString('es-ES');

        console.log('📁 Generando acta oficial para:', equipoLocal, 'vs', equipoVisitante);

        // Crear ventana de impresión
        const ventana = window.open('', '_blank', 'width=900,height=700');

        // HTML COMPLETO optimizado para acta oficial
        const htmlActaOficial = `<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Acta Oficial - ${equipoLocal} vs ${equipoVisitante}</title>
    <style>
        * { 
            margin: 0; 
            padding: 0; 
            box-sizing: border-box; 
            font-family: 'Times New Roman', serif; 
        }
        
        body { 
            background: white; 
            color: #000; 
            padding: 20mm; 
            line-height: 1.6;
            font-size: 12pt;
        }
        
        /* HEADER OFICIAL */
        .header-oficial {
            text-align: center;
            margin-bottom: 30px;
            border-bottom: 3px solid #000;
            padding-bottom: 20px;
        }
        
        .titulo-acta {
            font-size: 24pt;
            font-weight: bold;
            margin-bottom: 10px;
            text-transform: uppercase;
            letter-spacing: 2px;
        }
        
        .subtitulo-acta {
            font-size: 14pt;
            margin-bottom: 5px;
        }
        
        .fecha-generacion {
            font-size: 10pt;
            color: #666;
            margin-top: 10px;
        }
        
        /* ESTILOS DEL CONTENIDO EXISTENTE MEJORADOS */
        .navegacion, 
        .acciones_formulario, 
        .btn_volver {
            display: none !important;
        }
        
        .contenido_principal {
            max-width: none !important;
            padding: 0 !important;
            margin: 0 !important;
        }
        
        /* Resto del CSS existente optimizado... */
        .info_partido_header {
            margin-bottom: 25px;
            padding: 15px;
            border: 2px solid #000;
            border-radius: 8px;
            page-break-inside: avoid;
        }
        
        .equipos_enfrentamiento {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 30px;
            margin-bottom: 15px;
        }
        
        /* Continuar con estilos existentes pero optimizados para acta oficial... */
        
        @media print {
            body {
                margin: 0;
                padding: 15mm;
                -webkit-print-color-adjust: exact;
                print-color-adjust: exact;
            }
            
            .header-oficial {
                page-break-after: avoid;
            }
            
            .seccion_formulario {
                page-break-inside: avoid;
                margin-bottom: 8mm;
            }
        }
    </style>
</head>
<body>
    <div class="header-oficial">
        <div class="titulo-acta">Acta Oficial del Partido</div>
        <div class="subtitulo-acta">${equipoLocal} vs ${equipoVisitante}</div>
        <div class="fecha-generacion">Generada el ${fechaActual} a las ${horaActual}</div>
    </div>
    
    ${mainElement.outerHTML}
    
    <script>
        // Auto-imprimir después de cargar completamente
        window.onload = function() {
            setTimeout(function() {
                window.print();
            }, 1200);
        };
        
        // Cerrar ventana después de imprimir
        window.onafterprint = function() {
            setTimeout(function() {
                window.close();
            }, 500);
        };
    </script>
</body>
</html>`;

        // Escribir contenido en la nueva ventana
        ventana.document.open();
        ventana.document.write(htmlActaOficial);
        ventana.document.close();

        console.log('✅ Acta oficial lista para guardar como PDF');

        // Mostrar mensaje de éxito
        mostrarMensajeExito('Acta oficial generada correctamente. Use Ctrl+P o Cmd+P para guardar como PDF.');
    }

    // ===== FUNCIÓN PARA MOSTRAR MENSAJES DE ÉXITO =====
    function mostrarMensajeExito(mensaje) {
        const mensajeExito = document.createElement('div');
        mensajeExito.innerHTML = `
            <div style="
                position: fixed;
                top: 20px;
                right: 20px;
                background-color: #d1fae5;
                border: 1px solid #6ee7b7;
                color: #065f46;
                padding: 16px 20px;
                border-radius: 8px;
                font-weight: 500;
                box-shadow: 0 4px 12px rgba(0,0,0,0.15);
                z-index: 1000;
                max-width: 400px;
                display: flex;
                align-items: center;
                gap: 10px;
            ">
                <span style="font-size: 20px;">✅</span>
                <div>${mensaje}</div>
                <button onclick="this.parentElement.parentElement.remove()" style="
                    background: none;
                    border: none;
                    color: #065f46;
                    font-size: 18px;
                    cursor: pointer;
                    padding: 0;
                    margin-left: 10px;
                ">×</button>
            </div>
        `;

        document.body.appendChild(mensajeExito);

        // Auto-remover después de 4 segundos
        setTimeout(() => {
            if (mensajeExito.parentNode) {
                mensajeExito.remove();
            }
        }, 4000);
    }

    console.log('✅ Exportador PDF con validaciones configurado correctamente');
});