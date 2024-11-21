package com.jotacode.poliacciones.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "ventas")
@Entity
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "accion_id", nullable = false)
    private Accion accion;
    private Integer cantidad;
    private Double precio;
    private LocalDate fechaVenta;

}
