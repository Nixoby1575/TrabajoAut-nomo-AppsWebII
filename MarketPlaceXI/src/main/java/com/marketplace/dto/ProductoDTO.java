package com.marketplace.dto;

import com.marketplace.models.Producto;

public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stockDisponible;
    private String nombreMarca; 
    private String nombreCategoria; 


    public ProductoDTO(Producto producto) {
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.descripcion = producto.getDescripcion();
        this.precio = producto.getPrecio();
        this.stockDisponible = producto.getStockDisponible();
        this.nombreMarca = producto.getMarca().getNombre();
        this.nombreCategoria = producto.getCategoria().getNombre();
    }


    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }

    public String getNombreMarca() {
        return nombreMarca;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }


}
