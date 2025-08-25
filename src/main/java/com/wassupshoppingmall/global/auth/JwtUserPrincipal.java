package com.wassupshoppingmall.global.auth;

public record JwtUserPrincipal(Long userId, String email, String role) {
}
