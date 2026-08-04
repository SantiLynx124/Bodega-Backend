package com.huaman.bodega.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "cuenta_fiados")
@Getter
@Setter
@NoArgsConstructor
public class CuentaFiado {
    //Atributos - CuentaFiado

    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Cliente
    @OneToOne
    @NotNull
    @JoinColumn(name = "cliente_id",nullable = false)
    private Cliente cliente;

    //Saldo
    @NotNull
    @PositiveOrZero
    @Column(precision = 8, scale = 2, nullable = false)
    private BigDecimal saldo = BigDecimal.ZERO;

    //Tope de crédito individual
    @PositiveOrZero
    @Column(precision = 8, scale = 2, nullable = true)
    private BigDecimal topeIndividual;

    //Fiado habilitado
    @Column(nullable = false)
    private boolean fiadoHabilitado = true;
}
