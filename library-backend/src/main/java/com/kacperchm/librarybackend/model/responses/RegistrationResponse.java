package com.kacperchm.librarybackend.model.responses;

import org.springframework.http.HttpStatus;

public class RegistrationResponse {
    private String message;
    private HttpStatus status;

    public RegistrationResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
