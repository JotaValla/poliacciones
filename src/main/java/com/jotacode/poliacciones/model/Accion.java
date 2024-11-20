package com.jotacode.poliacciones.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity @Table(name = "acciones")
public class Accion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accion_id")
    private Long idAccion;

    @Column(name = "nombre_accion")
    private String nombreAccion;
    private Integer cantidad;
    private Double precio;
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

}
