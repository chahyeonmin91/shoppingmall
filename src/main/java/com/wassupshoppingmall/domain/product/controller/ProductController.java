package com.wassupshoppingmall.domain.product.controller;

import com.wassupshoppingmall.domain.product.dto.ProductRequest;
import com.wassupshoppingmall.domain.product.dto.ProductResponse;
import com.wassupshoppingmall.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    //전체 조회
    @GetMapping
    public List<ProductResponse> getAll(){
        return productService.getAllProducts();
    }

    //상세 조회
    @GetMapping("/{productId}")
    public ProductResponse getById(@PathVariable Long productId){
        return productService.getProduct(productId);
    }

    //조회
    @GetMapping("/search")
    public List<ProductResponse> search(@RequestParam String keyword){
        return productService.searchProducts(keyword);
    }

}
