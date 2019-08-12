package com.krzek.votacao.service.exception;

import org.springframework.http.HttpStatus;

public class VotacaoNotFoundException extends BusinessException {

    public VotacaoNotFoundException() {
        super("votacao-1", HttpStatus.NOT_FOUND);
    }
}
