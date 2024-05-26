package com.marketplace.services;

import com.marketplace.dto.PedidoDTO;
import com.marketplace.exceptions.ClienteNotFoundException;
import com.marketplace.exceptions.PedidoNotFoundException;
import com.marketplace.exceptions.SuccessfulOperationException;
import com.marketplace.models.Cliente;
import com.marketplace.models.Pedido;
import com.marketplace.repositories.ClienteRepository;
import com.marketplace.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findByEstado("Activo");
    }

    public PedidoDTO getPedidoById(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado con ID: " + id));
        return new PedidoDTO(pedido);
    }

    public Pedido createPedido(Pedido pedido) {
        Long clienteId = pedido.getCliente().getId();
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + clienteId));

        pedido.setCliente(cliente);
        Pedido nuevoPedido = pedidoRepository.save(pedido);
        throw new SuccessfulOperationException("Pedido creado exitosamente con ID: " + nuevoPedido.getId());
    }

    public Pedido updatePedido(Long id, Pedido pedido) {
        Pedido existingPedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado con ID: " + id));

        if (pedido.getCliente() != null) {
            Long clienteId = pedido.getCliente().getId();
            Cliente cliente = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + clienteId));
            existingPedido.setCliente(cliente);
        }

        existingPedido.setFechaPedido(pedido.getFechaPedido());

        pedidoRepository.save(existingPedido);
        throw new SuccessfulOperationException("Pedido actualizado exitosamente con ID: " + existingPedido.getId());
    }

    public void deletePedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado con ID: " + id));
        if (pedido.getEstado().equals("Inactivo")) {
            throw new PedidoNotFoundException("El pedido con ID " + id + " ya está eliminado.");
        }
        pedido.setEstado("Inactivo");
        pedidoRepository.save(pedido);
        throw new SuccessfulOperationException("Pedido eliminado con ID: " + id);
    }

    public List<Pedido> getPedidosEliminados() {
        return pedidoRepository.findByEstado("Inactivo");
    }

    public Pedido recoverPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado con ID: " + id));
        if (!pedido.getEstado().equals("Inactivo")) {
            throw new IllegalArgumentException("El pedido con ID " + id + " no está eliminado.");
        }
        pedido.setEstado("Activo");
        Pedido pedidoRecuperado = pedidoRepository.save(pedido);
        throw new SuccessfulOperationException("Pedido recuperado exitosamente con ID: " + pedidoRecuperado.getId());
    }
}
