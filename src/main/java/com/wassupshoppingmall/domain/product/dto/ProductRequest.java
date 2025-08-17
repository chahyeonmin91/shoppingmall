package com.wassupshoppingmall.domain.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    @NotBlank @Size(max = 100)
    private String name;
    @Min(0)
    private int price;
    @Size(max = 1000)
    private String description;
    @Min(0)
    private int stock;
}
