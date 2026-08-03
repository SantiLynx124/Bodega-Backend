package com.huaman.bodega.Exception;

public class ProductoNoEncontradoException extends RuntimeException{
    public ProductoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
