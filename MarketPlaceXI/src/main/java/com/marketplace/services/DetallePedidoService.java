package com.marketplace.services;

import com.marketplace.dto.DetallePedidoDTO;
import com.marketplace.exceptions.DetallePedidoNotFoundException;
import com.marketplace.exceptions.ProductoNotFoundException;
import com.marketplace.exceptions.SuccessfulOperationException;
import com.marketplace.models.DetallePedido;
import com.marketplace.models.Producto;
import com.marketplace.repositories.DetallePedidoRepository;
import com.marketplace.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    @Autowired
    private ProductoRepository productoRepository;

    public List<DetallePedidoDTO> getAllDetallesPedido() {
        List<DetallePedido> detallesPedido = detallePedidoRepository.findByEstado("Activo");
        return detallesPedido.stream().map(DetallePedidoDTO::new).collect(Collectors.toList());
    }

    public DetallePedidoDTO getDetallePedidoById(Long id) {
        DetallePedido detallePedido = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new DetallePedidoNotFoundException("Detalle de pedido no encontrado con ID: " + id));
        return new DetallePedidoDTO(detallePedido);
    }

    public DetallePedido createDetallePedido(DetallePedido detallePedido) {

        Long productoId = detallePedido.getProducto().getId();
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con ID: " + productoId));


        detallePedido.setProducto(producto);

        DetallePedido nuevoDetallePedido = detallePedidoRepository.save(detallePedido);
        throw new SuccessfulOperationException("Detalle de pedido creado exitosamente con ID: " + nuevoDetallePedido.getId());
    }

    public DetallePedido updateDetallePedido(Long id, DetallePedido detallePedido) {
        DetallePedido existingDetallePedido = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new DetallePedidoNotFoundException("Detalle de pedido no encontrado con ID: " + id));


        if (detallePedido.getProducto() != null) {
            Long productoId = detallePedido.getProducto().getId();
            Producto producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con ID: " + productoId));
            existingDetallePedido.setProducto(producto);
        }

        existingDetallePedido.setCantidad(detallePedido.getCantidad());

        detallePedidoRepository.save(existingDetallePedido);
        throw new SuccessfulOperationException("Detalle de pedido actualizado exitosamente con ID: " + existingDetallePedido.getId());
    }

    public void deleteDetallePedido(Long id) {
        DetallePedido detallePedido = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new DetallePedidoNotFoundException("Detalle de pedido no encontrado con ID: " + id));
        if (detallePedido.getEstado().equals("Inactivo")) {
            throw new DetallePedidoNotFoundException("El detalle de pedido con ID " + id + " ya está eliminado.");
        }
        detallePedido.setEstado("Inactivo");
        detallePedidoRepository.save(detallePedido);
        throw new SuccessfulOperationException("Detalle de pedido eliminado (estado cambiado a Inactivo) con ID: " + id);
    }

    public List<DetallePedido> getDetallesPedidoEliminados() {
        return detallePedidoRepository.findByEstado("Inactivo");
    }

    public DetallePedido recoverDetallePedido(Long id) {
        DetallePedido detallePedido = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new DetallePedidoNotFoundException("Detalle de pedido no encontrado con ID: " + id));
        if (!detallePedido.getEstado().equals("Inactivo")) {
            throw new IllegalArgumentException("El detalle de pedido con ID " + id + " no está eliminado.");
        }
        detallePedido.setEstado("Activo");
        DetallePedido detallePedidoRecuperado = detallePedidoRepository.save(detallePedido);
        throw new SuccessfulOperationException("Detalle de pedido recuperado exitosamente con ID: " + detallePedidoRecuperado.getId());
    }
}
