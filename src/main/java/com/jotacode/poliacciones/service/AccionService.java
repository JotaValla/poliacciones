package com.jotacode.poliacciones.service;

import com.jotacode.poliacciones.model.Accion;
import com.jotacode.poliacciones.model.Usuario;
import com.jotacode.poliacciones.repository.AccionRepository;
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
        // Verificar si el símbolo de la acción es válido
        if (!tiingoService.verificarSimbolo(accion.getNombreAccion())) {
            throw new IllegalArgumentException("El símbolo de la acción no es válido: " + accion.getNombreAccion());
        }

        // Verificar que el usuario exista
        Usuario usuario = usuarioService.obtenerUsuarioPorId(accion.getUsuario().getIdUsuario());
        accion.setUsuario(usuario);

        // Usar la fecha proporcionada por el usuario
        LocalDate fechaSeleccionada = accion.getFecha();
        if (fechaSeleccionada == null || fechaSeleccionada.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha no puede ser futura y debe ser válida.");
        }

        // Obtener el precio basado en la fecha seleccionada
        Double precio = obtenerPrecioPorSimboloYFecha(accion.getNombreAccion(), fechaSeleccionada);
        accion.setPrecio(precio);

        // Guardar la acción con la fecha seleccionada y el precio correspondiente
        return accionRepository.save(accion);
    }

    public Double obtenerPrecioPorSimboloYFecha(String simbolo, LocalDate fecha) {
        try {
            if (fecha.isEqual(LocalDate.now())) {
                // Si la fecha es hoy, obtener el precio actual
                Double precioActual = tiingoService.obtenerPrecioActual(simbolo);
                if (precioActual == null) {
                    throw new IllegalArgumentException("No se pudo obtener el precio actual para el símbolo: " + simbolo);
                }
                return precioActual;
            }

            // Para fechas anteriores, obtener el precio de cierre
            Double precioPorFecha = tiingoService.obtenerPrecioAccionPorFecha(simbolo, fecha);
            if (precioPorFecha == null) {
                throw new IllegalArgumentException("No se encontraron datos para el símbolo " + simbolo + " en la fecha " + fecha);
            }

            return precioPorFecha;
        } catch (IllegalArgumentException e) {
            System.out.println("Error específico: " + e.getMessage());
            throw e; // Lanza la excepción para manejarla en el controlador
        } catch (Exception e) {
            System.out.println("Error general al obtener precio: " + e.getMessage());
            throw new RuntimeException("Error interno al obtener el precio de la acción.");
        }
    }


    public List<Accion> obtenerAccionesPorUsuario(Long idUsuario) {
        return accionRepository.findByUsuarioIdUsuario(idUsuario);
    }

    public Accion obtenerAccionPorId(Long accionId) {
        return accionRepository.findById(accionId)
                .orElseThrow(() -> new RuntimeException("Acción no encontrada"));
    }

    public Map<String, Object> calcularGananciaPerdida(Long accionId) {
        Accion accion = obtenerAccionPorId(accionId);

        // Obtener el precio actual desde la API de Tiingo
        Double precioActual = tiingoService.obtenerPrecioActual(accion.getNombreAccion());
        if (precioActual == null) {
            throw new RuntimeException("No se pudo obtener el precio actual de la acción.");
        }

        // Calcular ganancia o pérdida
        Double ganancia = null;
        Double perdida = null;

        if (precioActual > accion.getPrecio()) {
            ganancia = precioActual - accion.getPrecio();
        } else if (precioActual < accion.getPrecio()) {
            perdida = accion.getPrecio() - precioActual;
        }

        // Registrar el log para depuración
        System.out.println("Ganancia: " + ganancia + ", Pérdida: " + perdida + ", Precio Actual: " + precioActual);

        return Map.of(
                "ganancia", ganancia != null ? ganancia : 0.0, // Evitar valores nulos
                "perdida", perdida != null ? perdida : 0.0, // Evitar valores nulos
                "fechaActual", LocalDate.now().toString(),
                "valorActual", precioActual
        );
    }



    public void venderAccion(Map<String, Object> datos) {
        Long accionId = Long.valueOf(datos.get("accionId").toString());
        Integer cantidad = Integer.valueOf(datos.get("cantidad").toString());

        Accion accion = obtenerAccionPorId(accionId);

        if (cantidad > accion.getCantidad()) {
            throw new IllegalArgumentException("No puedes vender más acciones de las que posees.");
        }

        // Actualizar la cantidad de acciones
        accion.setCantidad(accion.getCantidad() - cantidad);

        // Si la cantidad restante es 0, se elimina la acción
        if (accion.getCantidad() == 0) {
            accionRepository.delete(accion);
        } else {
            accionRepository.save(accion);
        }
    }


    public Accion actualizarAccion(Accion accion) {
        // Verificar si la acción existe antes de actualizarla
        Accion accionExistente = accionRepository.findById(accion.getIdAccion())
                .orElseThrow(() -> new RuntimeException("Acción no encontrada para actualizar"));

        // Actualizar los datos necesarios
        accionExistente.setCantidad(accion.getCantidad());
        accionExistente.setPrecio(accion.getPrecio());
        accionExistente.setFecha(accion.getFecha());

        // Guardar y devolver la acción actualizada
        return accionRepository.save(accionExistente);
    }

    public void eliminarAccion(Long accionId) {
        // Verificar si la acción existe antes de eliminarla
        Accion accion = accionRepository.findById(accionId)
                .orElseThrow(() -> new RuntimeException("Acción no encontrada para eliminar"));

        // Eliminar la acción
        accionRepository.delete(accion);
    }


}
