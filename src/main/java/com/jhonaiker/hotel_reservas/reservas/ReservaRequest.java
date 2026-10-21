package com.jhonaiker.hotel_reservas.reservas;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservaRequest(
        @NotNull Long clienteId,
        @NotNull Long habitacionId,
        @NotNull LocalDate fechaEntrada,
        @NotNull LocalDate fechaSalida
) {
}