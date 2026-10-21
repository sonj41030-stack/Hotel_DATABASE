package com.jhonaiker.hotel_reservas.reservas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("""
            select count(r) > 0 from Reserva r
            where r.habitacion.id = :habitacionId
              and r.estado <> 'CANCELADA'
              and r.fechaEntrada < :salida
              and r.fechaSalida > :entrada
            """)
    boolean existeSolape(@Param("habitacionId") Long habitacionId,
                         @Param("entrada") LocalDate entrada,
                         @Param("salida") LocalDate salida);
}
