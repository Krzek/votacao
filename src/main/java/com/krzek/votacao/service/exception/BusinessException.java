package com.krzek.votacao.service.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {

    private final String code;
    private final HttpStatus status;

    public BusinessException(String code, HttpStatus status) {
        this.code = code;
        this.status = status;
    }

    public BusinessException(String message, String code, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public BusinessException(String message, Throwable cause, String code, HttpStatus status) {
        super(message, cause);
        this.code = code;
        this.status = status;
    }

    public BusinessException(Throwable cause, String code, HttpStatus status) {
        super(cause);
        this.code = code;
        this.status = status;
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
