package com.marketplace.dto;

import java.util.Date;

import com.marketplace.models.Pedido;

public class PedidoDTO {
    private Long id;
    private Date fechaPedido;
    private String estado;
    private ClienteDTO cliente;

 
    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.fechaPedido = pedido.getFechaPedido();
        this.estado = pedido.getEstado();
        this.cliente = new ClienteDTO(pedido.getCliente());
    }

   
    public Long getId() {
        return id;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public String getEstado() {
        return estado;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }


}
