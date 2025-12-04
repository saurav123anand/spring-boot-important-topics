package com.learning.interceptor.entiity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductKeyMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false)
    private Long externalId;  // the long id sent by user

    @Column(name = "product_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID productId;   // maps to Product.id
}
