package com.huaman.bodega.Exception;

public class ProductoYaExisteException extends RuntimeException{
    public ProductoYaExisteException(String mensaje) {
        super(mensaje);
    }
}
