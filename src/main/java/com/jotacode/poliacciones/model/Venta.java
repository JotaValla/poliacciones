package com.jotacode.poliacciones.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

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
    @NotNull(message = "La acción es requerida") @NotBlank(message = "La acción es requerida")
    private Accion accion;

    @NotNull(message = "La cantidad es requerida") @NotBlank(message = "La cantidad es requerida")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @NotNull(message = "El precio es requerido") @NotBlank(message = "El precio es requerido")
    private Double precio;

    @NotNull(message = "La fecha de venta es requerida") @NotBlank(message = "La fecha de venta es requerida")
    private LocalDate fechaVenta;

}
