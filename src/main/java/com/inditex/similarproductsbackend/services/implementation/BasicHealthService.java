package com.inditex.similarproductsbackend.services.implementation;

import com.inditex.similarproductsbackend.services.contract.HealthService;
import org.springframework.stereotype.Service;

@Service
public class BasicHealthService implements HealthService {

    @Override
    public boolean systemIsHealthy() {
        //In the future we could check system dependencies to say if it's healthy or not.
        return true;
    }
}
