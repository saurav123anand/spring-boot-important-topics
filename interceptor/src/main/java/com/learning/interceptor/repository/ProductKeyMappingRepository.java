package com.learning.interceptor.repository;

import com.learning.interceptor.entiity.ProductKeyMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductKeyMappingRepository extends JpaRepository<ProductKeyMapping, Long> {

    Optional<ProductKeyMapping> findByExternalId(Long externalId);
}
