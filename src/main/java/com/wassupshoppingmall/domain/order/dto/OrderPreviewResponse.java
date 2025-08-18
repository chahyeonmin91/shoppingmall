package com.wassupshoppingmall.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class OrderPreviewResponse {
    private int totalPrice;
    private List<OrderResponse.OrderItemResponse> items;
}
