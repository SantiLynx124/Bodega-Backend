package com.huaman.bodega.Repository;

import com.huaman.bodega.Entity.ConfiguracionFiado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracionFiadoRepository extends JpaRepository<ConfiguracionFiado, Long> {

}