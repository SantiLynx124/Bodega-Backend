package com.huaman.bodega.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.huaman.bodega.Entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtService {
    //El secreto
    @Value("${jwt.secret}")
    private String secreto;

    @Value("${jwt.expiration}")
    private long expiracion;

    //Generar token
    public String generarToken(Usuario usuario){
        Algorithm algorithm = Algorithm.HMAC256(secreto);
        Date fechaExpiracion = new Date(System.currentTimeMillis() + expiracion);
        return JWT.create()
                .withSubject(usuario.getUsuario())
                .withClaim("rol", usuario.getRol().name())
                .withClaim("id", usuario.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(fechaExpiracion)
                .sign(algorithm);
    }

    public DecodedJWT validarToken(String token){
        return JWT.require(Algorithm.HMAC256(secreto)).build().verify(token);
    }
}
