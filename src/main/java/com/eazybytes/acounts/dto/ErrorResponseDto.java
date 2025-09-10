package com.eazybytes.acounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {
    private String statusCode;
    private String errorMessage;
    private LocalDateTime timestamp;

    public ErrorResponseDto(String statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
        this.timestamp = LocalDateTime.now();
    }
}
