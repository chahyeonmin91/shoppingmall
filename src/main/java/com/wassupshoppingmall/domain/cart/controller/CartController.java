package com.wassupshoppingmall.domain.cart.controller;

import com.wassupshoppingmall.domain.cart.dto.CartRequest;
import com.wassupshoppingmall.domain.cart.dto.CartResponse;
import com.wassupshoppingmall.domain.cart.service.CartService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostConstruct
    public void init() {
        System.out.println("✅ CartController 로딩 완료!");
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> getCartItems() {
        return ResponseEntity.ok(cartService.getCartItems());
    }

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestBody CartRequest request) {
        cartService.addToCart(request);
        return ResponseEntity.ok("장바구니에 담았습니다");
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<String> updateCartItem(@PathVariable Long cartId, @RequestParam int quantity) {
        cartService.updateCartItem(cartId, quantity);
        return ResponseEntity.ok("장바구니를 수정했습니다");
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long cartId) {
        cartService.deleteCartItem(cartId);
        return ResponseEntity.ok("장바구니에서 삭제했습니다");
    }
}
