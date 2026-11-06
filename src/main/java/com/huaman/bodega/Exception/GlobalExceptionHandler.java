package com.huaman.bodega.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //Body vacío o JSON mal formado: antes caía en el handler genérico y devolvía 500
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> bodyInvalido(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("El cuerpo de la solicitud está vacío o mal formado");
    }

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
        // Sin este log, los errores 500 quedaban invisibles en los logs del servidor.
        log.error("Error no controlado en la API", ex);
        return ResponseEntity.status(500).body("Ocurrió un error inesperado en el servidor");
    }

    @ExceptionHandler(ClienteNoEncontradoException.class)
    public ResponseEntity<String> clienteNoEncontradoException(ClienteNoEncontradoException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(VentaInvalidaException.class)
    public ResponseEntity<String> ventaInvalidaException(VentaInvalidaException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(VentaNoEncontradaException.class)
    public ResponseEntity<String> ventaNoEncontradaException(VentaNoEncontradaException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(ClienteYaExisteException.class)
    public ResponseEntity<String> clienteYaExisteException(ClienteYaExisteException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }
}
