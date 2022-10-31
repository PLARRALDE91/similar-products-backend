package com.inditex.similarproductsbackend.controllers;

import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected ResponseEntity<?> buildOKResponse(Object body) {
        return ResponseEntity.ok(body);
    }

    protected ResponseEntity<?> buildInternalErrorResponse(Object body) {
        return ResponseEntity.internalServerError().body(body);
    }
}
