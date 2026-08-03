package com.huaman.bodega.Config;

import com.huaman.bodega.Entity.Usuario;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

public class UsuarioDetails implements UserDetails {
    private final Usuario usuario;

    public UsuarioDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String rolConPrefijo = "ROLE_" + usuario.getRol().name();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(rolConPrefijo);
        return List.of(authority);
    }

    @Override
    public @Nullable String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getUsuario();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return usuario.isEstado();
    }

}
