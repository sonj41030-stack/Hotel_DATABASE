package com.jhonaiker.hotel_reservas.reservas;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReservaResponse(
        Long id,
        Long clienteId,
        String clienteNombre,
        Long habitacionId,
        String habitacionNumero,
        LocalDate fechaEntrada,
        LocalDate fechaSalida,
        String estado,
        BigDecimal total
) {
    public static ReservaResponse de(Reserva r) {
        return new ReservaResponse(
                r.getId(),
                r.getCliente().getId(),
                r.getCliente().getNombre(),
                r.getHabitacion().getId(),
                r.getHabitacion().getNumero(),
                r.getFechaEntrada(),
                r.getFechaSalida(),
                r.getEstado(),
                r.getTotal());
    }
}
