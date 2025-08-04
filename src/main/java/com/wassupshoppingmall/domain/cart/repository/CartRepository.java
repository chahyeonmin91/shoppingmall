package com.wassupshoppingmall.domain.cart.repository;

import com.wassupshoppingmall.domain.cart.entity.Cart;
import com.wassupshoppingmall.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);

    Optional<Cart> findByUserAndProduct_Id(User user, Long productId);
}
