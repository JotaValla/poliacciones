package com.jotacode.poliacciones_backend.service;

import com.jotacode.poliacciones_backend.model.Accion;
import com.jotacode.poliacciones_backend.model.Usuario;
import com.jotacode.poliacciones_backend.repository.AccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class AccionService {

    @Autowired
    private AccionRepository accionRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TiingoService tiingoService;

    public Accion registrarCompra(Accion accion) {
        // Validar símbolo de acción
        if (!tiingoService.verificarSimbolo(accion.getNombreAccion())) {
            throw new IllegalArgumentException("El símbolo de la acción no es válido: " + accion.getNombreAccion());
        }

        // Validar usuario
        Usuario usuario = usuarioService.obtenerUsuarioPorId(accion.getUsuario().getIdUsuario());
        accion.setUsuario(usuario);

        // Validar fecha
        if (accion.getFecha() == null || accion.getFecha().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha no puede ser futura y debe ser válida.");
        }

        // Obtener precio
        Double precio = tiingoService.obtenerPrecioPorFecha(accion.getNombreAccion(), accion.getFecha())
                .orElseThrow(() -> new RuntimeException("No se pudo obtener el precio para la acción: " + accion.getNombreAccion()));

        accion.setPrecio(precio);
        return accionRepository.save(accion);
    }

    public Accion obtenerAccionPorId(Long accionId) {
        return accionRepository.findById(accionId)
                .orElseThrow(() -> new IllegalArgumentException("Acción no encontrada con ID: " + accionId));
    }

    public List<Accion> obtenerAccionesPorUsuario(Long usuarioId) {
        return accionRepository.findByUsuarioIdUsuario(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontraron acciones para el usuario con ID: " + usuarioId));
    }

    public Accion actualizarAccion(Accion accion) {
        return accionRepository.save(accion);
    }

    public void eliminarAccion(Long accionId) {
        Accion accion = accionRepository.findById(accionId)
                .orElseThrow(() -> new IllegalArgumentException("Acción no encontrada con ID: " + accionId));
        accionRepository.delete(accion);
    }

    public Double obtenerPrecioPorSimboloYFecha(String simbolo, LocalDate fecha) {
        return tiingoService.obtenerPrecioPorFecha(simbolo, fecha)
                .orElseThrow(() -> new IllegalArgumentException("No se pudo obtener el precio para el símbolo " + simbolo + " en la fecha " + fecha));
    }

    public Map<String, Object> calcularGananciaPerdida(Long accionId) {
        Accion accion = obtenerAccionPorId(accionId);
        Double precioActual = tiingoService.obtenerPrecioActual(accion.getNombreAccion())
                .orElseThrow(() -> new RuntimeException("No se pudo obtener el precio actual para la acción " + accion.getNombreAccion()));

        Double ganancia = null;
        Double perdida = null;

        if (precioActual > accion.getPrecio()) {
            ganancia = precioActual - accion.getPrecio();
        } else if (precioActual < accion.getPrecio()) {
            perdida = accion.getPrecio() - precioActual;
        }
        return Map.of(
                "ganancia", ganancia != null ? ganancia : 0.0,
                "perdida", perdida != null ? perdida : 0.0,
                "fechaActual", LocalDate.now().toString(),
                "valorActual", precioActual
        );
    }



}
