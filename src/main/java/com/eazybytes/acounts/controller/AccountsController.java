package com.eazybytes.acounts.controller;

import com.eazybytes.acounts.constant.AccountsConstants;
import com.eazybytes.acounts.dto.CustomerDto;
import com.eazybytes.acounts.dto.ErrorResponseDto;
import com.eazybytes.acounts.dto.ResponseDto;
import com.eazybytes.acounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.acounts.impl.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api", produces={MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class AccountsController {

    private final IAccountsService iAccountsService;

    @GetMapping("/hello")
    public String sayHello() {
        return "helo";
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {
        try {
            iAccountsService.createAccount(customerDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
        } catch (CustomerAlreadyExistsException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(AccountsConstants.STATUS_400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, "An error occurred while creating the account"));
        }
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponseDto> handleNullPointerException(NullPointerException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(
                        AccountsConstants.STATUS_400,
                        AccountsConstants.MESSAGE_400 + ": " + ex.getMessage()
                ));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDto(
                        AccountsConstants.STATUS_500,
                        AccountsConstants.MESSAGE_500
                ));
    }
}
