package com.marketplace.controllers;

import com.marketplace.exceptions.CategoriaNotFoundException;
import com.marketplace.exceptions.SuccessfulOperationException;
import com.marketplace.models.Categoria;
import com.marketplace.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> getAllCategorias() {
        List<Categoria> categorias = categoriaService.getAllCategorias();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long id) {
        Categoria categoria = categoriaService.getCategoriaById(id);
        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<String> createCategoria(@RequestBody Categoria categoria) {
        try {
            categoriaService.createCategoria(categoria);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la categoría"); // Nunca se ejecutará
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.status(HttpStatus.CREATED).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        try {
            categoriaService.updateCategoria(id, categoria);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la categoría"); // Nunca se ejecutará
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (CategoriaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoria(@PathVariable Long id) {
        try {
            categoriaService.deleteCategoria(id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la categoría"); 
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (CategoriaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/eliminadas")
    public ResponseEntity<List<Categoria>> getCategoriasEliminadas() {
        List<Categoria> categoriasEliminadas = categoriaService.getCategoriasEliminadas();
        return ResponseEntity.ok(categoriasEliminadas);
    }

    @PutMapping("/recuperar/{id}")
    public ResponseEntity<String> recoverCategoria(@PathVariable Long id) {
        try {
            categoriaService.recoverCategoria(id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al recuperar la categoría"); 
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (CategoriaNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
