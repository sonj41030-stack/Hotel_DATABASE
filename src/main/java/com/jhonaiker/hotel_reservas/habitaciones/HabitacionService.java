package com.jhonaiker.hotel_reservas.habitaciones;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;

    @Transactional(readOnly = true)
    public List<Habitacion> listar(String estado) {
        if (estado != null && !estado.isBlank()) {
            return habitacionRepository.findByEstado(estado.toUpperCase());
        }
        return habitacionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Habitacion buscarPorId(Long id) {
        return habitacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Habitación no encontrada"));
    }

    @Transactional
    public Habitacion crear(Habitacion habitacion) {
        if (habitacionRepository.existsByNumero(habitacion.getNumero())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Ya existe una habitación con ese número");
        }
        return habitacionRepository.save(habitacion);
    }

    @Transactional
    public Habitacion actualizar(Long id, Habitacion datos) {
        Habitacion existente = buscarPorId(id);
        existente.setTipo(datos.getTipo());
        existente.setPrecioNoche(datos.getPrecioNoche());
        existente.setEstado(datos.getEstado());
        return habitacionRepository.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        habitacionRepository.delete(buscarPorId(id));
    }
}
