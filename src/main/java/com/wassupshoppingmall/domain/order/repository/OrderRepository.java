package com.wassupshoppingmall.domain.order.repository;

import com.wassupshoppingmall.domain.order.entity.Order;
import com.wassupshoppingmall.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    Optional<Order> findByIdAndUser(Long id, User user);
}
