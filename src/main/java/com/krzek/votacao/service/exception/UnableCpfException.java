package com.krzek.votacao.service.exception;

import org.springframework.http.HttpStatus;

public class UnableCpfException extends BusinessException {

    public UnableCpfException() {
        super("validacao-cpf-unable", HttpStatus.UNAUTHORIZED);
    }
}
