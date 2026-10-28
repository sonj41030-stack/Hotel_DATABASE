package com.jhonaiker.hotel_reservas.reservas;

import com.jhonaiker.hotel_reservas.clientes.Cliente;
import com.jhonaiker.hotel_reservas.habitaciones.Habitacion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "RESERVAS")
@Getter
@Setter
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESERVA")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_CLIENTE", nullable = false)
    private Cliente cliente;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_HABITACION", nullable = false)
    private Habitacion habitacion;

    @NotNull
    @Column(name = "FECHA_ENTRADA", nullable = false)
    private LocalDate fechaEntrada;

    @NotNull
    @Column(name = "FECHA_SALIDA", nullable = false)
    private LocalDate fechaSalida;

    // PENDIENTE, CONFIRMADA, CANCELADA
    @Column(name = "ESTADO", nullable = false, length = 20)
    private String estado = "PENDIENTE";

    @Column(name = "TOTAL", precision = 12, scale = 2)
    private BigDecimal total;
}