package com.codurance.guru.infra.web.responses;

import org.springframework.http.ResponseEntity;

import static org.springframework.http.ResponseEntity.badRequest;

public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
