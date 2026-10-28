package com.jhonaiker.hotel_reservas.reservas;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "HISTORIAL_RESERVAS")
@Getter
@Setter
@NoArgsConstructor
public class HistorialReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HISTORIAL")
    private Long id;

    @Column(name = "ID_RESERVA", nullable = false)
    private Long idReserva;

    @Column(name = "ACCION", nullable = false, length = 20)
    private String accion;

    @Column(name = "ESTADO_ANTES", length = 20)
    private String estadoAntes;

    @Column(name = "ESTADO_DESPUES", length = 20)
    private String estadoDespues;

    @Column(name = "USUARIO_BD", length = 50)
    private String usuarioBd;

    @Column(name = "FECHA_REGISTRO", nullable = false)
    private LocalDateTime fechaRegistro;
}
