package com.krzek.votacao.service.exception;

import org.springframework.http.HttpStatus;

public class SessaoTimeOutException extends BusinessException {

    public SessaoTimeOutException() {
        super("sessao-7", HttpStatus.REQUEST_TIMEOUT);
    }
}
