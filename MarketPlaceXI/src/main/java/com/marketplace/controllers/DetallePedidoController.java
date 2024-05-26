package com.marketplace.controllers;

import com.marketplace.dto.DetallePedidoDTO;
import com.marketplace.exceptions.DetallePedidoNotFoundException;
import com.marketplace.exceptions.ProductoNotFoundException;
import com.marketplace.exceptions.SuccessfulOperationException;
import com.marketplace.models.DetallePedido;
import com.marketplace.services.DetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalles-pedido")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @GetMapping
    public ResponseEntity<List<DetallePedidoDTO>> getAllDetallesPedido() {
        List<DetallePedidoDTO> detallePedidoDTOs = detallePedidoService.getAllDetallesPedido();
        return ResponseEntity.ok(detallePedidoDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoDTO> getDetallePedidoById(@PathVariable Long id) {
        DetallePedidoDTO detallePedidoDTO = detallePedidoService.getDetallePedidoById(id);
        return ResponseEntity.ok(detallePedidoDTO);
    }

    @PostMapping
    public ResponseEntity<String> createDetallePedido(@RequestBody DetallePedido detallePedido) {
        try {
            detallePedidoService.createDetallePedido(detallePedido);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el detalle de pedido"); // Nunca se ejecutará
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.status(HttpStatus.CREATED).body(e.getMessage());
        } catch (ProductoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDetallePedido(@PathVariable Long id, @RequestBody DetallePedido detallePedido) {
        try {
            detallePedidoService.updateDetallePedido(id, detallePedido);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el detalle de pedido"); // Nunca se ejecutará
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (DetallePedidoNotFoundException | ProductoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDetallePedido(@PathVariable Long id) {
        try {
            detallePedidoService.deleteDetallePedido(id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el detalle de pedido"); // Nunca se ejecutará
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (DetallePedidoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/eliminados")
    public ResponseEntity<List<DetallePedido>> getDetallesPedidoEliminados() {
        List<DetallePedido> detallesPedidoEliminados = detallePedidoService.getDetallesPedidoEliminados();
        return ResponseEntity.ok(detallesPedidoEliminados);
    }

    @PutMapping("/recuperar/{id}")
    public ResponseEntity<String> recoverDetallePedido(@PathVariable Long id) {
        try {
            detallePedidoService.recoverDetallePedido(id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al recuperar el detalle de pedido"); // Nunca se ejecutará
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (DetallePedidoNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
