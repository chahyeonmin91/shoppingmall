package com.wassupshoppingmall.domain.cart.service;

import com.wassupshoppingmall.domain.cart.dto.CartRequest;
import com.wassupshoppingmall.domain.cart.dto.CartResponse;
import com.wassupshoppingmall.domain.cart.entity.Cart;
import com.wassupshoppingmall.domain.cart.repository.CartRepository;
import com.wassupshoppingmall.domain.product.dto.ProductResponse;
import com.wassupshoppingmall.domain.product.entity.Product;
import com.wassupshoppingmall.domain.product.repository.ProductRepository;
import com.wassupshoppingmall.domain.user.entity.User;
import com.wassupshoppingmall.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<CartResponse> getCartItems(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return cartRepository.findByUser(user).stream()
                .map(c -> new CartResponse(
                        c.getId(),
                        c.getProduct().getId(),
                        c.getProduct().getName(),
                        c.getQuantity(),
                        c.getProduct().getPrice()
                ))
                .collect(Collectors.toList());
    }

    public void addToCart(CartRequest request, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다"));

        Cart cart = cartRepository.findByUserAndProduct_Id(user, request.getProductId())
                .orElseGet(() -> {
                            Cart newCart = new Cart();
                            newCart.setUser(user);
                            newCart.setProduct(product);
                            newCart.setQuantity(0);
                            return newCart;
                        }
                );
    }

    public void updateCartItem(Long cartId, int quantity, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 항목을 찾을 수 없습니다"));

        if (!cart.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인 장바구니만 수정할 수 있습니다");
        }
        cart.setQuantity(quantity);
        cartRepository.save(cart);

    }

    public void deleteCartItem(Long cartId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 항목을 찾을 수 없습니다"));

        if (!cart.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인 장바구니만 삭제할 수 있습니다");
        }
        cartRepository.delete(cart);
    }
}
