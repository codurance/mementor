package com.codurance.guru.infra.web.responses;

import org.springframework.http.ResponseEntity;

import static org.springframework.http.ResponseEntity.*;

public class Responses {

    public static ResponseEntity<ErrorResponse> errorResponse(String message) {
        return badRequest().body(new ErrorResponse(message));
    }

    public static <T> ResponseEntity<T> notFound() {
        return ResponseEntity.notFound().build();
    }

    public static <T> ResponseEntity<T> successResponse() {
        return noContent().build();
    }

    public static <T> ResponseEntity<T> successResponse(T body) {
        return ok(body);
    }
}
