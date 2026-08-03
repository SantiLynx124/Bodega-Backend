package com.huaman.bodega.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    //Atributos - Usurio:

    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Nombre
    @NotBlank
    @Column(length = 100, nullable = false)
    private String nombre;

    //Usuario
    @NotBlank
    @Column(length = 20, nullable = false, unique = true)
    private String usuario;

    //Contraseña
    @NotBlank
    @Column(length = 100, nullable = false)
    private String password;

    //Rol
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    //Estado
    @Column(nullable = false)
    private boolean estado = true;

}
