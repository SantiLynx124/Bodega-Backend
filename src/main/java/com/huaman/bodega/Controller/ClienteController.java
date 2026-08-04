package com.huaman.bodega.Controller;

import com.huaman.bodega.DTO.ActualizarTopeRequest;
import com.huaman.bodega.DTO.RegistrarAbonoRequest;
import com.huaman.bodega.Entity.Cliente;
import com.huaman.bodega.Entity.CuentaFiado;
import com.huaman.bodega.Service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public Cliente registrar(@Valid @RequestBody Cliente cliente) {
        return clienteService.registrar(cliente);
    }

    @PutMapping("/{id}")
    public Cliente actualizar(@Valid @RequestBody Cliente cliente, @PathVariable Long id) {
        cliente.setId(id);
        return clienteService.actualizar(cliente);
    }

    @GetMapping("/listar")
    public List<Cliente> listar() {
        return clienteService.listar();
    }

    @GetMapping("/{id}")
    public Cliente buscarId(@PathVariable Long id) {
        return clienteService.buscarId(id);
    }

    @GetMapping("/buscar")
    public List<Cliente> buscarNombre(@RequestParam String nombre) {
        return clienteService.buscarNombre(nombre);
    }

    @PatchMapping("/{id}/activar")
    public Cliente activar(@PathVariable Long id) {
        return clienteService.activar(id);
    }

    @PatchMapping("/{id}/desactivar")
    public Cliente desactivar(@PathVariable Long id) {
        return clienteService.desactivar(id);
    }

    @GetMapping("/{id}/cuenta-fiado")
    public CuentaFiado verCuentaFiado(@PathVariable Long id) {
        return clienteService.verCuentaFiado(id);
    }

    @PatchMapping("/{id}/cuenta-fiado/tope")
    public CuentaFiado actualizarTope(@PathVariable Long id, @Valid @RequestBody ActualizarTopeRequest request) {
        return clienteService.actualizarTope(id, request.getTope());
    }

    @PatchMapping("/{id}/cuenta-fiado/activar")
    public CuentaFiado activarFiado(@PathVariable Long id) {
        return clienteService.activarFiado(id);
    }

    @PatchMapping("/{id}/cuenta-fiado/desactivar")
    public CuentaFiado desactivarFiado(@PathVariable Long id) {
        return clienteService.desactivarFiado(id);
    }

    @PostMapping("/{id}/cuenta-fiado/abono")
    public CuentaFiado registrarAbono(@PathVariable Long id, @Valid @RequestBody RegistrarAbonoRequest request) {
        return clienteService.registrarAbono(id, request.getMonto());
    }
}