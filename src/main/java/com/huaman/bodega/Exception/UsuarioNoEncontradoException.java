package com.huaman.bodega.Exception;

public class UsuarioNoEncontradoException extends RuntimeException{
    public UsuarioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
