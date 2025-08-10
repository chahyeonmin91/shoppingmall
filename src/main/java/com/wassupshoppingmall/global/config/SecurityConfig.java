package com.wassupshoppingmall.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("\"/swagger-ui.html\",\n" +
                                "    \"/swagger-ui/**\",\n" +
                                "    \"/v3/api-docs/**\",\n" +
                                "    \"/swagger-resources/**\",\n" +
                                "    \"/webjars/**\"").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable()) // ✅ 람다 스타일로 CSRF 비활성화
                .formLogin(form -> form.disable()); // 기본 로그인 폼 비활성화

        return http.build();
    }
}
