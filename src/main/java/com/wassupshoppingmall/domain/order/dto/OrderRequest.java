package com.wassupshoppingmall.domain.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequest {

    @NotBlank
    private String recipient;
    @NotBlank
    private String phone;
    @NotBlank
    private String address;

    private List<OrderProduct> products;

    @Getter
    public static class OrderProduct{
        private Long productId;
        @Min(1)
        private int quantity;
    }
}
