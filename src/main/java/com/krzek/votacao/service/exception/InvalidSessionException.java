package com.krzek.votacao.service.exception;

import org.springframework.http.HttpStatus;

public class InvalidSessionException extends BusinessException {

    public InvalidSessionException() {
        super("sessao-8", HttpStatus.BAD_REQUEST);
    }
}
