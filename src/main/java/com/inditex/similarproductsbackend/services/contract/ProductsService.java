package com.inditex.similarproductsbackend.services.contract;

import com.inditex.similarproductsbackend.dto.ProductDTO;
import com.inditex.similarproductsbackend.exception.ProductNotFoundException;

import java.util.List;

public interface ProductsService {
    ProductDTO getProductById(String productId);
    List<ProductDTO> getSimilarProducts(String productId) throws ProductNotFoundException;
}
