// src/main/java/com/example/demo/shared/exception/ApiErrorResponse.java
package com.learning.interceptor.dto;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

@Getter
@Builder
public class ApiErrorResponse {
    private final String message;
    private final String path;
    private final int status;
    private final Timestamp timestamp;
}
