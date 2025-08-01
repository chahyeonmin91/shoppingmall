package com.wassupshoppingmall.domain.user.controller;

import com.wassupshoppingmall.domain.user.dto.LoginRequest;
import com.wassupshoppingmall.domain.user.dto.LoginResponse;
import com.wassupshoppingmall.domain.user.dto.SignupRequest;
import com.wassupshoppingmall.domain.user.entity.User;
import com.wassupshoppingmall.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        userService.signup(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpSession session) {
        User user = userService.login(request.getEmail(), request.getPassword());
        //세션에 공통 키로 저장
        session.setAttribute("userId", user.getId());
        session.setAttribute("role", user.getRole().name());

        //로그인 성공시 role과 함께 응답
        LoginResponse response = new LoginResponse(user.getId(), user.getRole().name());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("로그아웃 성공");
    }
}