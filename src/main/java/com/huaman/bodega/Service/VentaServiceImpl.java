package com.huaman.bodega.Service;

import com.huaman.bodega.DTO.CrearVentaRequest;
import com.huaman.bodega.DTO.ItemVentaRequest;
import com.huaman.bodega.DTO.VentaResponse;
import com.huaman.bodega.Entity.*;
import com.huaman.bodega.Exception.ClienteNoEncontradoException;
import com.huaman.bodega.Exception.VentaInvalidaException;
import com.huaman.bodega.Exception.VentaNoEncontradaException;
import com.huaman.bodega.Repository.ClienteRepository;
import com.huaman.bodega.Repository.CuentaFiadoRepository;
import com.huaman.bodega.Repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;
    private final CuentaFiadoRepository cuentaFiadoRepository;
    private final ProductoService productoService;
    private final ConfiguracionFiadoService configuracionFiadoService;

    @Override
    @Transactional
    public VentaResponse registrar(CrearVentaRequest request, Usuario usuarioLogueado) {

        // 1. Resolver cliente (null = venta anónima)
        Cliente cliente = null;
        if (request.getClienteId() != null) {
            cliente = clienteRepository.findById(request.getClienteId())
                    .orElseThrow(() -> new ClienteNoEncontradoException(
                            "El cliente con el id " + request.getClienteId() + " no existe"));
        }

        // 2. Si es fiado, validar que se pueda fiar antes de tocar el stock
        CuentaFiado cuentaFiado = null;
        if (request.getMetodoPago() == MetodoPago.FIADO) {
            if (cliente == null) {
                throw new VentaInvalidaException("No se puede fiar a un cliente no registrado");
            }

            ConfiguracionFiado configuracion = configuracionFiadoService.obtener();
            if (!configuracion.isFiadoHabilitadoGlobal()) {
                throw new VentaInvalidaException("El fiado está deshabilitado en este momento");
            }

            Cliente clienteParaCuenta = cliente;
            cuentaFiado = cuentaFiadoRepository.findByCliente(cliente)
                    .orElseGet(() -> crearCuentaFiado(clienteParaCuenta));

            if (!cuentaFiado.isFiadoHabilitado()) {
                throw new VentaInvalidaException(
                        "El cliente " + cliente.getNombre() + " tiene el fiado deshabilitado");
            }
        }

        // 3. Armar la venta y sus items, descontando stock de cada producto
        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setUsuario(usuarioLogueado);
        venta.setMetodoPago(request.getMetodoPago());
        venta.setEstado(EstadoVenta.REGISTRADA);

        BigDecimal montoTotal = BigDecimal.ZERO;

        for (ItemVentaRequest itemRequest : request.getItems()) {
            Producto producto = productoService.BuscarId(itemRequest.getProductoId());

            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(itemRequest.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecioVenta()); // copia el precio actual, no una referencia

            venta.getItems().add(detalle);
            montoTotal = montoTotal.add(detalle.getSubtotal());

            // Lanza StockInsuficienteException si no alcanza — @Transactional revierte todo lo anterior
            productoService.DisminuirStock(producto.getId(), itemRequest.getCantidad());
        }

        venta.setMontoTotal(montoTotal);
        Venta ventaGuardada = ventaRepository.save(venta);

        // 4. Si es fiado, cargar el monto a la cuenta y calcular si excede el límite
        boolean excedeLimite = false;
        String mensajeAdvertencia = null;

        if (cuentaFiado != null) {
            cuentaFiado.setSaldo(cuentaFiado.getSaldo().add(montoTotal));
            cuentaFiadoRepository.save(cuentaFiado);

            BigDecimal topeEfectivo = obtenerTopeEfectivo(cuentaFiado);
            if (cuentaFiado.getSaldo().compareTo(topeEfectivo) > 0) {
                excedeLimite = true;
                mensajeAdvertencia = "El cliente " + cliente.getNombre()
                        + " superó su límite de fiado (S/ " + topeEfectivo + "). Saldo actual: S/ "
                        + cuentaFiado.getSaldo();
            }
        }

        return new VentaResponse(ventaGuardada, excedeLimite, mensajeAdvertencia);
    }

    @Override
    @Transactional
    public Venta anular(Long id) {
        Venta venta = buscarId(id);

        if (venta.getEstado() == EstadoVenta.ANULADA) {
            throw new VentaInvalidaException("Esta venta ya está anulada");
        }

        // Devolver el stock de cada producto
        for (DetalleVenta detalle : venta.getItems()) {
            productoService.AumentarStock(detalle.getProducto().getId(), detalle.getCantidad());
        }

        // Sí era fiado, revertir el cargo de la cuenta del cliente
        if (venta.getMetodoPago() == MetodoPago.FIADO) {
            CuentaFiado cuentaFiado = cuentaFiadoRepository.findByCliente(venta.getCliente())
                    .orElseThrow(() -> new VentaInvalidaException(
                            "No se encontró la cuenta fiada del cliente para revertir el cargo"));
            cuentaFiado.setSaldo(cuentaFiado.getSaldo().subtract(venta.getMontoTotal()));
            cuentaFiadoRepository.save(cuentaFiado);
        }

        venta.setEstado(EstadoVenta.ANULADA);
        return ventaRepository.save(venta);
    }

    @Override
    public Venta buscarId(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new VentaNoEncontradaException("La venta con el id " + id + " no existe"));
    }

    @Override
    public List<Venta> buscarCliente(Long clienteId) {
        return ventaRepository.findByClienteId(clienteId);
    }

    @Override
    public List<Venta> listar() {
        return ventaRepository.findAll();
    }

    private CuentaFiado crearCuentaFiado(Cliente cliente) {
        CuentaFiado nueva = new CuentaFiado();
        nueva.setCliente(cliente);
        nueva.setSaldo(BigDecimal.ZERO);
        return cuentaFiadoRepository.save(nueva);
    }

    private BigDecimal obtenerTopeEfectivo(CuentaFiado cuentaFiado) {
        if (cuentaFiado.getTopeIndividual() != null) {
            return cuentaFiado.getTopeIndividual();
        }
        return configuracionFiadoService.obtener().getLimiteFiadoGlobal();
    }
}