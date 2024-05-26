package com.marketplace.controllers;

import com.marketplace.exceptions.MarcaNotFoundException;
import com.marketplace.exceptions.SuccessfulOperationException;
import com.marketplace.models.Marca;
import com.marketplace.services.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public ResponseEntity<List<Marca>> getAllMarcas() {
        List<Marca> marcas = marcaService.getAllMarcas();
        return ResponseEntity.ok(marcas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> getMarcaById(@PathVariable Long id) {
        Marca marca = marcaService.getMarcaById(id);
        return ResponseEntity.ok(marca);
    }

    @PostMapping
    public ResponseEntity<String> createMarca(@RequestBody Marca marca) {
        try {
            marcaService.createMarca(marca);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la marca");
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.status(HttpStatus.CREATED).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMarca(@PathVariable Long id, @RequestBody Marca marca) {
        try {
            marcaService.updateMarca(id, marca);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la marca"); 
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (MarcaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMarca(@PathVariable Long id) {
        try {
            marcaService.deleteMarca(id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la marca"); 
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (MarcaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/eliminadas")
    public ResponseEntity<List<Marca>> getMarcasEliminadas() {
        List<Marca> marcasEliminadas = marcaService.getMarcasEliminadas();
        return ResponseEntity.ok(marcasEliminadas);
    }

    @PutMapping("/recuperar/{id}")
    public ResponseEntity<String> recoverMarca(@PathVariable Long id) {
        try {
            marcaService.recoverMarca(id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al recuperar la marca"); 
        } catch (SuccessfulOperationException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (MarcaNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
