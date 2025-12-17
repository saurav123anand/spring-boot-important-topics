package com.security.jwt.repository;

import com.security.jwt.entity.WeatherLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherLogRepository extends JpaRepository<WeatherLog, Long> {
}