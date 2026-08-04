package com.huaman.bodega.Exception;

public class VentaNoEncontradaException extends RuntimeException {
    public VentaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}