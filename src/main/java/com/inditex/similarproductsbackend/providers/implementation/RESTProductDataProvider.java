package com.inditex.similarproductsbackend.providers.implementation;

import com.inditex.similarproductsbackend.dto.ProductDTO;
import com.inditex.similarproductsbackend.exception.ProductDataProviderException;
import com.inditex.similarproductsbackend.providers.contract.ProductDataProvider;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class RESTProductDataProvider implements ProductDataProvider {

    private final RestTemplate restClient;

    private final CircuitBreaker circuitBreaker;

    @Value("${repository.baseUrl}")
    @Setter
    private String baseUrl;

    public RESTProductDataProvider(RestTemplate restClient, CircuitBreaker circuitBreaker) {
        this.restClient = restClient;
        this.circuitBreaker = circuitBreaker;
    }

    public ProductDTO getProduct (String productId) throws ProductDataProviderException {
        String url = String.format("%s/%s", baseUrl, productId);
        try {
            ResponseEntity<ProductDTO> result = restClient.getForEntity(url, ProductDTO.class);
            return result.getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            return null;
        } catch (RestClientException ex) {
            logError("Error getting product from repository system", ex);
            throw new ProductDataProviderException("Exception getting product data from client", ex);
        }
    }

    @Override
    public ProductDTO getProductById(String productId) throws ProductDataProviderException {
        try {
            return circuitBreaker.executeCallable(() -> getProduct(productId));
        } catch (ProductDataProviderException e) {
            throw e;
        } catch (Exception ex) {
            logError("Exception getting product from repository system", ex);
            throw new ProductDataProviderException(ex.getMessage(), ex);
        }
    }

    public List<String> getSimilarIds(String productId) throws ProductDataProviderException {
        try {
            String url = String.format("%s/%s/%s", baseUrl, productId, "similarids");
            ResponseEntity<String[]> result = restClient.getForEntity(url, String[].class);
            return result.getBody() != null ? Arrays.asList(result.getBody()) : null;
        } catch (HttpClientErrorException.NotFound ex) {
            return null;
        } catch (RestClientException ex) {
            logError("Error getting similar product ids from repository system", ex);
            throw new ProductDataProviderException("Exception getting similar product ids from client", ex);
        }
    }

    @Override
    public List<String> getSimilarProductIds(String productId) throws ProductDataProviderException {
        try {
            return circuitBreaker.executeCallable(() -> getSimilarIds(productId));
        } catch (ProductDataProviderException e) {
            throw e;
        } catch (Exception ex) {
            logError("Exception getting product from repository system", ex);
            throw new ProductDataProviderException(ex.getMessage(), ex);
        }
    }

    private void logError(String message, Exception ex) {
        log.error("[RESTProductDataProvider] - {} ({})", message, ex.getMessage());
    }
}

