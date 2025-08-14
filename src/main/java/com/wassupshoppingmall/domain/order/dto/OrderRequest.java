package com.wassupshoppingmall.domain.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequest {

    private String recipient;
    private String phone;
    private String address;
    private List<OrderProduct> products;

    @Getter
    public static class OrderProduct{
        private Long productId;
        private int quantity;
    }
}
