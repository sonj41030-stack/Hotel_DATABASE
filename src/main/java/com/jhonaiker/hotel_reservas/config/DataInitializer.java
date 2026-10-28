package com.jhonaiker.hotel_reservas.config;

import com.jhonaiker.hotel_reservas.clientes.Cliente;
import com.jhonaiker.hotel_reservas.clientes.ClienteRepository;
import com.jhonaiker.hotel_reservas.habitaciones.Habitacion;
import com.jhonaiker.hotel_reservas.habitaciones.HabitacionRepository;
import com.jhonaiker.hotel_reservas.reservas.Reserva;
import com.jhonaiker.hotel_reservas.reservas.ReservaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(ReservaRepository reservaRepository,
                                   ClienteRepository clienteRepository,
                                   HabitacionRepository habitacionRepository) {
        return args -> {
            // Si ya hay datos, no volver a insertar los de prueba
            if (reservaRepository.count() > 0
                    || clienteRepository.count() > 0
                    || habitacionRepository.count() > 0) {
                System.out.println("Datos de prueba ya existentes, se omite la carga inicial.");
                return;
            }

            // 1. Crear y guardar un cliente de prueba
            Cliente cliente = new Cliente();
            cliente.setRut("17.482.915-3");
            cliente.setNombre("Andrea Miranda");
            cliente.setEmail("andrea.miranda@gmail.com");
            cliente.setTelefono("+56991234500");
            clienteRepository.save(cliente);

            // 2. Crear y guardar una habitación de prueba
            Habitacion habitacion = new Habitacion();
            habitacion.setNumero("244");
            habitacion.setTipo("MATRIMONIAL");
            habitacion.setPrecioNoche(new BigDecimal("55000.00"));
            habitacion.setEstado("DISPONIBLE");
            habitacionRepository.save(habitacion);

            // 3. Crear la reserva vinculando el cliente y la habitación
            Reserva reserva = new Reserva();
            reserva.setCliente(cliente);
            reserva.setHabitacion(habitacion);
            reserva.setFechaEntrada(LocalDate.of(2026, 11, 27));
            reserva.setFechaSalida(LocalDate.of(2026, 11, 30));
            reserva.setTotal(new BigDecimal("165000.00"));
            reserva.setEstado("CONFIRMADA");

            // 4. Guardar la reserva en la base de datos
            reservaRepository.save(reserva);

            System.out.println("==================================================");
            System.out.println(" ¡RESERVA CREADA Y GUARDADA EN LA BASE DE DATOS! ");
            System.out.println("==================================================");
        };
    }
}