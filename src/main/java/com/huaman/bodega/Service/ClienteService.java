package com.huaman.bodega.Service;

import com.huaman.bodega.Entity.Cliente;
import com.huaman.bodega.Entity.CuentaFiado;

import java.math.BigDecimal;
import java.util.List;

public interface ClienteService {
    Cliente registrar(Cliente cliente);
    Cliente actualizar(Cliente cliente);
    List<Cliente> listar();
    Cliente buscarId(Long id);
    List<Cliente> buscarNombre(String nombre);
    Cliente activar(Long id);
    Cliente desactivar(Long id);

    CuentaFiado verCuentaFiado(Long clienteId);
    CuentaFiado actualizarTope(Long clienteId, BigDecimal tope);
    CuentaFiado activarFiado(Long clienteId);
    CuentaFiado desactivarFiado(Long clienteId);
    CuentaFiado registrarAbono(Long clienteId, BigDecimal monto);
}