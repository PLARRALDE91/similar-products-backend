package com.inditex.similarproductsbackend.providers.contract;

import com.inditex.similarproductsbackend.dto.ProductDTO;

import java.util.List;

public interface ProductDataProvider {
    ProductDTO getProductById(String productId);
    List<ProductDTO> getSimilarProducts(String productId);
}
