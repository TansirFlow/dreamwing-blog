package com.dreamwing.exception;

import org.springframework.http.HttpStatus;

public class VerifyCodeException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public VerifyCodeException(String message) {
        this.status = HttpStatus.BAD_GATEWAY;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
