package com.eazybytes.acounts.exception;

import com.eazybytes.acounts.constant.AccountsConstants;
import com.eazybytes.acounts.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(
            CustomerAlreadyExistsException exception, WebRequest webRequest) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(
                        AccountsConstants.STATUS_400,
                        exception.getMessage()
                ));
    }
}
