package com.inditex.similarproductsbackend.providers.contract;

import com.inditex.similarproductsbackend.dto.ProductDTO;
import com.inditex.similarproductsbackend.exception.ProductDataProviderException;

import java.util.List;

public interface ProductDataProvider {
    ProductDTO getProductById(String productId) throws ProductDataProviderException;
    List<String> getSimilarProductIds(String productId) throws ProductDataProviderException;
}
