package com.huaman.bodega.Service;

import com.huaman.bodega.Entity.Usuario;
import com.huaman.bodega.Exception.UsuarioNoEncontradoException;
import com.huaman.bodega.Exception.UsuarioYaExisteException;
import com.huaman.bodega.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository UsuarioRepository;

    //Registrar usuario
    @Override
    public Usuario registrar(Usuario usuario) {
        if (UsuarioRepository.existsByUsuario(usuario.getUsuario())) {
            throw new UsuarioYaExisteException("El usuario ya existe");
        } else {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            return UsuarioRepository.save(usuario);
        }
    }

    //Actualizar usuario
    @Override
    public Usuario actualizar(Usuario usuario) {
        Usuario usuarioFinal = buscarId(usuario.getId());
        usuarioFinal.setNombre(usuario.getNombre());
        usuarioFinal.setRol(usuario.getRol());
        return UsuarioRepository.save(usuarioFinal);
    }

    //Buscar usuario por ID
    @Override
    public Usuario buscarId(Long id) {
        return UsuarioRepository.findById(id).orElseThrow(() -> new UsuarioNoEncontradoException("El usuario con el id " + id + " no existe"));
    }

    //Buscar usuario por usuario
    @Override
    public Usuario buscarUsuario(String usuario) {
        return UsuarioRepository.findByUsuario(usuario).orElseThrow(() -> new UsuarioNoEncontradoException("El usuario con el usuario " + usuario + " no existe"));
    }

    //Buscar usuario por nombre
    @Override
    public List<Usuario> buscarNombre(String nombre) {
        return UsuarioRepository.findByNombreContainingIgnoreCase(nombre);
    }

    //Listar todos los usuarios activos
    @Override
    public List<Usuario> listar() {
        return UsuarioRepository.findByEstadoTrue();
    }

    //Listar todos los usuarios desactivados
    @Override
    public List<Usuario> listarDesactivados() {
        return UsuarioRepository.findByEstadoFalse();
    }

    //Activar usuario
    @Override
    public Usuario activarUsuario(Long id) {
        Usuario usuarioFinal = buscarId(id);
        usuarioFinal.setEstado(true);
        return UsuarioRepository.save(usuarioFinal);
    }

    //Desactivar usuario
    @Override
    public Usuario desactivarUsuario(Long id) {
        Usuario usuarioFinal = buscarId(id);
        usuarioFinal.setEstado(false);
        return UsuarioRepository.save(usuarioFinal);
    }

    //Cambiar Contraseña
    @Override
    public Usuario cambiarContraseña(Long id, String password) {
        Usuario usuarioFinal = buscarId(id);
        usuarioFinal.setPassword(passwordEncoder.encode(password));
        return UsuarioRepository.save(usuarioFinal);
    }

    //Cambiar Usuario
    @Override
    public Usuario cambiarUsuario(Long id, String usuario) {
        Optional<Usuario> usuarioConEseNombre = UsuarioRepository.findByUsuario(usuario);

        if (usuarioConEseNombre.isPresent() && !usuarioConEseNombre.get().getId().equals(id)) {
            throw new UsuarioYaExisteException("El usuario ya existe");
        } else {
            Usuario usuarioFinal = buscarId(id);
            usuarioFinal.setUsuario(usuario);
            return UsuarioRepository.save(usuarioFinal);
        }
    }
}
