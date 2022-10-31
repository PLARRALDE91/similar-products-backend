package com.inditex.similarproductsbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDTO {
    private String id;
    private String name;
    private Number price;
    private Boolean availability;
}
