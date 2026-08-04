package com.huaman.bodega.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "productos", uniqueConstraints = @UniqueConstraint(columnNames = { "nombre", "marca"}))
@Setter
@Getter
@NoArgsConstructor
public class Producto {
    //Atributos - Producto:

    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Nombre
    @NotBlank
    @Column(length = 100, nullable = false)
    private String nombre;

    //Marca
    @NotBlank
    @Column(length = 100, nullable = false)
    private String marca;

    //Descripción
    @Column(length = 255, nullable = true)
    private String descripcion;

    //Precio de venta
    @NotNull
    @PositiveOrZero
    @Column(precision = 8, scale = 2, nullable = false)
    private BigDecimal precioVenta;

    //Precio de compra
    @PositiveOrZero
    @Column(precision = 8, scale = 2, nullable = true)
    private BigDecimal precioCompra;

    //Unidad de medida
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnidadMetrica unidadMetrica;

    //Stock
    @NotNull
    @PositiveOrZero
    @Column(precision = 8, scale = 2, nullable = false)
    private BigDecimal stock;

    //Estado
    @Column(nullable = false)
    private boolean estado = true;
}
