package com.huaman.bodega.Exception;

public class VentaInvalidaException extends RuntimeException {
    public VentaInvalidaException(String mensaje) {
        super(mensaje);
    }
}