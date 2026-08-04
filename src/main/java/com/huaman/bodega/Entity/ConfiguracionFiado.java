package com.huaman.bodega.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "configuracion_fiado")
@Getter
@Setter
@NoArgsConstructor
public class ConfiguracionFiado {

    @Id
    private Long id = 1L;

    @NotNull
    @PositiveOrZero
    @Column(precision = 8, scale = 2, nullable = false)
    private BigDecimal limiteFiadoGlobal = new BigDecimal("200.00");

    @Column(nullable = false)
    private boolean fiadoHabilitadoGlobal = true;
}