package com.marketplace.controllers;

import com.marketplace.exceptions.ClienteNotFoundException;
import com.marketplace.exceptions.SuccessfulOperationException;
import com.marketplace.models.Cliente;
import com.marketplace.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteService.getAllClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Cliente cliente = clienteService.getClienteById(id);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<String> createCliente(@RequestBody Cliente cliente) {
        try {
            clienteService.createCliente(cliente);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el cliente"); // Nunca se ejecutará
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.status(HttpStatus.CREATED).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        try {
            clienteService.updateCliente(id, cliente);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el cliente"); // Nunca se ejecutará
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id) {
        try {
            clienteService.deleteCliente(id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el cliente");
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/eliminados")
    public ResponseEntity<List<Cliente>> getClientesEliminados() {
        List<Cliente> clientesEliminados = clienteService.getClientesEliminados();
        return ResponseEntity.ok(clientesEliminados);
    }

    @PutMapping("/recuperar/{id}")
    public ResponseEntity<String> recoverCliente(@PathVariable Long id) {
        try {
            clienteService.recoverCliente(id); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al recuperar el cliente"); // Nunca se ejecutará
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (ClienteNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
