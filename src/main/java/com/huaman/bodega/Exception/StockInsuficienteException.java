package com.huaman.bodega.Exception;

public class StockInsuficienteException extends RuntimeException{
    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
