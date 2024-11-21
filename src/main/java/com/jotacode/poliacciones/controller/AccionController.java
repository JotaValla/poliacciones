package com.jotacode.poliacciones.controller;

import com.jotacode.poliacciones.model.Accion;
import com.jotacode.poliacciones.service.AccionService;
import com.jotacode.poliacciones.service.AlphaVantageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/acciones")
public class AccionController {

    @Autowired
    private AccionService accionService;

    @Autowired
    private AlphaVantageService alphaVantageService;

    @PostMapping("/comprar")
    public Accion comprarAccion(@RequestBody Accion accion) {
        return accionService.registrarCompra(accion);
    }


    @GetMapping("/usuario/{usuarioId}")
    public List<Accion> listarAcciones(@PathVariable Long usuarioId) {
        return accionService.obtenerAccionesPorUsuario(usuarioId);
    }

    @GetMapping("/buscar")
    public Map<String, Object> buscarAccion(
            @RequestParam String simbolo,
            @RequestParam String fecha
    ) {
        LocalDate fechaConsulta = LocalDate.parse(fecha);
        LocalDate fechaActual = LocalDate.now();

        if (fechaConsulta.isAfter(fechaActual)) {
            throw new IllegalArgumentException("La fecha no puede ser en el futuro.");
        }

        try {
            Double precio = accionService.obtenerPrecioPorSimboloYFecha(simbolo, fechaConsulta);

            return Map.of(
                    "simbolo", simbolo,
                    "precio", precio,
                    "fecha", fechaConsulta.toString()
            );
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
            throw new RuntimeException("Error interno del servidor. Intenta de nuevo más tarde.");
        }
    }





}
