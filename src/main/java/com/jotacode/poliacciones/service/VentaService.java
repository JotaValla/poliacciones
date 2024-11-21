package com.jotacode.poliacciones.service;

import com.jotacode.poliacciones.model.Accion;
import com.jotacode.poliacciones.model.Venta;
import com.jotacode.poliacciones.repository.VentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private AccionService accionService;


    public List<Venta> obtenerVentasPorAccion(Long accionId) {
        return ventaRepository.findByAccion_IdAccion(accionId);
    }

    public void registrarVenta(Venta venta) {
        Accion accion = accionService.obtenerAccionPorId(venta.getAccion().getIdAccion());

        if (venta.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad de venta debe ser mayor a cero.");
        }

        if (venta.getCantidad() > accion.getCantidad()) {
            throw new IllegalArgumentException("No puedes vender más acciones de las que posees.");
        }

        // Actualizar la cantidad restante de la acción
        accion.setCantidad(accion.getCantidad() - venta.getCantidad());
        accionService.actualizarAccion(accion);

        // Registrar la venta
        venta.setFechaVenta(LocalDate.now());
        venta.setPrecio(accion.getPrecio());
        ventaRepository.save(venta);

        // Si la cantidad de la acción llega a 0, eliminar la acción
        if (accion.getCantidad() == 0) {
            accionService.eliminarAccion(accion.getIdAccion());
        }
    }


}
