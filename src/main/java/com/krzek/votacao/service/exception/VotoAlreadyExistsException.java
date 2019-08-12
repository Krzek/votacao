package com.krzek.votacao.service.exception;

import org.springframework.http.HttpStatus;

public class VotoAlreadyExistsException extends BusinessException {

    public VotoAlreadyExistsException() {
        super("voto-7", HttpStatus.ALREADY_REPORTED);
    }
}
