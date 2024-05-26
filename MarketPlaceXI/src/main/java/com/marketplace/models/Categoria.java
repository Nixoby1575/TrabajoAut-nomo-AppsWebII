package com.marketplace.models;

import jakarta.persistence.*;

@Entity
@Table
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "nombre", nullable = false)
    private String nombre;
	
	@Column(name = "descripcion", nullable = false)
    private String descripcion;
    
    @Column(name = "estado", columnDefinition = "varchar(255) default 'Activo' NOT NULL")
    private String estado;

    public Categoria() {}
    public Categoria(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;

    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    @Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", estado=" + estado
				+ "]";
	}


}
