package com.huaman.bodega.Controller;

import com.huaman.bodega.DTO.ActualizarConfiguracionFiadoRequest;
import com.huaman.bodega.Entity.ConfiguracionFiado;
import com.huaman.bodega.Service.ConfiguracionFiadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/configuracion/fiado")
@RequiredArgsConstructor
public class ConfiguracionFiadoController {

    private final ConfiguracionFiadoService configuracionFiadoService;

    // Cualquier usuario autenticado puede consultarla (el vendedor necesita
    // saber el límite/estado global al momento de vender)
    @GetMapping
    public ConfiguracionFiado obtener() {
        return configuracionFiadoService.obtener();
    }

    // Solo el administrador puede cambiarla (restringido en SecurityConfig)
    @PutMapping
    public ConfiguracionFiado actualizar(@Valid @RequestBody ActualizarConfiguracionFiadoRequest request) {
        return configuracionFiadoService.actualizar(
                request.getLimiteFiadoGlobal(),
                request.getFiadoHabilitadoGlobal()
        );
    }
}