package com.marketplace.controllers;

import com.marketplace.dto.ProductoDTO;
import com.marketplace.exceptions.CategoriaNotFoundException;
import com.marketplace.exceptions.MarcaNotFoundException;
import com.marketplace.exceptions.ProductoNotFoundException;
import com.marketplace.exceptions.SuccessfulOperationException;
import com.marketplace.models.Producto;
import com.marketplace.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAllProductos() { 
        List<ProductoDTO> productoDTOs = productoService.getAllProductos();
        return ResponseEntity.ok(productoDTOs); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        Producto producto = productoService.getProductoById(id);
        return ResponseEntity.ok(producto); 
    }

    @PostMapping
    public ResponseEntity<String> createProducto(@RequestBody Producto producto) {
        try {
            productoService.createProducto(producto);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el producto"); // Nunca se ejecutará
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.status(HttpStatus.CREATED).body(e.getMessage());
        } catch (MarcaNotFoundException | CategoriaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProducto(@PathVariable Long id, @RequestBody Producto producto) {
        try {
            productoService.updateProducto(id, producto);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el producto"); // Nunca se ejecutará
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (ProductoNotFoundException | MarcaNotFoundException | CategoriaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable Long id) {
        try {
            productoService.deleteProducto(id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el producto"); // Nunca se ejecutará
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (ProductoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/eliminados")
    public ResponseEntity<List<Producto>> getProductosEliminados() {
        List<Producto> productosEliminados = productoService.getProductosEliminados();
        return ResponseEntity.ok(productosEliminados);
    }

    @PutMapping("/recuperar/{id}")
    public ResponseEntity<String> recoverProducto(@PathVariable Long id) {
        try {
            productoService.recoverProducto(id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al recuperar el producto"); // Nunca se ejecutará
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (ProductoNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
