package com.jhonaiker.hotel_reservas.habitaciones;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    Optional<Habitacion> findByNumero(String numero);

    boolean existsByNumero(String numero);

    List<Habitacion> findByEstado(String estado);
}
