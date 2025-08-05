package com.wassupshoppingmall.global.auth;

import com.wassupshoppingmall.domain.user.entity.User;
import com.wassupshoppingmall.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final HttpSession session;

    public Long getLoginUserId() {
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            throw new IllegalStateException("로그인이 필요합니다");
        }
        return (Long) userId;
    }

    public User getLoginUser() {
        return userRepository.findById(getLoginUserId())
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다"));
    }

    public boolean isAdmin() {
        Object role = session.getAttribute("role");
        return role != null && "ADMIN".equals(role);
    }

}
