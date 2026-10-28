package com.jhonaiker.hotel_reservas.reservas;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

@Service
@Profile("oracle")
public class ReservaPlsqlService {

    private final SimpleJdbcCall fnCalcularTotal;
    private final SimpleJdbcCall spRecalcularTotales;

    public ReservaPlsqlService(JdbcTemplate jdbcTemplate) {
        this.fnCalcularTotal = new SimpleJdbcCall(jdbcTemplate)
                .withFunctionName("fn_calcular_total");
        this.spRecalcularTotales = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_recalcular_totales");
    }

    // Llama a la función PL/SQL fn_calcular_total
    public BigDecimal calcularTotal(Long habitacionId, LocalDate entrada, LocalDate salida) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("p_id_habitacion", habitacionId)
                .addValue("p_entrada", Date.valueOf(entrada))
                .addValue("p_salida", Date.valueOf(salida));

        return fnCalcularTotal.executeFunction(BigDecimal.class, params);
    }

    // Llama al procedure PL/SQL sp_recalcular_totales (parámetro IN y OUT)
    @Transactional
    public int recalcularTotales(String estado) {
        Map<String, Object> resultado = spRecalcularTotales.execute(
                new MapSqlParameterSource().addValue("p_estado", estado.toUpperCase()));

        return ((Number) resultado.get("p_filas")).intValue();
    }
}