package com.wassupshoppingmall.domain.product.controller;

import com.wassupshoppingmall.domain.product.dto.ProductRequest;
import com.wassupshoppingmall.domain.product.dto.ProductResponse;
import com.wassupshoppingmall.domain.product.entity.Product;
import com.wassupshoppingmall.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {
    private final ProductService productService;

    //관리자 상품 등록
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
       Product product = productService.createProduct(request);
       return ResponseEntity.ok(
                new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getDescription(),
                        product.getStock()
                )
        );
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long productId, @RequestBody ProductRequest request) {
        Product updated = productService.updateProduct(productId, request);
        return ResponseEntity.ok(
                new ProductResponse(
                        updated.getId(),
                        updated.getName(),
                        updated.getPrice(),
                        updated.getDescription(),
                        updated.getStock()
                )
        );
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
