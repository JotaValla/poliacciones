package com.jotacode.poliacciones.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity @Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venta_id")
    private Long idCuenta;

    @Column(name = "nombre_accion")
    private String nombreAccion;
    private Integer cantidad;
    private Double precio;
    private LocalDate fechaVenta;

    @Column(name = "ganancia_perdida")
    private Double gananciaPerdida;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;


}
