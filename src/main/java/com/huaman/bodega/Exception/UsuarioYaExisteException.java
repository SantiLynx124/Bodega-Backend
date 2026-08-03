package com.huaman.bodega.Exception;

public class UsuarioYaExisteException extends RuntimeException{
    public UsuarioYaExisteException(String mensaje) {
        super(mensaje);
    }
}