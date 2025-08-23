package com.wassupshoppingmall.global.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;


@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-validity-ms}")
    private long validityInMs;

    private Key key;

    @PostConstruct
    void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String genersteToken(Long userId, String email, String role) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + validityInMs);

        return Jwts.builder()

    }


}
