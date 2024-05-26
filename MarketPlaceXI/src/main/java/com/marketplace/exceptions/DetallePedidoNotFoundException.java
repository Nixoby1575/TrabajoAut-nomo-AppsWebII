package com.marketplace.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DetallePedidoNotFoundException extends EntityNotFoundException {
    public DetallePedidoNotFoundException(String message) {
        super(message);
    }
}
