package com.huaman.bodega.Service;

import com.huaman.bodega.Entity.Usuario;

import java.util.List;

public interface UsuarioService {

    //Registrar usuario
    public Usuario registrar(Usuario usuario);

    //Actualizar usuario
    public Usuario actualizar(Usuario usuario);

    //Buscar usuario por id
    public Usuario buscarId(Long id);

    //buscar usuario por usuario
    public Usuario buscarUsuario(String usuario);

    //Buscar usuario por nombre
    public List<Usuario> buscarNombre(String nombre);

    //Listar todos los usuarios
    public List<Usuario> listar();

    //Activar usuario
    public Usuario activarUsuario(Long id);

    //Desactivar usuario
    public Usuario desactivarUsuario(Long id);

    //Cambiar contraseña
    public Usuario cambiarContraseña(Long id,String password);

    //Cambiar Usuario
    public Usuario cambiarUsuario(Long id,String usuario);
}
