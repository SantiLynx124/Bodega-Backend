package com.huaman.bodega.DTO;

import com.huaman.bodega.Entity.MetodoPago;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CrearVentaRequest {

    // null = cliente no registrado (venta anónima)
    private Long clienteId;

    @NotNull
    private MetodoPago metodoPago;

    @NotEmpty
    @Valid
    private List<ItemVentaRequest> items;
}