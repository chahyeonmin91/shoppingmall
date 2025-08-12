package com.wassupshoppingmall.domain.order.service;

import com.wassupshoppingmall.domain.order.dto.OrderRequest;
import com.wassupshoppingmall.domain.order.dto.OrderResponse;
import com.wassupshoppingmall.domain.order.entity.Order;
import com.wassupshoppingmall.domain.order.repository.OrderRepository;
import com.wassupshoppingmall.domain.product.repository.ProductRepository;
import com.wassupshoppingmall.domain.user.entity.User;
import com.wassupshoppingmall.global.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AuthService authService;

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        User user = authService.getLoginUser();

        Order order = Order.
    }


}
