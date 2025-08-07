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
import com.wassupshoppingmall.global.auth.AuthService;
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
    private final AuthService authService;

    public List<CartResponse> getCartItems() {
        User user = authService.getLoginUser();
        return cartRepository.findByUser(user).stream()
                .map(c -> new CartResponse(
                        c.getId(),
                        c.getProduct().getId(),
                        c.getProduct().getName(),
                        c.getQuantity(),
                        c.getProduct().getPrice()
                ))
                .toList();
    }

    public void addToCart(CartRequest request) {
        User user = authService.getLoginUser();

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다"));

        //이미 장바구니에 있으면 수량만 업데이트
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

    public void updateCartItem(Long cartId, int quantity) {
        User user = authService.getLoginUser();
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 항목을 찾을 수 없습니다"));

        if (!cart.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인 장바구니만 수정할 수 있습니다");
        }
        cart.setQuantity(quantity);
        cartRepository.save(cart);

    }

    public void deleteCartItem(Long cartId) {
        User user = authService.getLoginUser();
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 항목을 찾을 수 없습니다"));

        if (!cart.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인 장바구니만 삭제할 수 있습니다");
        }
        cartRepository.delete(cart);
    }
}
