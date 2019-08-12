package com.krzek.votacao.service.exception;

import org.springframework.http.HttpStatus;

public class InvalidCpfException extends BusinessException {

    public InvalidCpfException() {
        super("validacao-cpf", HttpStatus.BAD_REQUEST);
    }
}
