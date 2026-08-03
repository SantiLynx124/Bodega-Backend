package com.huaman.bodega.Service;

import com.huaman.bodega.Entity.Producto;
import com.huaman.bodega.Entity.UnidadMetrica;

import java.math.BigDecimal;
import java.util.List;

public interface ProductoService {

    public Producto Registrar(Producto producto);

    public Producto Actualizar(Producto producto);

    public List<Producto> Listar();

    public Producto Desactivar(Long id);

    public Producto Activar(Long id);

    public Producto BuscarId(long id);

    public List<Producto> BuscarNombre(String nombre);

    public Producto AumentarStock(Long id, BigDecimal cantidad);

    public Producto DisminuirStock(Long id, BigDecimal cantidad);
}
