package com.marketplace.services;

import com.marketplace.exceptions.ClienteNotFoundException;
import com.marketplace.exceptions.SuccessfulOperationException;
import com.marketplace.models.Cliente;
import com.marketplace.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getAllClientes() {
        return clienteRepository.findByEstado("Activo");
    }

    public Cliente getClienteById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + id));
    }

    public Cliente createCliente(Cliente cliente) {
        Cliente nuevoCliente = clienteRepository.save(cliente);
        throw new SuccessfulOperationException("Cliente creado exitosamente con ID: " + nuevoCliente.getId());
    }

    public Cliente updateCliente(Long id, Cliente cliente) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente existingCliente = clienteOptional.get();

            existingCliente.setNombre(cliente.getNombre());
            existingCliente.setApellido(cliente.getApellido());
            existingCliente.setCorreoElectronico(cliente.getCorreoElectronico());
            existingCliente.setDireccion(cliente.getDireccion());
            existingCliente.setCiudad(cliente.getCiudad());
            existingCliente.setPais(cliente.getPais());
            existingCliente.setTelefono(cliente.getTelefono());

            clienteRepository.save(existingCliente);
            throw new SuccessfulOperationException("Cliente actualizado exitosamente con ID: " + existingCliente.getId());
        } else {
            throw new ClienteNotFoundException("Cliente no encontrado con ID: " + id);
        }
    }




    public void deleteCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + id));

        if (cliente.getEstado().equals("Inactivo")) {
            throw new ClienteNotFoundException("El cliente con ID " + id + " ya está dado de baja");
        }

        cliente.setEstado("Inactivo");
        clienteRepository.save(cliente);
        throw new SuccessfulOperationException("Cliente eliminado correctamente con ID: " + id);
    }


    public List<Cliente> getClientesEliminados() {
        return clienteRepository.findByEstado("Inactivo");
    }

    public Cliente recoverCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + id));
        if (!cliente.getEstado().equals("Inactivo")) {
            throw new IllegalArgumentException("El cliente con ID " + id + " no está eliminado.");
        }
        cliente.setEstado("Activo");
        Cliente clienteRecuperado = clienteRepository.save(cliente);
        throw new SuccessfulOperationException("Cliente recuperado exitosamente con ID: " + clienteRecuperado.getId());
    }
}
