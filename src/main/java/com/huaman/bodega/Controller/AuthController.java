package com.huaman.bodega.Controller;


import com.huaman.bodega.DTO.LoginRequest;
import com.huaman.bodega.Entity.Usuario;
import com.huaman.bodega.Exception.UsuarioNoEncontradoException;
import com.huaman.bodega.Service.JwtService;
import com.huaman.bodega.Service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioService usuarioService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Usuario usuario;
        try {
            usuario = usuarioService.buscarUsuario(loginRequest.getUsuario());
        } catch (UsuarioNoEncontradoException ex) {
            // No revelamos si el usuario existe o no (evita enumeración de usuarios).
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
        }

        if (!usuario.isEstado()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El usuario no está activo");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
        }

        return ResponseEntity.ok(jwtService.generarToken(usuario));
    }

}
