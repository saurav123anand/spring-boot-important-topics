package com.learning.interceptor.controller;

import com.learning.interceptor.dto.ProductResponseDto;
import com.learning.interceptor.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products/{id}")
    public ProductResponseDto getProductById(@PathVariable UUID id) {
        log.info("reaching to controller");
        return productService.getProductById(id);
    }
}
