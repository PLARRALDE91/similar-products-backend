package com.inditex.similarproductsbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inditex.similarproductsbackend.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class SimilarProductsResponseDTO {

    @JsonProperty("description")
    private String description;

    @JsonProperty("content")
    private List<ProductDTO> products;
}
