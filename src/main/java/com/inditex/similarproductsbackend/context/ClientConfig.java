package com.inditex.similarproductsbackend.context;

import com.inditex.similarproductsbackend.annotation.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
@Generated
public class ClientConfig {

    @Autowired
    private HttpMessageConverter jacksonConverter;

    @Value("${client.connectTimeout}")
    private int connectTimeout;

    @Value("${client.readTimeout}")
    private int readTimeout;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(getRestTemplateFactory());
        restTemplate.getMessageConverters().add(0, jacksonConverter);
        return restTemplate;
    }

    private SimpleClientHttpRequestFactory getRestTemplateFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);
        return factory;
    }
}