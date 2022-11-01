package com.inditex.similarproductsbackend.dto;

import com.inditex.similarproductsbackend.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class ProductDTO {
    private String id;
    private String name;
    private Number price;
    private Boolean availability;
}
