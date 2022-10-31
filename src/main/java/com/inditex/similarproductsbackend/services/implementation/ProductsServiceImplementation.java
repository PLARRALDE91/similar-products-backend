package com.inditex.similarproductsbackend.services.implementation;

import com.inditex.similarproductsbackend.dto.ProductDTO;
import com.inditex.similarproductsbackend.exception.ProductNotFoundException;
import com.inditex.similarproductsbackend.providers.contract.ProductDataProvider;
import com.inditex.similarproductsbackend.services.contract.ProductsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsServiceImplementation implements ProductsService {

    private ProductDataProvider productDataProvider;

    public ProductsServiceImplementation(ProductDataProvider productDataProvider) {
        this.productDataProvider = productDataProvider;
    }

    @Override
    public ProductDTO getProductById(String productId) {
        return productDataProvider.getProductById(productId);
    }

    @Override
    public List<ProductDTO> getSimilarProducts(String productId) throws ProductNotFoundException {
        if(getProductById(productId) == null) {
            throw new ProductNotFoundException();
        }
        return productDataProvider.getSimilarProducts(productId);
    }
}
