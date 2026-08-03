package com.huaman.bodega.Config;

import com.huaman.bodega.Entity.Rol;
import com.huaman.bodega.Entity.Usuario;
import com.huaman.bodega.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!usuarioRepository.existsByRol(Rol.ADMINISTRADOR)) {
            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setUsuario("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // contraseña temporal
            admin.setRol(Rol.ADMINISTRADOR);
            admin.setEstado(true);
            usuarioRepository.save(admin);
            System.out.println("Admin por defecto creado: usuario=admin, password=admin123");
        }
    }
}