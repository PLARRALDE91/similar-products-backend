package integration.com.inditex.similarproductsbackend.services;

import com.inditex.similarproductsbackend.services.implementation.BasicHealthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class HealthServiceTest {

    @InjectMocks
    private BasicHealthService basicHealthService;

    @Test
    public void testHealthCheck() {
        assertTrue(basicHealthService.systemIsHealthy());
    }
}
