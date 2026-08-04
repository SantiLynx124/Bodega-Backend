package com.huaman.bodega.Repository;

import com.huaman.bodega.Entity.Cliente;
import com.huaman.bodega.Entity.CuentaFiado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuentaFiadoRepository extends JpaRepository<CuentaFiado, Long> {
    Optional<CuentaFiado> findByCliente(Cliente cliente);
    boolean existsByCliente(Cliente cliente);
}
