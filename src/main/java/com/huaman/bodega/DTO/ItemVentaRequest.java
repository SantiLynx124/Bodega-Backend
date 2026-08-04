package com.huaman.bodega.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemVentaRequest {

    @NotNull
    private Long productoId;

    @NotNull
    @Positive
    private BigDecimal cantidad;
}