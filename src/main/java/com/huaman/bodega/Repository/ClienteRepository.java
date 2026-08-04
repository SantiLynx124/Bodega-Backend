package com.huaman.bodega.Repository;

import com.huaman.bodega.Entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByEstadoTrue();
    List<Cliente> findByNombreContainingIgnoreCaseAndEstadoTrue(String nombres);
    List<Cliente> findByNombreContainingIgnoreCase(String nombres);
    Boolean existsByNombreAndTelefono(String nombres, String telefono);
}
