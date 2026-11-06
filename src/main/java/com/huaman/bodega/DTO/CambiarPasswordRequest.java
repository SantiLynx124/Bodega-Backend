package com.huaman.bodega.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CambiarPasswordRequest {
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
