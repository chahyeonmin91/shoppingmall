package com.wassupshoppingmall.domain.order.controller;

import com.wassupshoppingmall.domain.order.dto.OrderRequest;
import com.wassupshoppingmall.domain.order.dto.OrderResponse;
import com.wassupshoppingmall.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createorder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getMyOrders() {
        return ResponseEntity.ok(orderService.getMyOrders());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getMyOrderDetail(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getMyOrderDetail(orderId));
    }
}
