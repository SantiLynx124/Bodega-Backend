package com.huaman.bodega.Exception;

public class ClienteYaExisteException extends RuntimeException {
    public ClienteYaExisteException(String mensaje) {
        super(mensaje);
    }
}