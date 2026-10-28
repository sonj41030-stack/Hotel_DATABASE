package com.jhonaiker.hotel_reservas.habitaciones;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
@RequiredArgsConstructor
public class HabitacionController {

    private final HabitacionService habitacionService;

    @GetMapping
    public List<Habitacion> listar(@RequestParam(required = false) String estado) {
        return habitacionService.listar(estado);
    }

    @GetMapping("/{id}")
    public Habitacion buscar(@PathVariable Long id) {
        return habitacionService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Habitacion crear(@Valid @RequestBody Habitacion habitacion) {
        return habitacionService.crear(habitacion);
    }

    @PutMapping("/{id}")
    public Habitacion actualizar(@PathVariable Long id, @Valid @RequestBody Habitacion habitacion) {
        return habitacionService.actualizar(id, habitacion);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        habitacionService.eliminar(id);
    }
}
