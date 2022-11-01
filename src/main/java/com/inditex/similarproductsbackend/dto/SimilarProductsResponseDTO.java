package com.inditex.similarproductsbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimilarProductsResponseDTO {

    @JsonProperty("description")
    private String description;

    @JsonProperty("content")
    private List<ProductDTO> products;
}
