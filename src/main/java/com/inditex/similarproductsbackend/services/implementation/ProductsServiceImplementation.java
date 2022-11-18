package com.inditex.similarproductsbackend.services.implementation;

import com.google.common.collect.Lists;
import com.inditex.similarproductsbackend.dto.ProductDTO;
import com.inditex.similarproductsbackend.exception.ProductDataProviderException;
import com.inditex.similarproductsbackend.exception.ServiceException;
import com.inditex.similarproductsbackend.providers.contract.ProductDataProvider;
import com.inditex.similarproductsbackend.services.contract.ProductsService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImplementation implements ProductsService {

    private final ProductDataProvider productDataProvider;

    public ProductsServiceImplementation(ProductDataProvider productDataProvider) {
        this.productDataProvider = productDataProvider;
    }

    @Override
    public ProductDTO getProductById(String productId) throws ServiceException {
        try {
            return productDataProvider.getProductById(productId);
        } catch (ProductDataProviderException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Cacheable("products")
    public List<ProductDTO> getSimilarProducts(String productId) throws ServiceException {
        try {
            List<String> similarProductIds = productDataProvider.getSimilarProductIds(productId);
            if(similarProductIds == null) return Lists.newArrayList();
            List<ProductDTO> enrichedList = similarProductIds.stream()
                    .map(id -> {
                        try {
                            return getProductById(id);
                        } catch (ServiceException e) {
                            return null;
                        }
                    }).collect(Collectors.toList());
            enrichedList.removeAll(Collections.singleton(null));
            return enrichedList;
        } catch (ProductDataProviderException e) {
            throw new ServiceException(e);
        }
    }
}
