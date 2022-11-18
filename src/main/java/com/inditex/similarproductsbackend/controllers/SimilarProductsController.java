package com.inditex.similarproductsbackend.controllers;

import com.google.common.collect.Lists;
import com.inditex.similarproductsbackend.dto.ProductDTO;
import com.inditex.similarproductsbackend.exception.ProductNotFoundException;
import com.inditex.similarproductsbackend.exception.ServiceException;
import com.inditex.similarproductsbackend.services.contract.ProductsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/product")
public class SimilarProductsController extends BaseController {

    private final ProductsService productsService;

    public SimilarProductsController(ProductsService productsService){
        this.productsService = productsService;
    }

    @GetMapping("/{productId}/similar")
    public ResponseEntity<?> getSimilarProductsData(@PathVariable String productId) {
        try {
            List<ProductDTO> products = productsService.getSimilarProducts(productId);
            return buildOKResponse(products);
        } catch (ServiceException e) {
            log.error("[SimilarProductsController] - Error getting similar products (Id: {}): {}", productId, e.getMessage());
            return buildOKResponseWithErrorHeader(Lists.newArrayList());
        }
    }
}
