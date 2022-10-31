package com.inditex.similarproductsbackend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected ResponseEntity<?> buildOKResponse(Object body) {
        return ResponseEntity.ok(body);
    }

    protected ResponseEntity<?> buildInternalErrorResponse(Object body) {
        return ResponseEntity.internalServerError().body(body);
    }

    protected ResponseEntity<?> buildNotFoundResponse(Object body) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
