package com.huaman.bodega.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ActualizarConfiguracionFiadoRequest {
    //Atributos - Actualizar Configuración Fiado Request

    @NotNull
    @PositiveOrZero
    private BigDecimal limiteFiadoGlobal;

    @NotNull
    private Boolean fiadoHabilitadoGlobal;
}
