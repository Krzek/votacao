package com.krzek.votacao.error;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

public class ErrorResponse {

    private final int statusCode;

    private final List<ApiError> errors;

    public ErrorResponse(int statusCode, List<ApiError> errors) {
        this.statusCode = statusCode;
        this.errors = errors;
    }

    static ErrorResponse of(HttpStatus status, List<ApiError> errors) {
        return new ErrorResponse(status.value(), errors);
    }

    static ErrorResponse of(HttpStatus status, ApiError error) {
        return new ErrorResponse(status.value(), Collections.singletonList(error));
    }

    static class ApiError{
        private final String code;
        private final String message;

        public ApiError(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
