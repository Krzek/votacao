package com.krzek.votacao.service.exception;

import org.springframework.http.HttpStatus;

public class VotoNotFoundException extends BusinessException {

    public VotoNotFoundException() {
        super("voto-6", HttpStatus.NOT_FOUND);
    }
}
