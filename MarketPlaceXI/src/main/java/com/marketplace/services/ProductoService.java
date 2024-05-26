package com.marketplace.services;

import com.marketplace.dto.ProductoDTO;
import com.marketplace.exceptions.CategoriaNotFoundException;
import com.marketplace.exceptions.MarcaNotFoundException;
import com.marketplace.exceptions.ProductoNotFoundException;
import com.marketplace.exceptions.SuccessfulOperationException;
import com.marketplace.models.Categoria;
import com.marketplace.models.Marca;
import com.marketplace.models.Producto;
import com.marketplace.repositories.CategoriaRepository;
import com.marketplace.repositories.MarcaRepository;
import com.marketplace.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<ProductoDTO> getAllProductos() {
        List<Producto> productos = productoRepository.findByEstado("Activo");
        return productos.stream().map(ProductoDTO::new).collect(Collectors.toList());
    }

    public Producto getProductoById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con ID: " + id));
    }

    public Producto createProducto(Producto producto) {
        Long marcaId = producto.getMarca().getId();
        Long categoriaId = producto.getCategoria().getId();
        Marca marca = marcaRepository.findById(marcaId)
                .orElseThrow(() -> new MarcaNotFoundException("Marca no encontrada con ID: " + marcaId));
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new CategoriaNotFoundException("Categoría no encontrada con ID: " + categoriaId));

        producto.setMarca(marca);
        producto.setCategoria(categoria);
        Producto nuevoProducto = productoRepository.save(producto);
        throw new SuccessfulOperationException("Producto creado exitosamente con ID: " + nuevoProducto.getId());
    }

    public Producto updateProducto(Long id, Producto producto) {
        Optional<Producto> productoOptional = productoRepository.findById(id);
        if (productoOptional.isPresent()) {
            Producto existingProducto = productoOptional.get();
            existingProducto.setNombre(producto.getNombre());
            existingProducto.setDescripcion(producto.getDescripcion());
            existingProducto.setPrecio(producto.getPrecio());
            existingProducto.setStockDisponible(producto.getStockDisponible());

            if (producto.getMarca() != null) {
                Long marcaId = producto.getMarca().getId();
                Marca marca = marcaRepository.findById(marcaId)
                        .orElseThrow(() -> new MarcaNotFoundException("Marca no encontrada con ID: " + marcaId));
                existingProducto.setMarca(marca);
            }
            if (producto.getCategoria() != null) {
                Long categoriaId = producto.getCategoria().getId();
                Categoria categoria = categoriaRepository.findById(categoriaId)
                        .orElseThrow(() -> new CategoriaNotFoundException("Categoría no encontrada con ID: " + categoriaId));
                existingProducto.setCategoria(categoria);
            }

            productoRepository.save(existingProducto);
            throw new SuccessfulOperationException("Producto actualizado exitosamente con ID: " + existingProducto.getId());
        } else {
            throw new ProductoNotFoundException("Producto no encontrado con ID: " + id);
        }
    }

    public void deleteProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con ID: " + id));
        if (producto.getEstado().equals("Inactivo")) {
            throw new ProductoNotFoundException("El producto con ID " + id + " ya está eliminado.");
        }
        producto.setEstado("Inactivo");
        productoRepository.save(producto);
        throw new SuccessfulOperationException("Producto eliminado con ID: " + id);
    }

    public List<Producto> getProductosEliminados() {
        return productoRepository.findByEstado("Inactivo");
    }

    public Producto recoverProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con ID: " + id));
        if (!producto.getEstado().equals("Inactivo")) {
            throw new IllegalArgumentException("El producto con ID " + id + " no está eliminado.");
        }
        producto.setEstado("Activo");
        Producto productoRecuperado = productoRepository.save(producto);
        throw new SuccessfulOperationException("Producto recuperado exitosamente con ID: " + productoRecuperado.getId());
    }
}
