package com.krzek.votacao.service.exception;

import org.springframework.http.HttpStatus;

public class SessaoNotFoundException extends BusinessException {

    public SessaoNotFoundException() {
        super("sessao-6", HttpStatus.NOT_FOUND);
    }
}
