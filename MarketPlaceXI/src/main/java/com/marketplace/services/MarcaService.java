package com.marketplace.services;

import com.marketplace.exceptions.MarcaNotFoundException;
import com.marketplace.exceptions.SuccessfulOperationException;
import com.marketplace.models.Marca;
import com.marketplace.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    public List<Marca> getAllMarcas() {
        return marcaRepository.findByEstado("Activo");
    }

    public Marca getMarcaById(Long id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new MarcaNotFoundException("Marca no encontrada con ID: " + id));
    }

    public Marca createMarca(Marca marca) {
        Marca nuevaMarca = marcaRepository.save(marca);
        throw new SuccessfulOperationException("Marca creada exitosamente con ID: " + nuevaMarca.getId());
    }

    public Marca updateMarca(Long id, Marca marca) {
        Optional<Marca> marcaOptional = marcaRepository.findById(id);
        if (marcaOptional.isPresent()) {
            Marca existingMarca = marcaOptional.get();
            existingMarca.setNombre(marca.getNombre());
            existingMarca.setPaisOrigen(marca.getPaisOrigen());
            marcaRepository.save(existingMarca);
            throw new SuccessfulOperationException("Marca actualizada exitosamente con ID: " + existingMarca.getId());
        } else {
            throw new MarcaNotFoundException("Marca no encontrada con ID: " + id);
        }
    }

    public void deleteMarca(Long id) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new MarcaNotFoundException("Marca no encontrada con ID: " + id));
        if (marca.getEstado().equals("Inactivo")) {
            throw new MarcaNotFoundException("La marca con ID " + id + " ya está eliminada.");
        }
        marca.setEstado("Inactivo");
        marcaRepository.save(marca);
        throw new SuccessfulOperationException("Marca eliminada con ID: " + id);
    }

    public List<Marca> getMarcasEliminadas() {
        return marcaRepository.findByEstado("Inactivo");
    }

    public Marca recoverMarca(Long id) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new MarcaNotFoundException("Marca no encontrada con ID: " + id));
        if (!marca.getEstado().equals("Inactivo")) {
            throw new IllegalArgumentException("La marca con ID " + id + " no está eliminada.");
        }
        marca.setEstado("Activo");
        Marca marcaRecuperada = marcaRepository.save(marca);
        throw new SuccessfulOperationException("Marca recuperada exitosamente con ID: " + marcaRecuperada.getId());
    }
}
