package com.jotacode.poliacciones.service;

import com.jotacode.poliacciones.model.Accion;
import com.jotacode.poliacciones.model.Usuario;
import com.jotacode.poliacciones.repository.AccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccionService {

    @Autowired
    private AccionRepository accionRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AlphaVantageService alphaVantageService;

    public Accion registrarCompra(Accion accion) {
        // Verificar si el símbolo de la acción es válido
        if (!alphaVantageService.verificarSimbolo(accion.getNombreAccion())) {
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
        System.out.println("Buscando precio para símbolo: " + simbolo + " en la fecha: " + fecha);

        // Verifica que el símbolo sea válido
        if (!alphaVantageService.verificarSimbolo(simbolo)) {
            throw new IllegalArgumentException("El símbolo de la acción no es válido: " + simbolo);
        }

        // Si la fecha es hoy, obtener el precio actual
        if (fecha.isEqual(LocalDate.now())) {
            Double precioActual = alphaVantageService.obtenerPrecioActual(simbolo);
            System.out.println("Precio actual: " + precioActual);
            return precioActual;
        }

        // Para fechas anteriores, obtener el precio de cierre
        Double precio = alphaVantageService.obtenerPrecioAccionPorFecha(simbolo, fecha);
        System.out.println("Precio de cierre: " + precio);

        if (precio == null) {
            throw new IllegalArgumentException(
                    "No se encontraron datos para el símbolo " + simbolo + " en la fecha " + fecha
            );
        }

        return precio;
    }



    public List<Accion> obtenerAccionesPorUsuario(Long idUsuario) {
        return accionRepository.findByUsuarioIdUsuario(idUsuario);
    }

}
