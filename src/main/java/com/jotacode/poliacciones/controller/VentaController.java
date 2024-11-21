package com.jotacode.poliacciones.controller;

import com.jotacode.poliacciones.model.Venta;
import com.jotacode.poliacciones.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping("/{accionId}")
    public List<Venta> obtenerHistorialVentas(@PathVariable Long accionId) {
        return ventaService.obtenerVentasPorAccion(accionId);
    }


    @PostMapping
    public void registrarVenta(@RequestBody Venta datosVenta) {
        try {
            ventaService.registrarVenta(datosVenta);
        } catch (IllegalArgumentException e) {
            System.out.println("Error al registrar la venta: " + e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al registrar la venta: " + e.getMessage());
            throw new RuntimeException("Error interno al registrar la venta.");
        }
    }

}
