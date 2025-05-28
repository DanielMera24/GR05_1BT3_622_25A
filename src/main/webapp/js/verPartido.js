document.addEventListener('DOMContentLoaded', function() {
    console.log('🚀 Inicializando exportador PDF con impresión nativa...');

    const btnExportarPDF = document.getElementById('btnExportarPDF');

    if (!btnExportarPDF) {
        console.error('❌ Botón no encontrado');
        return;
    }

    // REEMPLAZAR la funcionalidad del botón original con el método que funciona
    btnExportarPDF.addEventListener('click', function(event) {
        event.preventDefault();
        console.log('🖨️ Exportando con método de impresión nativa...');
        exportarConVentanaImpresion();
    });

    // MÉTODO DE IMPRESIÓN NATIVA - EL QUE FUNCIONA BIEN
    function exportarConVentanaImpresion() {
        console.log('📋 Preparando ventana de impresión...');

        const mainElement = document.querySelector('main.contenido_principal');
        if (!mainElement) {
            alert('No se encontró el contenido a exportar');
            return;
        }

        // Obtener datos del partido
        const equipoLocal = window.partidoData?.equipoLocal?.nombre || 'Local';
        const equipoVisitante = window.partidoData?.equipoVisitante?.nombre || 'Visitante';

        console.log('📁 Generando PDF para:', equipoLocal, 'vs', equipoVisitante);

        // Crear ventana de impresión
        const ventana = window.open('', '_blank', 'width=900,height=700');

        // HTML COMPLETO optimizado para PDF
        const htmlImpresion = `<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalles del Partido - ${equipoLocal} vs ${equipoVisitante}</title>
    <style>
        * { 
            margin: 0; 
            padding: 0; 
            box-sizing: border-box; 
            font-family: Arial, sans-serif; 
        }
        
        body { 
            background: white; 
            color: #333; 
            padding: 15mm; 
            line-height: 1.4;
        }
        
        /* OCULTAR elementos no deseados */
        .navegacion, 
        .acciones_formulario, 
        .btn_volver {
            display: none !important;
        }
        
        /* CONTENIDO PRINCIPAL */
        .contenido_principal {
            max-width: none !important;
            padding: 0 !important;
            margin: 0 !important;
        }
        
        /* HEADER DEL PARTIDO */
        .info_partido_header {
            text-align: center;
            margin-bottom: 20mm;
            padding: 8mm;
            border: 2px solid #ddd;
            border-radius: 8px;
            page-break-inside: avoid;
            background: white;
        }
        
        .equipos_enfrentamiento {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 10mm;
            margin-bottom: 8mm;
        }
        
        .equipo_header {
            text-align: center;
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 3mm;
        }
        
        .escudo_grande {
            max-width: 12mm;
            max-height: 12mm;
            object-fit: contain;
        }
        
        .nombre_equipo_header {
            font-weight: bold;
            font-size: 12pt;
        }
        
        .vs_header {
            font-size: 16pt;
            font-weight: bold;
            color: #6366f1;
            background: #eef2ff;
            padding: 3mm 6mm;
            border-radius: 4mm;
        }
        
        .detalles_partido {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 2mm;
        }
        
        .torneo_info {
            font-weight: bold;
            color: #4f46e5;
            font-size: 11pt;
        }
        
        .fecha_info {
            color: #6b7280;
            font-size: 10pt;
        }
        
        /* SECCIONES DEL FORMULARIO */
        .seccion_formulario {
            margin-bottom: 8mm;
            padding: 6mm;
            border: 1px solid #ddd;
            border-radius: 6px;
            page-break-inside: avoid;
            background: white;
        }
        
        .seccion_formulario h2 {
            color: #333;
            font-size: 14pt;
            margin-bottom: 5mm;
            border-bottom: 2px solid #eee;
            padding-bottom: 3mm;
            font-weight: bold;
        }
        
        /* CAMPOS DEL FORMULARIO */
        .campos_grupo {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 6mm;
        }
        
        .campo_formulario {
            display: flex;
            flex-direction: column;
            gap: 2mm;
        }
        
        .campo_formulario label {
            font-weight: bold;
            font-size: 10pt;
            color: #374151;
        }
        
        .campo_formulario input {
            padding: 2mm;
            font-size: 10pt;
            border: 1px solid #d1d5db;
            border-radius: 2mm;
            background: #f9fafb;
        }
        
        /* MARCADOR */
        .marcador_section {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 8mm;
            padding: 5mm;
        }
        
        .marcador_equipo {
            display: flex;
            align-items: center;
            gap: 4mm;
        }
        
        .icono_marcador {
            max-width: 10mm;
            max-height: 10mm;
            object-fit: contain;
        }
        
        .nombre_equipo_marcador {
            font-weight: bold;
            min-width: 30mm;
            font-size: 11pt;
        }
        
        .select_goles {
            font-size: 16pt;
            font-weight: bold;
            color: #6366f1;
            border: 2px solid #6366f1;
            padding: 3mm 5mm;
            border-radius: 4mm;
            min-width: 12mm;
            text-align: center;
            background: white;
        }
        
        .separador_marcador {
            font-size: 18pt;
            font-weight: bold;
            color: #6b7280;
        }
        
        /* SEPARADOR DE EQUIPOS */
        .separador {
            display: flex;
            gap: 8mm;
        }
        
        .equipo_participantes {
            flex: 1;
            margin-bottom: 6mm;
            page-break-inside: avoid;
        }
        
        .header_equipo_participantes {
            display: flex;
            align-items: center;
            gap: 3mm;
            margin-bottom: 3mm;
            padding: 3mm;
            background: #f8f9fa;
            border-radius: 3mm;
        }
        
        .icono_equipo {
            max-width: 8mm;
            max-height: 8mm;
            object-fit: contain;
        }
        
        .header_equipo_participantes h3 {
            font-size: 12pt;
            margin: 0;
            color: #1f2937;
        }
        
        /* TABLAS */
        .tabla_participantes {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 4mm;
            page-break-inside: avoid;
        }
        
        .tabla_participantes th,
        .tabla_participantes td {
            border: 1px solid #ddd;
            padding: 2mm;
            text-align: left;
            font-size: 9pt;
        }
        
        .tabla_participantes th {
            background: #f5f5f5;
            font-weight: bold;
            color: #374151;
        }
        
        .tabla_participantes tr:nth-child(even) {
            background: #f9fafb;
        }
        
        /* INPUTS EN TABLAS */
        input, select, textarea {
            border: none !important;
            background: transparent !important;
            font-weight: bold !important;
            padding: 1mm !important;
        }
        
        /* GOLES Y TARJETAS */
        .gol_partido,
        .tarjeta_partido {
            display: flex;
            align-items: center;
            margin-bottom: 3mm;
            padding: 4mm;
            border-left: 3mm solid #28a745;
            background: #f0fdf4;
            border-radius: 3mm;
            page-break-inside: avoid;
        }
        
        .tarjeta_partido.amarilla {
            border-left-color: #fbbf24;
            background: #fffbeb;
        }
        
        .tarjeta_partido.roja {
            border-left-color: #ef4444;
            background: #fef2f2;
        }
        
        .indicador_gol,
        .indicador_tarjeta {
            margin-right: 4mm;
            flex-shrink: 0;
            font-size: 14pt;
        }
        
        .indicador_tarjeta {
            width: 4mm;
            height: 4mm;
            border-radius: 1mm;
        }
        
        .indicador_tarjeta.amarilla {
            background: #fbbf24;
        }
        
        .indicador_tarjeta.roja {
            background: #ef4444;
        }
        
        .info_gol,
        .info_tarjeta {
            flex: 1;
        }
        
        .nombre_jugador {
            font-weight: bold;
            margin-bottom: 1mm;
            font-size: 10pt;
            color: #1f2937;
        }
        
        .equipo_jugador {
            font-size: 8pt;
            color: #6b7280;
            margin-bottom: 1mm;
        }
        
        .detalle_gol,
        .detalle_tarjeta {
            font-size: 9pt;
            color: #374151;
            font-weight: 500;
        }
        
        /* CONTENEDORES DE EVENTOS */
        .contenedor_goles_partido,
        .contenedor_tarjetas_partido {
            max-height: none !important;
            overflow: visible !important;
        }
        
        /* ESTILOS DE IMPRESIÓN */
        @media print {
            body {
                margin: 0;
                padding: 10mm;
                -webkit-print-color-adjust: exact;
                print-color-adjust: exact;
            }
            
            .seccion_formulario {
                page-break-inside: avoid;
                margin-bottom: 6mm;
            }
            
            .equipo_participantes {
                page-break-inside: avoid;
            }
            
            .info_partido_header {
                page-break-inside: avoid;
            }
            
            .gol_partido,
            .tarjeta_partido {
                page-break-inside: avoid;
            }
            
            .tabla_participantes {
                page-break-inside: avoid;
            }
        }
        
        /* RESPONSIVE PARA PANTALLA PEQUEÑA */
        @media (max-width: 180mm) {
            .separador {
                flex-direction: column;
                gap: 4mm;
            }
            
            .equipos_enfrentamiento {
                flex-direction: column;
                gap: 5mm;
            }
            
            .marcador_section {
                flex-direction: column;
                gap: 4mm;
            }
            
            .campos_grupo {
                grid-template-columns: 1fr;
                gap: 4mm;
            }
        }
    </style>
</head>
<body>
    ${mainElement.outerHTML}
    
    <script>
        // Auto-imprimir después de cargar completamente
        window.onload = function() {
            setTimeout(function() {
                window.print();
            }, 1000); // Esperar un poco más para que carguen las imágenes
        };
        
        // Cerrar ventana después de imprimir o cancelar
        window.onafterprint = function() {
            setTimeout(function() {
                window.close();
            }, 500);
        };
        
        // Manejar el caso de cancelar impresión
        window.onbeforeunload = function() {
            return null;
        };
    </script>
</body>
</html>`;

        // Escribir contenido en la nueva ventana
        ventana.document.open();
        ventana.document.write(htmlImpresion);
        ventana.document.close();

        console.log('✅ Ventana de impresión abierta - Listo para guardar como PDF');
    }

    // Función de prueba
    window.testExportarPDF = exportarConVentanaImpresion;

    console.log('✅ Exportador PDF con impresión nativa listo');
    console.log('🎯 El botón "Exportar a PDF" ahora usa el método confiable');
    console.log('💡 Para guardar: Imprimir > Guardar como PDF');
});