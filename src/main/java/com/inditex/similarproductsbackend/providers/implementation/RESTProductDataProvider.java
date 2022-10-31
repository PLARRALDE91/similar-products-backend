package com.inditex.similarproductsbackend.providers.implementation;

import com.inditex.similarproductsbackend.dto.ProductDTO;
import com.inditex.similarproductsbackend.providers.contract.ProductDataProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RESTProductDataProvider implements ProductDataProvider {

    @Override
    public ProductDTO getProductById(String productId) {
        return null;
    }

    @Override
    public List<ProductDTO> getSimilarProducts(String productId) {
        return null;
    }
}
