package com.marketplace.services;

import com.marketplace.exceptions.CategoriaNotFoundException;
import com.marketplace.exceptions.SuccessfulOperationException;
import com.marketplace.models.Categoria;
import com.marketplace.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findByEstado("Activo");
    }

    public Categoria getCategoriaById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("Categoría no encontrada con ID: " + id));
    }

    public Categoria createCategoria(Categoria categoria) {
        Categoria nuevaCategoria = categoriaRepository.save(categoria);
        throw new SuccessfulOperationException("Categoría creada exitosamente con ID: " + nuevaCategoria.getId());
    }

    public Categoria updateCategoria(Long id, Categoria categoria) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
        if (categoriaOptional.isPresent()) {
            Categoria existingCategoria = categoriaOptional.get();
            existingCategoria.setNombre(categoria.getNombre());
            existingCategoria.setDescripcion(categoria.getDescripcion());
            categoriaRepository.save(existingCategoria);
            throw new SuccessfulOperationException("Categoría actualizada exitosamente con ID: " + existingCategoria.getId());
        } else {
            throw new CategoriaNotFoundException("Categoría no encontrada con ID: " + id);
        }
    }

    public void deleteCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("Categoría no encontrada con ID: " + id));
        if (categoria.getEstado().equals("Inactivo")) {
            throw new CategoriaNotFoundException("La categoría con ID " + id + " ya está eliminada.");
        }
        categoria.setEstado("Inactivo");
        categoriaRepository.save(categoria);
        throw new SuccessfulOperationException("Categoría eliminada con ID: " + id);
    }

    public List<Categoria> getCategoriasEliminadas() {
        return categoriaRepository.findByEstado("Inactivo");
    }

    public Categoria recoverCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("Categoría no encontrada con ID: " + id));
        if (!categoria.getEstado().equals("Inactivo")) {
            throw new IllegalArgumentException("La categoría con ID " + id + " no está eliminada.");
        }
        categoria.setEstado("Activo");
        Categoria categoriaRecuperada = categoriaRepository.save(categoria);
        throw new SuccessfulOperationException("Categoría recuperada exitosamente con ID: " + categoriaRecuperada.getId());
    }
}
