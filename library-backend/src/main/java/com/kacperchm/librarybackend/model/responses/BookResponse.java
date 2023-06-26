package com.kacperchm.librarybackend.model.responses;

import org.springframework.http.HttpStatus;

public class BookResponse {
    private String message;
    private HttpStatus status;

    public BookResponse(String message, HttpStatus status) {
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
