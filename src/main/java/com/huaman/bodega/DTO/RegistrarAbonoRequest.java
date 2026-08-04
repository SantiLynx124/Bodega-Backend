package com.huaman.bodega.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RegistrarAbonoRequest {
    @NotNull
    @Positive
    private BigDecimal monto;
}