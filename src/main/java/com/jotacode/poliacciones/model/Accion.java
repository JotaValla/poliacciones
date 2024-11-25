package com.jotacode.poliacciones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "Nombre de la acción es requerido") @NotNull(message = "Nombre de la acción es requerido")
    private String nombreAccion;

    @NotNull(message = "Cantidad es requerida") @NotBlank(message = "Cantidad es requerida")
    @Min(value = 1, message = "Cantidad debe ser mayor a 0")
    private Integer cantidad;

    @NotNull(message = "Precio es requerido") @NotBlank(message = "Precio es requerido")
    private Double precio;

    @NotNull(message = "Fecha es requerida") @NotBlank(message = "Fecha es requerida")
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

}
