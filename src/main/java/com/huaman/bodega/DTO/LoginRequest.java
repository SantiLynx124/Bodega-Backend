package com.huaman.bodega.DTO;

import lombok.Data;

@Data
public class LoginRequest {
    private String usuario;
    private String password;
}
