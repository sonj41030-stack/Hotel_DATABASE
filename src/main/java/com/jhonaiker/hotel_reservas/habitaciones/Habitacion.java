package com.jhonaiker.hotel_reservas.habitaciones;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "HABITACIONES")
@Getter
@Setter
@NoArgsConstructor
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HABITACION")
    private Long id;

    @NotBlank
    @Column(name = "NUMERO", nullable = false, unique = true, length = 10)
    private String numero;

    @NotBlank
    @Column(name = "TIPO", nullable = false, length = 30)
    private String tipo;

    @NotNull
    @Positive
    @Column(name = "PRECIO_NOCHE", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioNoche;

    // DISPONIBLE, OCUPADA, MANTENCION
    @Column(name = "ESTADO", nullable = false, length = 20)
    private String estado = "DISPONIBLE";
}
