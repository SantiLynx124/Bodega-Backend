package com.huaman.bodega.DTO;

import com.huaman.bodega.Entity.Rol;
import com.huaman.bodega.Entity.Usuario;

public record UsuarioResponse(Long id, String nombre, String usuario, Rol rol, boolean estado) {
    public static UsuarioResponse fromEntity(Usuario usuario) {
        return new UsuarioResponse(usuario.getId(), usuario.getNombre(), usuario.getUsuario(), usuario.getRol(), usuario.isEstado());
    }
}