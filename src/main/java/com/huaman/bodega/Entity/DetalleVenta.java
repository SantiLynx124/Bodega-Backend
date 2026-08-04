package com.huaman.bodega.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_ventas")
@Getter
@Setter
@NoArgsConstructor
public class DetalleVenta {
    //Atributos - DetalleVenta:

    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Venta
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    //Producto
    @NotNull
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    //Cantidad
    @NotNull
    @Positive
    @Column(precision = 8, scale = 2, nullable = false)
    private BigDecimal cantidad;

    //Precio unitario
    @NotNull
    @Positive
    @Column(precision = 8, scale = 2, nullable = false)
    private BigDecimal precioUnitario;

    //Subtotal
    public BigDecimal getSubtotal() {
        return precioUnitario.multiply(cantidad);
    }
}