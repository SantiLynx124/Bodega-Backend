package com.huaman.bodega.Controller;

import com.huaman.bodega.Config.UsuarioDetails;
import com.huaman.bodega.DTO.CambiarPasswordRequest;
import com.huaman.bodega.DTO.CambiarUsuarioRequest;
import com.huaman.bodega.DTO.UsuarioResponse;
import com.huaman.bodega.Entity.Usuario;
import com.huaman.bodega.Service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController{
    private final UsuarioService usuarioService;

    //Registrar usuario
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity.ok(UsuarioResponse.fromEntity(usuarioService.registrar(usuario)));
    }

    //Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@Valid@RequestBody Usuario usuario, @Valid@PathVariable Long id){
        usuario.setId(id);
        return ResponseEntity.ok(UsuarioResponse.fromEntity(usuarioService.actualizar(usuario)));
    }

    //listar usuarios
    @GetMapping("/listar")
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok(usuarioService.listar().stream().map(UsuarioResponse::fromEntity).toList());
    }

    @GetMapping("listar-desactivos")
    public ResponseEntity<?> listarDesactivados(){
        return ResponseEntity.ok(usuarioService.listarDesactivados().stream().map(UsuarioResponse::fromEntity).toList());
    }

    //Buscar Usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarId(@Valid @PathVariable Long id){
        return ResponseEntity.ok(UsuarioResponse.fromEntity(usuarioService.buscarId(id)));
    }

    //Buscar Usuario por nombre
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarUsuario(@Valid @RequestParam String usuario){
        return ResponseEntity.ok(UsuarioResponse.fromEntity(usuarioService.buscarUsuario(usuario)));
    }

    //Activar usuario
    @PatchMapping("/{id}/activar")
    public ResponseEntity<?> activarUsuario(@Valid @PathVariable Long id){
        return ResponseEntity.ok(UsuarioResponse.fromEntity(usuarioService.activarUsuario(id)));
    }

    //Desactivar usuario
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<?> desactivarUsuario(@Valid @PathVariable Long id){
        return ResponseEntity.ok(UsuarioResponse.fromEntity(usuarioService.desactivarUsuario(id)));
    }

    //Cambiar Contraseña
    @PatchMapping("/{id}/password")
    public ResponseEntity<?> cambiarContraseña(@Valid @PathVariable Long id,@Valid @RequestBody CambiarPasswordRequest request){
        return ResponseEntity.ok(UsuarioResponse.fromEntity(usuarioService.cambiarContraseña(id, request.getPassword())));
    }

    @PatchMapping("/{id}/usuario")
    public ResponseEntity<?> cambiarUsuario(@Valid @PathVariable Long id,@Valid @RequestBody CambiarUsuarioRequest request){
        return ResponseEntity.ok(UsuarioResponse.fromEntity(usuarioService.cambiarUsuario(id, request.getUsuario())));
    }

    @PatchMapping("/me/contraseña")
    public ResponseEntity<?> cambiarMiContraseña(@Valid @AuthenticationPrincipal UsuarioDetails usuarioDetails,@Valid @RequestBody CambiarPasswordRequest request) {
        Usuario usuario = usuarioService.buscarUsuario(usuarioDetails.getUsername());
        return ResponseEntity.ok(UsuarioResponse.fromEntity(usuarioService.cambiarContraseña(usuario.getId(), request.getPassword())));
    }

    @PatchMapping("/me/usuario")
    public ResponseEntity<?> cambiarMiUsuario(@Valid @AuthenticationPrincipal UsuarioDetails usuarioDetails,@Valid @RequestBody CambiarUsuarioRequest request) {
        Usuario usuario = usuarioService.buscarUsuario(usuarioDetails.getUsername());
        return ResponseEntity.ok(UsuarioResponse.fromEntity(usuarioService.cambiarUsuario(usuario.getId(), request.getUsuario())));
    }
}
