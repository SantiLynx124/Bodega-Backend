package com.huaman.bodega.Service;

import com.huaman.bodega.Entity.ConfiguracionFiado;
import com.huaman.bodega.Repository.ConfiguracionFiadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ConfiguracionFiadoServiceImpl implements ConfiguracionFiadoService {

    private static final Long ID_UNICO = 1L;
    private final ConfiguracionFiadoRepository configuracionFiadoRepository;

    @Override
    public ConfiguracionFiado obtener() {
        // Si no existe la fila todavía (primera vez que se usa el sistema), la crea con valores por defecto
        return configuracionFiadoRepository.findById(ID_UNICO)
                .orElseGet(() -> {
                    ConfiguracionFiado nueva = new ConfiguracionFiado();
                    nueva.setId(ID_UNICO);
                    return configuracionFiadoRepository.save(nueva);
                });
    }

    @Override
    public ConfiguracionFiado actualizar(BigDecimal limiteFiadoGlobal, boolean fiadoHabilitadoGlobal) {
        ConfiguracionFiado configuracion = obtener();
        configuracion.setLimiteFiadoGlobal(limiteFiadoGlobal);
        configuracion.setFiadoHabilitadoGlobal(fiadoHabilitadoGlobal);
        return configuracionFiadoRepository.save(configuracion);
    }
}