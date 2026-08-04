package com.huaman.bodega.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
public class Cliente {
    //Atributos - Clientes

    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Nombre
    @NotBlank
    @Column(length = 100, nullable = false)
    private String nombre;

    //Teléfono
    @Column(length = 10, nullable = true)
    private String telefono;

    //Descripción
    @Column(length = 255, nullable = true)
    private String descripcion;

    //Estado
    @Column(nullable = false)
    private boolean estado = true;
}
