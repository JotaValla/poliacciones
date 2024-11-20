package com.jotacode.poliacciones.controller;

import com.jotacode.poliacciones.model.Accion;
import com.jotacode.poliacciones.service.AccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acciones")
public class AccionController {

    @Autowired
    private AccionService accionService;


    @PostMapping("/comprar")
    public Accion comprarAccion(@RequestBody Accion accion) {
        return accionService.registrarCompra(accion);
    }


    @GetMapping("/usuario/{usuarioId}")
    public List<Accion> listarAcciones(@PathVariable Long usuarioId) {
        return accionService.obtenerAccionesPorUsuario(usuarioId);
    }

}
