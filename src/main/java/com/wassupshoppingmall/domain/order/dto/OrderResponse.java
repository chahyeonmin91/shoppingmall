package com.wassupshoppingmall.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long orderId;
    private String recipient;
    private String phone;
    private String address;
    private int totalPrice;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;

    public static class OrderItemResponse{
        private String productName;
        private int quantity;
        private int price;
    }

}
