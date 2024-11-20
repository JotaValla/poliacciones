package com.jotacode.poliacciones.repository;

import com.jotacode.poliacciones.model.Accion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccionRepository extends JpaRepository<Accion, Long> {
    List<Accion> findByUsuarioIdUsuario(Long idUsuario);
}
