package com.krzek.votacao.service.exception;

import org.springframework.http.HttpStatus;

public class PautaNotFoundException extends BusinessException {

    public PautaNotFoundException() {
        super("pauta-6", HttpStatus.NOT_FOUND);
    }
}
