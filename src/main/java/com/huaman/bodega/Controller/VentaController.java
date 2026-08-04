package com.huaman.bodega.Controller;

import com.huaman.bodega.Config.UsuarioDetails;
import com.huaman.bodega.DTO.CrearVentaRequest;
import com.huaman.bodega.DTO.VentaResponse;
import com.huaman.bodega.Entity.Usuario;
import com.huaman.bodega.Entity.Venta;
import com.huaman.bodega.Service.UsuarioService;
import com.huaman.bodega.Service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;
    private final UsuarioService usuarioService;

    @PostMapping
    public VentaResponse registrar(
            @Valid @RequestBody CrearVentaRequest request,
            @AuthenticationPrincipal UsuarioDetails usuarioDetails
    ) {
        Usuario usuarioLogueado = usuarioService.buscarUsuario(usuarioDetails.getUsername());
        return ventaService.registrar(request, usuarioLogueado);
    }

    @PatchMapping("/{id}/anular")
    public Venta anular(@PathVariable Long id) {
        return ventaService.anular(id);
    }

    @GetMapping("/{id}")
    public Venta buscarId(@PathVariable Long id) {
        return ventaService.buscarId(id);
    }

    @GetMapping
    public List<Venta> listar() {
        return ventaService.listar();
    }
}