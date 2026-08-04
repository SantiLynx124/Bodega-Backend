package com.huaman.bodega.DTO;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ActualizarTopeRequest {
    // null = usar el límite global en vez de uno individual
    @PositiveOrZero
    private BigDecimal tope;
}