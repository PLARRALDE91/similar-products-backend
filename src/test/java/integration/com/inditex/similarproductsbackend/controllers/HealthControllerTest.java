package integration.com.inditex.similarproductsbackend.controllers;

import com.inditex.similarproductsbackend.controllers.HealthController;
import com.inditex.similarproductsbackend.services.implementation.BasicHealthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class HealthControllerTest {

    @Mock
    private BasicHealthService healthService;

    @InjectMocks
    private HealthController healthController;

    @Test
    public void testHealthCheckOKResult() {
        Mockito.when(healthService.systemIsHealthy()).thenReturn(true);
        ResponseEntity<?> response = healthController.healthCheck();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testHealthCheckNotOKResult() {
        Mockito.when(healthService.systemIsHealthy()).thenReturn(false);
        ResponseEntity<?> response = healthController.healthCheck();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
