package com.wassupshoppingmall.domain.cart.repository;

import com.wassupshoppingmall.domain.cart.entity.Cart;
import com.wassupshoppingmall.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);

    Optional<Cart> findByUserAndProduct_Id(User user, Long productId);

    //product 필드도 Product 엔티티로 매핑되어 있지만,여기서는 굳이 Product 객체를 다 가져올 필요 없이,
    //상품 ID 값만 조건으로 주고 싶을 때 _Id를 씁니다.
    //_Id는 JPA에서 연관관계 필드의 기본키를 직접 비교하겠다는 의미입니다.
}
