package com.jhonaiker.hotel_reservas.clientes;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cliente no encontrado"));
    }

    @Transactional
    public Cliente crear(Cliente cliente) {
        if (clienteRepository.existsByRut(cliente.getRut())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Ya existe un cliente con ese RUT");
        }
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente actualizar(Long id, Cliente datos) {
        Cliente existente = buscarPorId(id);
        existente.setNombre(datos.getNombre());
        existente.setEmail(datos.getEmail());
        existente.setTelefono(datos.getTelefono());
        return clienteRepository.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        clienteRepository.delete(buscarPorId(id));
    }
}