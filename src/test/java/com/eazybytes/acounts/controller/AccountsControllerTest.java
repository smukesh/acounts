package com.eazybytes.acounts.controller;

import com.eazybytes.acounts.constant.AccountsConstants;
import com.eazybytes.acounts.dto.CustomerDto;
import com.eazybytes.acounts.dto.ResponseDto;
import com.eazybytes.acounts.impl.IAccountsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AccountsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IAccountsService iAccountsService;

    @InjectMocks
    private AccountsController accountsController;

    private ObjectMapper objectMapper = new ObjectMapper();
    private CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountsController).build();
        
        customerDto = new CustomerDto();
        customerDto.setName("Test User");
        customerDto.setEmail("test@example.com");
        customerDto.setMobileNumber("1234567890");
    }

    @Test
    void createAccount_Success() throws Exception {
        // Given
        doNothing().when(iAccountsService).createAccount(any(CustomerDto.class));

        // When & Then
        mockMvc.perform(post("/api/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(AccountsConstants.STATUS_201))
                .andExpect(jsonPath("$.statusMsg").value(AccountsConstants.MESSAGE_201));

        verify(iAccountsService, times(1)).createAccount(any(CustomerDto.class));
    }

    @Test
    void createAccount_WhenInvalidInput_ShouldReturnBadRequest() throws Exception {
        // Given
        CustomerDto invalidCustomer = new CustomerDto(); // Missing required fields

        // When & Then
        mockMvc.perform(post("/api/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidCustomer)))
                .andExpect(status().isBadRequest());

        verify(iAccountsService, never()).createAccount(any(CustomerDto.class));
    }

    @Test
    void sayHello_ShouldReturnHelloMessage() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/hello")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("helo"));
    }

    @Test
    void createAccount_WhenServiceThrowsException_ShouldHandleIt() throws Exception {
        // Given
        doThrow(new RuntimeException("Service exception"))
                .when(iAccountsService).createAccount(any(CustomerDto.class));

        // When & Then
        mockMvc.perform(post("/api/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isInternalServerError());

        verify(iAccountsService, times(1)).createAccount(any(CustomerDto.class));
    }
}
