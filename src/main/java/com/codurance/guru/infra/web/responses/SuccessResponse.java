package com.codurance.guru.infra.web.responses;

import feign.Response;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.ResponseEntity.noContent;

public class SuccessResponse {

    public static ResponseEntity<Void> successResponse() {
        return noContent().build();
    }

}
