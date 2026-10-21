package com.jhonaiker.hotel_reservas.reservas;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@Profile("oracle")
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaPlsqlController {

    private final ReservaPlsqlService plsqlService;

    // GET /api/reservas/calcular-total?habitacionId=1&entrada=2026-11-01&salida=2026-11-04
    @GetMapping("/calcular-total")
    public Map<String, Object> calcularTotal(
            @RequestParam Long habitacionId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entrada,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate salida) {

        return Map.of("total", plsqlService.calcularTotal(habitacionId, entrada, salida));
    }

    // POST /api/reservas/recalcular-totales?estado=CONFIRMADA
    @PostMapping("/recalcular-totales")
    public Map<String, Object> recalcularTotales(@RequestParam String estado) {
        return Map.of("filasActualizadas", plsqlService.recalcularTotales(estado));
    }
}
