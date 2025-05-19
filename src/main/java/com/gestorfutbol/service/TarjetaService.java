package com.gestorfutbol.service;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.dao.implementation.TarjetaDAOImpl;
import com.gestorfutbol.dao.interfaces.TarjetaDAO;
import com.gestorfutbol.dto.TarjetaDTO;
import com.gestorfutbol.entity.Jugador;
import com.gestorfutbol.entity.Tarjeta;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TarjetaService {

    // Constantes para los tipos de tarjetas
    private static final String TARJETA_AMARILLA = "AMARILLA";
    private static final String TARJETA_ROJA = "ROJA";

    // Constantes para las validaciones
    private static final int MINUTO_MAXIMO = 90;
    private static final int MINUTO_MINIMO = 0;
    private static final int MAX_TARJETAS_AMARILLAS = 2;
    private static final int MAX_TARJETAS_ROJAS = 1;
    private static final int MAX_CARACTERES_MOTIVO = 200;

    private final TarjetaDAO tarjetaDAO;

    public TarjetaService(TarjetaDAO tarjetaDAO) {
        this.tarjetaDAO = tarjetaDAO;
    }

    public TarjetaService() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        this.tarjetaDAO = new TarjetaDAOImpl(sessionFactory);
    }

    /**
     * Guarda una lista de tarjetas después de validarlas
     * @param tarjetas Lista de tarjetas a guardar
     * @return true si todas las tarjetas son válidas y se guardaron correctamente
     */
    public boolean guardarTarjeta(List<Tarjeta> tarjetas) {
        if (tarjetas == null || tarjetas.isEmpty()) {
            return false;
        }

        // Validar cada tarjeta individualmente
        if (!sonTarjetasValidas(tarjetas)) {
            return false;
        }

        // Validar reglas de negocio por jugador
        if (!esValidaDistribucionTarjetasPorJugador(tarjetas)) {
            return false;
        }

        // Guardar todas las tarjetas
        tarjetas.forEach(tarjetaDAO::guardarTarjeta);
        return true;
    }

    /**
     * Valida que todas las tarjetas cumplan con las reglas básicas
     */
    private boolean sonTarjetasValidas(List<Tarjeta> tarjetas) {
        return tarjetas.stream().allMatch(this::esTarjetaValida);
    }

    /**
     * Valida una tarjeta individual
     */
    private boolean esTarjetaValida(Tarjeta tarjeta) {
        return esTipoTarjetaValido(tarjeta.getTipoTarjeta()) &&
                esMinutoValido(tarjeta.getMinuto()) &&
                esMotivoValido(tarjeta.getMotivo());
    }

    /**
     * Valida que la distribución de tarjetas por jugador sea correcta
     */
    private boolean esValidaDistribucionTarjetasPorJugador(List<Tarjeta> tarjetasNuevas) {
        int idPartido = tarjetasNuevas.get(0).getPartido().getIdPartido();
        List<Tarjeta> tarjetasExistentes = tarjetaDAO.obtenerPorPartido(idPartido);

        Map<Jugador, List<Tarjeta>> tarjetasPorJugador = agruparTarjetasPorJugador(tarjetasExistentes, tarjetasNuevas);

        return tarjetasPorJugador.values().stream()
                .allMatch(this::esValidaCantidadTarjetasPorJugador);
    }

    /**
     * Agrupa las tarjetas existentes y nuevas por jugador
     */
    private Map<Jugador, List<Tarjeta>> agruparTarjetasPorJugador(List<Tarjeta> tarjetasExistentes, List<Tarjeta> tarjetasNuevas) {
        Map<Jugador, List<Tarjeta>> tarjetasPorJugador = new HashMap<>();

        // Agregar tarjetas existentes
        tarjetasExistentes.forEach(tarjeta ->
                tarjetasPorJugador.computeIfAbsent(tarjeta.getJugador(), k -> new ArrayList<>()).add(tarjeta)
        );

        // Agregar tarjetas nuevas
        tarjetasNuevas.forEach(tarjeta ->
                tarjetasPorJugador.computeIfAbsent(tarjeta.getJugador(), k -> new ArrayList<>()).add(tarjeta)
        );

        return tarjetasPorJugador;
    }

    /**
     * Valida que un jugador no exceda el límite de tarjetas permitidas
     */
    private boolean esValidaCantidadTarjetasPorJugador(List<Tarjeta> tarjetasJugador) {
        long tarjetasAmarillas = contarTarjetasPorTipo(tarjetasJugador, TARJETA_AMARILLA);
        long tarjetasRojas = contarTarjetasPorTipo(tarjetasJugador, TARJETA_ROJA);

        return tarjetasAmarillas <= MAX_TARJETAS_AMARILLAS && tarjetasRojas <= MAX_TARJETAS_ROJAS;
    }

    /**
     * Cuenta las tarjetas de un tipo específico
     */
    private long contarTarjetasPorTipo(List<Tarjeta> tarjetas, String tipoTarjeta) {
        return tarjetas.stream()
                .filter(tarjeta -> tipoTarjeta.equals(tarjeta.getTipoTarjeta()))
                .count();
    }

    // ===== MÉTODOS DE VALIDACIÓN =====

    public boolean esMinutoValido(int minutos) {
        return minutos >= MINUTO_MINIMO && minutos <= MINUTO_MAXIMO;
    }

    public boolean esTipoTarjetaValido(String tipoTarjeta) {
        return TARJETA_AMARILLA.equals(tipoTarjeta) || TARJETA_ROJA.equals(tipoTarjeta);
    }

    public boolean esMotivoValido(String motivo) {
        return motivo != null && !motivo.trim().isEmpty() && motivo.length() < MAX_CARACTERES_MOTIVO;
    }

    // ===== MÉTODOS LEGACY (Mantenidos para compatibilidad) =====

    @Deprecated
    public boolean esValidoCantidadTarjetasAmarillasAJugador(List<Tarjeta> tarjetasDeUnJugador) {
        return contarTarjetasPorTipo(tarjetasDeUnJugador, TARJETA_AMARILLA) <= MAX_TARJETAS_AMARILLAS;
    }

    @Deprecated
    public boolean esValidoCantidadRojasAJugador(List<Tarjeta> tarjetas) {
        return contarTarjetasPorTipo(tarjetas, TARJETA_ROJA) <= MAX_TARJETAS_ROJAS;
    }

    @Deprecated
    public boolean validarTipoTarjeta(Tarjeta tarjeta) {
        return esTipoTarjetaValido(tarjeta.getTipoTarjeta());
    }

    @Deprecated
    public boolean esNuloOVacio(String motivo) {
        return motivo == null || motivo.trim().isEmpty();
    }

    @Deprecated
    public boolean cumpleLimiteCaracteres(String motivo) {
        return motivo != null && motivo.length() >= MAX_CARACTERES_MOTIVO;
    }

    @Deprecated
    public boolean cantidadEsNegativa(int cantidad) {
        return cantidad < 0;
    }

    // ===== MÉTODOS DE CONSULTA =====

    /**
     * Verifica si un jugador existe en la lista por su cédula
     */
    public boolean jugadorAmonestadoExiste(List<Jugador> jugadores, String cedula) {
        return jugadores.stream()
                .anyMatch(jugador -> cedula.equals(jugador.getCedula()));
    }

    /**
     * Lista todas las tarjetas como DTOs
     */
    public List<TarjetaDTO> listarTarjetas() {
        List<Tarjeta> tarjetas = tarjetaDAO.obtenerTodas();
        return convertirADTOs(tarjetas);
    }

    /**
     * Lista las tarjetas de un partido específico como DTOs
     */
    public List<TarjetaDTO> listarTarjetasPorPartido(int idPartido) {
        List<Tarjeta> tarjetas = tarjetaDAO.obtenerPorPartido(idPartido);
        return convertirADTOs(tarjetas);
    }

    /**
     * Convierte una lista de entidades Tarjeta a DTOs
     */
    private List<TarjetaDTO> convertirADTOs(List<Tarjeta> tarjetas) {
        if (tarjetas == null) {
            return new ArrayList<>();
        }

        return tarjetas.stream()
                .map(this::crearTarjetaDTO)
                .collect(Collectors.toList());
    }

    /**
     * Crea un DTO a partir de una entidad Tarjeta
     */
    private TarjetaDTO crearTarjetaDTO(Tarjeta tarjeta) {
        return new TarjetaDTO(
                tarjeta.getIdTarjeta(),
                tarjeta.getTipoTarjeta(),
                tarjeta.getMinuto(),
                tarjeta.getMotivo(),
                tarjeta.getJugador().getIdJugador(),
                tarjeta.getJugador().getNombre(),
                tarjeta.getJugador().getEquipo().getNombre(),
                tarjeta.getJugador().getDorsal(),
                tarjeta.getPartido().getIdPartido(),
                tarjeta.getPartido().getEquipoLocal().getSiglas(),
                tarjeta.getPartido().getEquipoVisita().getSiglas()
        );
    }

    public List<Tarjeta> obtenerPorPartido(int idPartido) {
        List<Tarjeta> tarjetas = tarjetaDAO.obtenerPorPartido(idPartido);
        return tarjetas != null ? tarjetas : new ArrayList<>();
    }
}