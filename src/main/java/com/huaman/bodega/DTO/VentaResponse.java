package com.huaman.bodega.DTO;

import com.huaman.bodega.Entity.Venta;
import lombok.Getter;

@Getter
public class VentaResponse {

    private final Venta venta;
    private final boolean excedeLimiteFiado;
    private final String mensajeAdvertencia;

    public VentaResponse(Venta venta, boolean excedeLimiteFiado, String mensajeAdvertencia) {
        this.venta = venta;
        this.excedeLimiteFiado = excedeLimiteFiado;
        this.mensajeAdvertencia = mensajeAdvertencia;
    }
}