package com.marketplace.controllers;

import com.marketplace.dto.PedidoDTO;
import com.marketplace.exceptions.ClienteNotFoundException;
import com.marketplace.exceptions.PedidoNotFoundException;
import com.marketplace.exceptions.SuccessfulOperationException;
import com.marketplace.models.Pedido;
import com.marketplace.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getAllPedidos() {
        List<Pedido> pedidos = pedidoService.getAllPedidos();
        List<PedidoDTO> pedidoDTOs = pedidos.stream()
                .map(PedidoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pedidoDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getPedidoById(@PathVariable Long id) {
        PedidoDTO pedidoDTO = pedidoService.getPedidoById(id);
        return ResponseEntity.ok(pedidoDTO);
    }

    @PostMapping
    public ResponseEntity<String> createPedido(@RequestBody Pedido pedido) {
        try {
            pedidoService.createPedido(pedido);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el pedido"); 
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.status(HttpStatus.CREATED).body(e.getMessage());
        } catch (ClienteNotFoundException e) { 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        try {
            pedidoService.updatePedido(id, pedido);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el pedido"); 
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (PedidoNotFoundException | ClienteNotFoundException e) { 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePedido(@PathVariable Long id) {
        try {
            pedidoService.deletePedido(id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el pedido"); 
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (PedidoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/eliminados")
    public ResponseEntity<List<Pedido>> getPedidosEliminados() {
        List<Pedido> pedidosEliminados = pedidoService.getPedidosEliminados();
        return ResponseEntity.ok(pedidosEliminados);
    }

    @PutMapping("/recuperar/{id}")
    public ResponseEntity<String> recoverPedido(@PathVariable Long id) {
        try {
            pedidoService.recoverPedido(id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al recuperar el pedido"); 
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (PedidoNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
