package com.eazybytes.acounts.service;

import com.eazybytes.acounts.constant.AccountsConstants;
import com.eazybytes.acounts.dto.CustomerDto;
import com.eazybytes.acounts.entity.Account;
import com.eazybytes.acounts.entity.Customer;
import com.eazybytes.acounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.acounts.mapper.CustomerMapper;
import com.eazybytes.acounts.repository.AccountRepository;
import com.eazybytes.acounts.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountsServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountsService accountsService;

    private CustomerDto customerDto;
    private Customer customer;
    private Account account;

    @BeforeEach
    void setUp() {
        customerDto = new CustomerDto();
        customerDto.setName("Test User");
        customerDto.setEmail("test@example.com");
        customerDto.setMobileNumber("1234567890");

        customer = new Customer();
        customer.setCustomerId(1L);
        customer.setName("Test User");
        customer.setEmail("test@example.com");
        customer.setMobileNumber("1234567890");
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Smuk");

        account = new Account();
        account.setAccountNumber(1234567890L);
        account.setCustomerId(1L);
        account.setAccountType(AccountsConstants.SAVING);
        account.setBranchAddress(AccountsConstants.ADDRESS);
        account.setCreatedAt(LocalDateTime.now());
        account.setCreatedBy("Smuk");
    }

    @Test
    void createAccount_Success() {
        // Given
        when(customerRepository.findByMobileNumber(anyString())).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        // When
        assertDoesNotThrow(() -> accountsService.createAccount(customerDto));

        // Then
        verify(customerRepository, times(1)).findByMobileNumber(customerDto.getMobileNumber());
        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void createAccount_WhenCustomerAlreadyExists_ShouldThrowException() {
        // Given
        when(customerRepository.findByMobileNumber(anyString())).thenReturn(Optional.of(customer));

        // When & Then
        CustomerAlreadyExistsException exception = assertThrows(
                CustomerAlreadyExistsException.class,
                () -> accountsService.createAccount(customerDto)
        );
        
        assertEquals("customer already exists with the given mobile number " + customerDto.getMobileNumber(), 
                    exception.getMessage());
        
        verify(customerRepository, times(1)).findByMobileNumber(customerDto.getMobileNumber());
        verify(customerRepository, never()).save(any(Customer.class));
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void createAccount_ShouldSetAccountNumberInRange() {
        // Given
        when(customerRepository.findByMobileNumber(anyString())).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> {
            Account savedAccount = invocation.getArgument(0);
            assertTrue(savedAccount.getAccountNumber() >= 1000000000L && 
                      savedAccount.getAccountNumber() <= 1999999999L);
            return savedAccount;
        });

        // When
        assertDoesNotThrow(() -> accountsService.createAccount(customerDto));

        // Then
        verify(accountRepository).save(any(Account.class));
    }
}
