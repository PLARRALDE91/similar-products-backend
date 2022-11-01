package com.inditex.similarproductsbackend.context;

import com.inditex.similarproductsbackend.annotation.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
@Generated
public class ClientConfig {

    @Autowired
    private HttpMessageConverter jacksonConverter;

    @Bean
    public RestTemplate restTemplate() {
        log.info("Creating rest template ");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, jacksonConverter);
        return restTemplate;
    }
}