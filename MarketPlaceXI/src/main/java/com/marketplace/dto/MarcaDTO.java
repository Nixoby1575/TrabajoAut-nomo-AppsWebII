package com.marketplace.dto;

import com.marketplace.models.Marca;

public class MarcaDTO {
    private Long id;
    private String nombre;

   
    public MarcaDTO(Marca marca) {
        this.id = marca.getId();
        this.nombre = marca.getNombre();
    }

  
}