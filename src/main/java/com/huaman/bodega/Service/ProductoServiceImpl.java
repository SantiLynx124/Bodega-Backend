package com.huaman.bodega.Service;

import com.huaman.bodega.Entity.Producto;
import com.huaman.bodega.Exception.ProductoNoEncontradoException;
import com.huaman.bodega.Exception.ProductoYaExisteException;
import com.huaman.bodega.Exception.StockInsuficienteException;
import com.huaman.bodega.Repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productoRepository;

    @Override
    public Producto Registrar(Producto producto) {
        if(productoRepository.existsByNombreAndMarca(producto.getNombre(), producto.getMarca())){
            throw new ProductoYaExisteException("El producto ya existe");
        } else {
            return productoRepository.save(producto);
        }
    }

    @Override
    public Producto Actualizar(Producto producto) {
        Producto productoFinal = BuscarId(producto.getId());
        productoFinal.setNombre(producto.getNombre());
        productoFinal.setMarca(producto.getMarca());
        productoFinal.setDescripcion(producto.getDescripcion());
        productoFinal.setPrecioVenta(producto.getPrecioVenta());
        productoFinal.setPrecioCompra(producto.getPrecioCompra());
        productoFinal.setStock(producto.getStock());
        productoFinal.setUnidadMetrica(producto.getUnidadMetrica());
        return productoRepository.save(productoFinal);
    }

    @Override
    public List<Producto> Listar() {
        return productoRepository.findByEstadoTrue();
    }

    @Override
    public List<Producto> ListarDesactivados() {
        return productoRepository.findByEstadoFalse();
    }

    @Override
    public Producto Desactivar(Long id) {
        Producto productoFinal = BuscarId(id);
        productoFinal.setEstado(false);
        productoRepository.save(productoFinal);
        return productoFinal;
    }

    @Override
    public Producto Activar(Long id) {
        Producto productoFinal = BuscarId(id);
        productoFinal.setEstado(true);
        productoRepository.save(productoFinal);
        return productoFinal;
    }

    @Override
    public Producto BuscarId(long id) {
        return productoRepository.findById(id).orElseThrow(() -> new ProductoNoEncontradoException("El producto con el id " + id + " no existe"));
    }

    @Override
    public List<Producto> BuscarNombre(String nombre) {
        return productoRepository.findByEstadoTrueAndNombreContainingIgnoreCaseOrEstadoTrueAndMarcaContainingIgnoreCase(nombre, nombre);
    }

    @Override
    public Producto AumentarStock(Long id, BigDecimal cantidad) {
        Producto productoFinal = BuscarId(id);
        productoFinal.setStock( productoFinal.getStock().add(cantidad));
        productoRepository.save(productoFinal);
        return productoFinal;
    }

    @Override
    public Producto DisminuirStock(Long id, BigDecimal cantidad) {
        Producto productoFinal = BuscarId(id);
        if (0 >= cantidad.compareTo(productoFinal.getStock())){
            productoFinal.setStock(productoFinal.getStock().subtract(cantidad));
            productoRepository.save(productoFinal);
            return productoFinal;
        } else {
            throw new StockInsuficienteException("El producto: " + productoFinal.getNombre() + " no tiene stock suficiente");
        }
    }
}
