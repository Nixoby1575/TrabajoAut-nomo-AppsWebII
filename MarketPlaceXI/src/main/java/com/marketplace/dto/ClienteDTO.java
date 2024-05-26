package com.marketplace.dto;

import com.marketplace.models.Cliente;

public class ClienteDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String correoElectronico;

  
    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nombre = cliente.getNombre();
        this.apellido = cliente.getApellido();
        this.correoElectronico = cliente.getCorreoElectronico();
    }

  
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

}
