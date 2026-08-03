package com.huaman.bodega.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validationException(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errores);
    }

    //Producto no encontrado exception
    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<String> ProductoNoEncontradoException(ProductoNoEncontradoException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    //Producto ya existe exception
    @ExceptionHandler(ProductoYaExisteException.class)
    public ResponseEntity<String> ProductoYaExisteException(ProductoYaExisteException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    //Stock insuficiente exception
    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<String> StockInsuficienteException(StockInsuficienteException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    //Usuario ya existe exception
    @ExceptionHandler(UsuarioYaExisteException.class)
    public ResponseEntity<String> UsuarioYaExisteException(UsuarioYaExisteException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    //Usuario encontrado exception
    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<String> UsuarioNoEncontradoException(UsuarioNoEncontradoException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> excepcionGeneral(Exception ex) {
        return ResponseEntity.status(500).body("Ocurrió un error inesperado en el servidor");
    }
}
