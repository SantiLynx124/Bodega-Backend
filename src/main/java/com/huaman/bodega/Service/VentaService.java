package com.huaman.bodega.Service;

import com.huaman.bodega.DTO.CrearVentaRequest;
import com.huaman.bodega.DTO.VentaResponse;
import com.huaman.bodega.Entity.Usuario;
import com.huaman.bodega.Entity.Venta;

import java.util.List;

public interface VentaService {
    VentaResponse registrar(CrearVentaRequest request, Usuario usuarioLogueado);
    Venta anular(Long id);
    Venta buscarId(Long id);
    List<Venta> buscarCliente(Long clienteId);

    List<Venta> listar();
}