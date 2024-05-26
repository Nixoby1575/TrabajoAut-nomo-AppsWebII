package com.marketplace.models;

import jakarta.persistence.*;

@Entity
@Table
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "pais_origen", nullable = false)
    private String paisOrigen;

    @Column(name = "estado", columnDefinition = "varchar(255) default 'Activo' NOT NULL")
    private String estado;

    public Marca() {}

    public Marca(String nombre, String paisOrigen) {
        this.nombre = nombre;
        this.paisOrigen = paisOrigen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
	public String toString() {
		return "Marca [id=" + id + ", nombre=" + nombre + ", paisOrigen=" + paisOrigen + ", estado=" + estado + "]";
	}
}
