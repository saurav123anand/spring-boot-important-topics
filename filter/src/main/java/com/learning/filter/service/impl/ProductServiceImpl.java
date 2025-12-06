package com.learning.filter.service.impl;

import com.learning.filter.service.ProductService;
import com.learning.filter.dto.ProductResponseDto;
import com.learning.filter.repository.ProductRepository;
import com.learning.filter.exception.ResourceNotFoundException;
import com.learning.filter.entiity.Product;
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
