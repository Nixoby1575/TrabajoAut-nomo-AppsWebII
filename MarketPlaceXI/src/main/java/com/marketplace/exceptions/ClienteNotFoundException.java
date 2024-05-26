package com.marketplace.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class ClienteNotFoundException extends EntityNotFoundException {
    public ClienteNotFoundException(String message) {
        super(message);
    }
}
