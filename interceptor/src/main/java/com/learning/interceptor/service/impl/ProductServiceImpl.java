package com.learning.interceptor.service.impl;

import com.learning.interceptor.dto.ProductResponseDto;
import com.learning.interceptor.entiity.Product;
import com.learning.interceptor.exception.ResourceNotFoundException;
import com.learning.interceptor.repository.ProductRepository;
import com.learning.interceptor.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponseDto getProductById(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
