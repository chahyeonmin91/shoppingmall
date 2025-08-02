package com.wassupshoppingmall.domain;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ApiTestController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "🛒 쇼핑몰 API 서버가 정상적으로 실행 중입니다!");
        response.put("status", "SUCCESS");
        response.put("availableEndpoints", getAvailableEndpoints());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api")
    public ResponseEntity<Map<String, Object>> apiInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("title", "쇼핑몰 API 문서");
        response.put("version", "1.0.0");
        response.put("endpoints", getAvailableEndpoints());
        return ResponseEntity.ok(response);
    }

    private List<Map<String, String>> getAvailableEndpoints() {
        return Arrays.asList(
                createEndpoint("GET", "/", "홈페이지"),
                createEndpoint("GET", "/api", "API 정보"),
                createEndpoint("GET", "/health", "헬스체크"),
                createEndpoint("POST", "/api/admin/products", "상품 등록"),
                createEndpoint("GET", "/api/admin/products", "상품 목록 (추가 필요)"),
                createEndpoint("GET", "/actuator/health", "시스템 상태")
        );
    }

    private Map<String, String> createEndpoint(String method, String path, String description) {
        Map<String, String> endpoint = new HashMap<>();
        endpoint.put("method", method);
        endpoint.put("path", path);
        endpoint.put("description", description);
        return endpoint;
    }
}