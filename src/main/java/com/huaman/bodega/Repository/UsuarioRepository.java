package com.huaman.bodega.Repository;

import com.huaman.bodega.Entity.Rol;
import com.huaman.bodega.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsuario(String usuario);
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
    boolean existsByUsuario(String usuario);
    boolean existsByRol(Rol rol);
    List<Usuario> findByEstadoTrue();
    List<Usuario> findByEstadoFalse();

}
