package com.inditex.similarproductsbackend.controllers;

import com.inditex.similarproductsbackend.dto.MessageResponseDTO;
import com.inditex.similarproductsbackend.services.contract.HealthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController extends BaseController {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        ResponseEntity<?> response;
        if(healthService.systemIsHealthy()) {
            response = buildOKResponse(new MessageResponseDTO("System is up and running!"));
        } else {
            response = buildInternalErrorResponse(new MessageResponseDTO("System health is not OK"));
        }
        return response;
    }
}
