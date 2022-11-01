package com.inditex.similarproductsbackend.services.implementation;

import com.inditex.similarproductsbackend.dto.ProductDTO;
import com.inditex.similarproductsbackend.exception.ProductDataProviderException;
import com.inditex.similarproductsbackend.exception.ProductNotFoundException;
import com.inditex.similarproductsbackend.exception.ServiceException;
import com.inditex.similarproductsbackend.providers.contract.ProductDataProvider;
import com.inditex.similarproductsbackend.services.contract.ProductsService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImplementation implements ProductsService {

    private final ProductDataProvider productDataProvider;

    public ProductsServiceImplementation(ProductDataProvider productDataProvider) {
        this.productDataProvider = productDataProvider;
    }

    @Override
    @Cacheable("products")
    public ProductDTO getProductById(String productId) throws ServiceException {
        try {
            return productDataProvider.getProductById(productId);
        } catch (ProductDataProviderException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductDTO> getSimilarProducts(String productId) throws ProductNotFoundException, ServiceException {
        try {
            if(getProductById(productId) == null) {
                throw new ProductNotFoundException();
            }
            List<String> similarProductIds = productDataProvider.getSimilarProductIds(productId);
            return similarProductIds.stream()
                    .map(id -> {
                        try {
                            return getProductById(id);
                        } catch (ServiceException e) {
                            return null;
                        }
                    }).collect(Collectors.toList());
        } catch (ProductDataProviderException | ServiceException e) {
            throw new ServiceException(e);
        }
    }
}
