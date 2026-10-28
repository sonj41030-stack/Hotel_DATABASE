package com.jhonaiker.hotel_reservas.reservas;
import com.jhonaiker.hotel_reservas.clientes.Cliente;
import com.jhonaiker.hotel_reservas.clientes.ClienteService;
import com.jhonaiker.hotel_reservas.habitaciones.Habitacion;
import com.jhonaiker.hotel_reservas.habitaciones.HabitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ClienteService clienteService;
    private final HabitacionService habitacionService;

    @Transactional(readOnly = true)
    public List<ReservaResponse> listar() {
        return reservaRepository.findAll().stream()
                .map(ReservaResponse::de)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReservaResponse buscarPorId(Long id) {
        return ReservaResponse.de(obtener(id));
    }

    @Transactional
    public ReservaResponse crear(ReservaRequest req) {
        if (!req.fechaSalida().isAfter(req.fechaEntrada())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "La fecha de salida debe ser posterior a la de entrada");
        }

        Cliente cliente = clienteService.buscarPorId(req.clienteId());
        Habitacion habitacion = habitacionService.buscarPorId(req.habitacionId());

        if ("MANTENCION".equals(habitacion.getEstado())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "La habitación está en mantención");
        }

        if (reservaRepository.existeSolape(habitacion.getId(), req.fechaEntrada(), req.fechaSalida())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "La habitación ya está reservada en esas fechas");
        }

        long noches = ChronoUnit.DAYS.between(req.fechaEntrada(), req.fechaSalida());

        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setHabitacion(habitacion);
        reserva.setFechaEntrada(req.fechaEntrada());
        reserva.setFechaSalida(req.fechaSalida());
        reserva.setEstado("CONFIRMADA");
        reserva.setTotal(habitacion.getPrecioNoche().multiply(BigDecimal.valueOf(noches)));

        return ReservaResponse.de(reservaRepository.save(reserva));
    }

    @Transactional
    public ReservaResponse cancelar(Long id) {
        Reserva reserva = obtener(id);
        if ("CANCELADA".equals(reserva.getEstado())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "La reserva ya está cancelada");
        }
        reserva.setEstado("CANCELADA");
        return ReservaResponse.de(reservaRepository.save(reserva));
    }

    private Reserva obtener(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Reserva no encontrada"));
    }
}
