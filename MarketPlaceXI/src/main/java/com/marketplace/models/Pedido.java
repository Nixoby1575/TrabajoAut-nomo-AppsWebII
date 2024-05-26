package com.marketplace.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_pedido", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPedido;

    @Column(name = "estado", columnDefinition = "varchar(255) default 'Activo' NOT NULL")
    private String estado;

    @ManyToOne(fetch = FetchType.EAGER) 
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;


    public Pedido() {}


    public Pedido(Date fechaPedido, Cliente cliente) {
        this.fechaPedido = fechaPedido;
        this.cliente = cliente;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Pedido [id=" + id + ", fechaPedido=" + fechaPedido + ", estado=" + estado + ", cliente=" + cliente + "]";
    }
}
