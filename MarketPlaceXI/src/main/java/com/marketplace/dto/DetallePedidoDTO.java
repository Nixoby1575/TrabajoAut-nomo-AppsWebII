package com.marketplace.dto;

import com.marketplace.models.DetallePedido;

public class DetallePedidoDTO {
    private Long id;
    private Long productoId;
    private Long pedidoId;
    private int cantidad;
    private double precioUnitario; // Fetched from Producto
    private String nombreProducto;

    // Constructors

    public DetallePedidoDTO() {}

    public DetallePedidoDTO(DetallePedido detallePedido) {
        this.id = detallePedido.getId();
        this.productoId = detallePedido.getProducto().getId();
        this.pedidoId = detallePedido.getPedido().getId();
        this.cantidad = detallePedido.getCantidad();
        this.precioUnitario = detallePedido.getProducto().getPrecio(); 
        this.nombreProducto = detallePedido.getProducto().getNombre();
    }

    // Getters

    public Long getId() {
        return id;
    }

    public Long getProductoId() {
        return productoId;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    // Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
}
