package com.wassupshoppingmall.domain.order.service;

import com.wassupshoppingmall.domain.order.controller.OrderController;
import com.wassupshoppingmall.domain.order.dto.OrderRequest;
import com.wassupshoppingmall.domain.order.dto.OrderResponse;
import com.wassupshoppingmall.domain.order.entity.Order;
import com.wassupshoppingmall.domain.order.entity.OrderItem;
import com.wassupshoppingmall.domain.order.repository.OrderRepository;
import com.wassupshoppingmall.domain.product.entity.Product;
import com.wassupshoppingmall.domain.product.repository.ProductRepository;
import com.wassupshoppingmall.domain.user.entity.User;
import com.wassupshoppingmall.global.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AuthService authService;

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        User user = authService.getLoginUser();

        Order order = Order.builder()
                .user(user)
                .recipient(request.getRecipient())
                .phone(request.getPhone())
                .address(request.getAddress())
                .totalPrice(0)
                .build();

        int total = 0;

        for (OrderRequest.OrderProduct op : request.getProducts()) {
            Product product = productRepository.findById(op.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다"));
            int price = product.getPrice();
            total += price * op.getQuantity();

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(op.getQuantity())
                    .price(price)
                    .build();

            order.addOrderItem(orderItem);
        }
        order = orderRepository.save(order);
        order = order.toBuilder().totalPrice(total).build();

        return new OrderResponse(
                order.getId(),
                order.getRecipient(),
                order.getPhone(),
                order.getAddress(),
                total,
                order.getCreatedAt(),
                order.getOrderItems().stream()
                        .map(oi -> new OrderResponse.OrderItemResponse(
                                oi.getProduct().getName(),
                                oi.getQuantity(),
                                oi.getPrice()
                        ))
                        .collect(Collectors.toList())
        );
    }

    public List<OrderResponse> getMyOrders() {
        User user = authService.getLoginUser();
        return orderRepository.findByUser(user).stream()
                .map(order -> new OrderResponse(
                        order.getId(),
                        order.getRecipient(),
                        order.getPhone(),
                        order.getAddress(),
                        order.getTotalPrice(),
                        order.getCreatedAt(),
                        order.getOrderItems().stream()
                                .map(oi -> new OrderResponse.OrderItemResponse(
                                        oi.getProduct().getName(),
                                        oi.getQuantity(),
                                        oi.getPrice()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}
