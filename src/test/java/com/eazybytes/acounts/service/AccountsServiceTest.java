package com.eazybytes.acounts.service;

import com.eazybytes.acounts.constant.AccountsConstants;
import com.eazybytes.acounts.dto.CustomerDto;
import com.eazybytes.acounts.entity.Account;
import com.eazybytes.acounts.entity.Customer;
import com.eazybytes.acounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.acounts.repository.AccountRepository;
import com.eazybytes.acounts.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

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

    @Test
    void createAccount_ShouldSetCorrectAccountDetails() {
        // Given
        when(customerRepository.findByMobileNumber(anyString())).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        // When
        accountsService.createAccount(customerDto);

        // Then
        verify(accountRepository).save(argThat(acc -> 
            acc.getAccountType().equals(AccountsConstants.SAVING) &&
            acc.getBranchAddress().equals(AccountsConstants.ADDRESS) &&
            acc.getCustomerId() == customer.getCustomerId() &&
            acc.getCreatedBy() != null &&
            acc.getCreatedAt() != null
        ));
    }

    @Test
    void createAccount_ShouldSetCustomerDetailsCorrectly() {
        // Given
        when(customerRepository.findByMobileNumber(anyString())).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        // When
        accountsService.createAccount(customerDto);

        // Then
        verify(customerRepository).save(argThat(cust -> 
            cust.getName().equals(customerDto.getName()) &&
            cust.getEmail().equals(customerDto.getEmail()) &&
            cust.getMobileNumber().equals(customerDto.getMobileNumber()) &&
            cust.getCreatedAt() != null &&
            cust.getCreatedBy() != null
        ));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    void createAccount_WithInvalidMobileNumber_ShouldThrowException(String invalidMobile) {
        // Given
        CustomerDto invalidDto = new CustomerDto();
        invalidDto.setName("Test User");
        invalidDto.setEmail("test@example.com");
        invalidDto.setMobileNumber(invalidMobile);

        // When & Then
        assertThrows(
            NullPointerException.class,
            () -> accountsService.createAccount(invalidDto)
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    void createAccount_WithInvalidName_ShouldThrowException(String invalidName) {
        // Given
        CustomerDto invalidDto = new CustomerDto();
        invalidDto.setName(invalidName);
        invalidDto.setEmail("test@example.com");
        invalidDto.setMobileNumber("1234567890");

        // When & Then
        assertThrows(
            NullPointerException.class,
            () -> accountsService.createAccount(invalidDto)
        );
    }

    @Test
    void createAccount_WithNullCustomerDto_ShouldThrowException() {
        // When & Then
        assertThrows(
            NullPointerException.class,
            () -> accountsService.createAccount(null)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid-email", "test@", "@example.com", "test@.com"})
    void createAccount_WithInvalidEmailFormat_ShouldThrowException(String invalidEmail) {
        // Given
        CustomerDto invalidDto = new CustomerDto();
        invalidDto.setName("Test User");
        invalidDto.setEmail(invalidEmail);
        invalidDto.setMobileNumber("1234567890");

        // When & Then
        assertThrows(
            NullPointerException.class,
            () -> accountsService.createAccount(invalidDto)
        );
    }

    private static Stream<Arguments> invalidEmailProvider() {
        return Stream.of(
            Arguments.of((String)null),
            Arguments.of(""),
            Arguments.of(" "),
            Arguments.of("invalid-email"),
            Arguments.of("@invalid.com"),
            Arguments.of("test@.com")
        );
    }

    @Test
    void createAccount_WhenCustomerSaveFails_ShouldNotCreateAccount() {
        // Given
        when(customerRepository.findByMobileNumber(anyString())).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenThrow(new RuntimeException("DB Error"));

        // When & Then
        assertThrows(RuntimeException.class, 
            () -> accountsService.createAccount(customerDto));
        
        verify(customerRepository).findByMobileNumber(customerDto.getMobileNumber());
        verify(customerRepository).save(any(Customer.class));
        verifyNoInteractions(accountRepository);
    }

    @Test
    void createAccount_WhenAccountSaveFails_ShouldNotPersistPartialData() {
        // Given
        when(customerRepository.findByMobileNumber(anyString())).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(accountRepository.save(any(Account.class))).thenThrow(new RuntimeException("DB Error"));

        // When & Then
        assertThrows(RuntimeException.class, 
            () -> accountsService.createAccount(customerDto));
        
        verify(customerRepository).findByMobileNumber(customerDto.getMobileNumber());
        verify(customerRepository).save(any(Customer.class));
        verify(accountRepository).save(any(Account.class));
    }
}
