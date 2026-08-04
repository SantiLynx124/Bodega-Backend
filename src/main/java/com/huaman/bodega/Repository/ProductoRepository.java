package com.huaman.bodega.Repository;

import com.huaman.bodega.Entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByEstadoTrueAndNombreContainingIgnoreCaseOrEstadoTrueAndMarcaContainingIgnoreCase(String nombre, String marca);
    List<Producto> findByEstadoTrue();
    List<Producto> findByEstadoFalse();
    boolean existsByNombreAndMarca(String nombre, String marca);
}
