package com.dreamwing.exception;

import org.springframework.http.HttpStatus;

public class DreamWingRuntimeException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public DreamWingRuntimeException(String message) {
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
