package com.wassupshoppingmall.domain.product.service;

import com.wassupshoppingmall.domain.product.dto.ProductRequest;
import com.wassupshoppingmall.domain.product.dto.ProductResponse;
import com.wassupshoppingmall.domain.product.entity.Product;
import com.wassupshoppingmall.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product createProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setStock(request.getStock());
        return productRepository.save(product);
    }
    public Product updateProduct(Long productId, ProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setStock(request.getStock());
        return productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    public List<ProductResponse> getAllProducts(){
        return productRepository.findAll().stream()
                .map(p -> new ProductResponse(p.getId(), p.getName(), p.getPrice(), p.getDescription(), p.getStock()))
                .toList();
    }
    public ProductResponse getProduct(Long id){
        Product p = productRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("상품을 찾을 수 없습니다"));
        return new ProductResponse(p.getId(), p.getName(), p.getPrice(), p.getDescription(), p.getStock());
    }

    public List<ProductResponse> searchProducts(String keyword){
        return productRepository.findByNameContainingIgnoreCase(keyword).stream()
                .map(p -> new ProductResponse(p.getId(), p.getName(), p.getPrice(), p.getDescription(), p.getStock()))
                .toList();
    }
}
