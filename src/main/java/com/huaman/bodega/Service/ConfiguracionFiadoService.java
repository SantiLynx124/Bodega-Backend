package com.huaman.bodega.Service;

import com.huaman.bodega.Entity.ConfiguracionFiado;

import java.math.BigDecimal;

public interface ConfiguracionFiadoService {
    ConfiguracionFiado obtener();
    ConfiguracionFiado actualizar(BigDecimal limitePredeterminado, boolean fiadoHabilitadoGlobal);
}