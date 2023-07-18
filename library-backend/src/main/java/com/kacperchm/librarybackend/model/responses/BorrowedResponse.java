package com.kacperchm.librarybackend.model.responses;

import org.springframework.http.HttpStatus;

import java.util.List;

public class BorrowedResponse {
    private List<String> message;
    private HttpStatus status;

    public BorrowedResponse(List<String> message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public List<String> getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
