package com.learning.filter.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {

    private UUID id;                // internal UUID
    private String name;
    private String description;
    private Double price;

    // extra fields added in ResponseBodyAdvice
    private Long originalExternalId;  // the long id user sent
    private String metaMessage;       // "appended response" etc.
}
