package com.wassupshoppingmall.domain.cart.controller;

import com.wassupshoppingmall.domain.cart.dto.CartResponse;
import com.wassupshoppingmall.domain.cart.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartResponse>> getCartItems(HttpSession session) {
        return ResponseEntity.ok(cartService.getCartItems(session));
    }

    @PostMapping
}
