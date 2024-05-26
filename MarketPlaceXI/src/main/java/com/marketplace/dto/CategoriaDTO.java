package com.marketplace.dto;

import com.marketplace.models.Categoria;

public class CategoriaDTO {
    private Long id;
    private String nombre;

    
    public CategoriaDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.nombre = categoria.getNombre();
    }

    
}