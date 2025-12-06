package com.learning.filter.service;

import com.learning.filter.dto.ProductResponseDto;

import java.util.UUID;

public interface ProductService {
    ProductResponseDto getProductById(UUID productId);
}
