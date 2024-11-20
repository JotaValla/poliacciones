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

        // Agregar la fecha actual y el precio desde la API
        Double precioTotal = alphaVantageService.obtenerPrecioAccion(accion.getNombreAccion()) * accion.getCantidad();
        accion.setPrecio(precioTotal);
        accion.setFecha(LocalDate.now());

        // Guardar la acción en la base de datos
        return accionRepository.save(accion);
    }



    public List<Accion> obtenerAccionesPorUsuario(Long idUsuario) {
        return accionRepository.findByUsuarioIdUsuario(idUsuario);
    }

}
