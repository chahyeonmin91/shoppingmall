package com.wassupshoppingmall.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartResponse {
    private Long cartId;
    private Long productId;
    private String productName;
    private int quantity;
    private int price;

}
