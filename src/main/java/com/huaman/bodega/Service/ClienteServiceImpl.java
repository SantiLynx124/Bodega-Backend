package com.huaman.bodega.Service;

import com.huaman.bodega.Entity.Cliente;
import com.huaman.bodega.Entity.CuentaFiado;
import com.huaman.bodega.Exception.ClienteNoEncontradoException;
import com.huaman.bodega.Exception.ClienteYaExisteException;
import com.huaman.bodega.Exception.VentaInvalidaException;
import com.huaman.bodega.Repository.ClienteRepository;
import com.huaman.bodega.Repository.CuentaFiadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final CuentaFiadoRepository cuentaFiadoRepository;

    @Override
    public Cliente registrar(Cliente cliente) {
        if (cliente.getTelefono() != null
                && clienteRepository.existsByNombreAndTelefono(cliente.getNombre(), cliente.getTelefono())) {
            throw new ClienteYaExisteException("Ya existe un cliente registrado con ese nombre y teléfono");
        }
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente actualizar(Cliente cliente) {
        Cliente clienteFinal = buscarId(cliente.getId());
        clienteFinal.setNombre(cliente.getNombre());
        clienteFinal.setTelefono(cliente.getTelefono());
        clienteFinal.setDescripcion(cliente.getDescripcion());
        return clienteRepository.save(clienteFinal);
    }

    @Override
    public List<Cliente> listar() {
        return clienteRepository.findByEstadoTrue();
    }

    @Override
    public Cliente buscarId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNoEncontradoException("El cliente con el id " + id + " no existe"));
    }

    @Override
    public List<Cliente> buscarNombre(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCaseAndEstadoTrue(nombre);
    }

    @Override
    public Cliente activar(Long id) {
        Cliente cliente = buscarId(id);
        cliente.setEstado(true);
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente desactivar(Long id) {
        Cliente cliente = buscarId(id);
        cliente.setEstado(false);
        return clienteRepository.save(cliente);
    }

    @Override
    public CuentaFiado verCuentaFiado(Long clienteId) {
        Cliente cliente = buscarId(clienteId);
        return obtenerOCrearCuentaFiado(cliente);
    }

    @Override
    public CuentaFiado actualizarTope(Long clienteId, BigDecimal tope) {
        CuentaFiado cuentaFiado = verCuentaFiado(clienteId);
        cuentaFiado.setTopeIndividual(tope); // null = vuelve a usar el límite global
        return cuentaFiadoRepository.save(cuentaFiado);
    }

    @Override
    public CuentaFiado activarFiado(Long clienteId) {
        CuentaFiado cuentaFiado = verCuentaFiado(clienteId);
        cuentaFiado.setFiadoHabilitado(true);
        return cuentaFiadoRepository.save(cuentaFiado);
    }

    @Override
    public CuentaFiado desactivarFiado(Long clienteId) {
        CuentaFiado cuentaFiado = verCuentaFiado(clienteId);
        cuentaFiado.setFiadoHabilitado(false);
        return cuentaFiadoRepository.save(cuentaFiado);
    }

    @Override
    public CuentaFiado registrarAbono(Long clienteId, BigDecimal monto) {
        CuentaFiado cuentaFiado = verCuentaFiado(clienteId);
        if (monto.compareTo(cuentaFiado.getSaldo()) > 0) {
            throw new VentaInvalidaException(
                    "El abono (S/ " + monto + ") no puede ser mayor a la deuda actual (S/ " + cuentaFiado.getSaldo() + ")");
        }
        cuentaFiado.setSaldo(cuentaFiado.getSaldo().subtract(monto));
        return cuentaFiadoRepository.save(cuentaFiado);
    }

    private CuentaFiado obtenerOCrearCuentaFiado(Cliente cliente) {
        return cuentaFiadoRepository.findByCliente(cliente)
                .orElseGet(() -> {
                    CuentaFiado nueva = new CuentaFiado();
                    nueva.setCliente(cliente);
                    nueva.setSaldo(BigDecimal.ZERO);
                    return cuentaFiadoRepository.save(nueva);
                });
    }
}