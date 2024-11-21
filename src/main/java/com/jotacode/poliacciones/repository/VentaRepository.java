package com.jotacode.poliacciones.repository;

import com.jotacode.poliacciones.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByAccion_IdAccion(Long accionId);

}
