package com.learning.interceptor.service;

import com.learning.interceptor.dto.ProductResponseDto;
import java.util.UUID;

public interface ProductService {
    ProductResponseDto getProductById(UUID productId);
}
