package com.inditex.similarproductsbackend.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected ResponseEntity<?> buildOKResponse(Object body) {
        return ResponseEntity.ok(body);
    }

    protected ResponseEntity<?> buildOKResponseWithErrorHeader(Object body) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("error", "true");
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(body);
    }

    protected ResponseEntity<?> buildInternalErrorResponse(Object body) {
        return ResponseEntity.internalServerError().body(body);
    }
}
