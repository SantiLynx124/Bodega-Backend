package com.huaman.bodega.Controller;

import com.huaman.bodega.Entity.Producto;
import com.huaman.bodega.Service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;

    //Listar todos los productos
    @GetMapping("/listar")
    public List<Producto> listar(){
        return productoService.Listar();
    }

    //Listar todos los productos desactivados
    @GetMapping("/listar-desactivados")
    public List<Producto> listarDesactivados(){
        return productoService.ListarDesactivados();
    }

    //Buscar por id
    @GetMapping("/{id}")
    public Producto buscarId(@Valid @PathVariable Long id){
        return productoService.BuscarId(id);
    }

    //Buscar por nombre
    @GetMapping("/buscar")
    public List<Producto> buscarNombre(@Valid @RequestParam String nombre){
        return productoService.BuscarNombre(nombre);
    }

    //Registrar un producto
    @PostMapping
    public Producto registrar(@Valid @RequestBody Producto producto){
        return productoService.Registrar(producto);
    }

    //Actualizar un producto
    @PutMapping("/{id}")
    public Producto actualizar(@Valid @RequestBody Producto producto, @PathVariable Long id){
        producto.setId(id);
        return productoService.Actualizar(producto);
    }

    //Desactivar un producto
    @PatchMapping("/{id}/desactivar")
    public Producto desactivar(@Valid @PathVariable Long id){
        return productoService.Desactivar(id);
    }

    //Activar un producto
    @PatchMapping("/{id}/activar")
    public Producto activar(@Valid @PathVariable Long id){
        return productoService.Activar(id);
    }

    //Aumentar Stock
    @PatchMapping("/{id}/aumentar-stock")
    public Producto aumentarStock(@Valid @PathVariable Long id, @RequestParam BigDecimal cantidad){
        return productoService.AumentarStock(id, cantidad);
    }

    //Disminuir Stock
    @PatchMapping("/{id}/disminuir-stock")
    public Producto disminuirStock(@Valid @PathVariable Long id, @RequestParam BigDecimal cantidad){
        return productoService.DisminuirStock(id, cantidad);
    }
}
