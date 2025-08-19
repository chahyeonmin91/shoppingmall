package com.wassupshoppingmall.domain.order.service;

import com.wassupshoppingmall.domain.cart.entity.Cart;
import com.wassupshoppingmall.domain.cart.repository.CartRepository;
import com.wassupshoppingmall.domain.order.controller.OrderController;
import com.wassupshoppingmall.domain.order.dto.OrderPreviewResponse;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AuthService authService;
    private final CartRepository cartRepository;

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
        order.setTotalPrice(total);
        Order saved = orderRepository.save(order);

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getMyOrders() {
        User user = authService.getLoginUser();
        List<Order> orders = orderRepository.findByUser(user);

        List<OrderResponse> result = new ArrayList<>();
        for(Order o: orders){
            result.add(toResponse(o));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public OrderResponse getMyOrderDetail(Long orderId) {
        User user = authService.getLoginUser();
        Order order = orderRepository.findByIdAndUser(orderId,user)
                .orElseThrow(()->new IllegalArgumentException("해당 주문이 없거나 접근 권한이 없습니다."));
        return toResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrdersForAdmin() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> result = new ArrayList<>();
        for(Order o: orders){
            result.add(toResponse(o));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderDetailForAdmin(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new IllegalArgumentException("주문을 찾을 수 없습니다."));
        return toResponse(order);
    }

    @Transactional(readOnly = true)
    public OrderPreviewResponse previewFromCart(){
        User user =authService.getLoginUser();

        List<Cart> carts = cartRepository.findByUser(user);
        int total = 0;

        List<OrderResponse.OrderItemResponse> items = new ArrayList<>();

        for(Cart c: carts){
                    int price = c.getProduct().getPrice();
                    total += price * c.getQuantity();
                    items.add(new OrderResponse.OrderItemResponse(
                            c.getProduct().getName(),
                            c.getQuantity(),
                            price
                    ));
        }

        return OrderPreviewResponse.builder()
                .totalPrice(total)
                .items(items)
                .build();
    }







    private OrderResponse toResponse(Order order) {
        List<OrderResponse.OrderItemResponse> items = new ArrayList<>();
        for(OrderItem oi:order.getOrderItems()) {
            items.add(new OrderResponse.OrderItemResponse(
                    oi.getProduct().getName(),
                    oi.getQuantity(),
                    oi.getPrice()
            ));
        }

        return OrderResponse.builder()
                .orderId(order.getId())
                .recipient(order.getRecipient())
                .phone(order.getPhone())
                .address(order.getAddress())
                .totalPrice(order.getTotalPrice())
                .items(items)
                .build();
    }
}
