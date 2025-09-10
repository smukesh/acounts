package com.eazybytes.acounts.mapper;

import com.eazybytes.acounts.dto.AccountDto;
import com.eazybytes.acounts.entity.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountMapperTest {

    @Test
    void testMapToAccountDto() {
        // Given
        Account account = new Account();
        account.setAccountNumber(1234567890L);
        account.setAccountType("SAVINGS");
        account.setBranchAddress("123 Main St");
        
        // When
        AccountDto accountDto = AccountMapper.maptoAccountDto(account, new AccountDto());
        
        // Then
        assertNotNull(accountDto);
        assertEquals(account.getAccountNumber(), accountDto.getAccountNumber());
        assertEquals(account.getAccountType(), accountDto.getAccountType());
        assertEquals(account.getBranchAddress(), accountDto.getBranchAddress());
    }
    
    @Test
    void testMapToAccount() {
        // Given
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber(1234567890L);
        accountDto.setAccountType("SAVINGS");
        accountDto.setBranchAddress("123 Main St");
        
        // When
        Account account = AccountMapper.maptoAccount(new Account(), accountDto);
        
        // Then
        assertNotNull(account);
        assertEquals(accountDto.getAccountNumber(), account.getAccountNumber());
        assertEquals(accountDto.getAccountType(), account.getAccountType());
        assertEquals(accountDto.getBranchAddress(), account.getBranchAddress());
    }
    
    @Test
    void testMapToAccountDto_WithNullInput_ShouldReturnNull() {
        // When
        AccountDto result = AccountMapper.maptoAccountDto(null, null);
        
        // Then
        assertNull(result);
    }
    
    @Test
    void testMapToAccount_WithNullInput_ShouldReturnNull() {
        // When
        Account result = AccountMapper.maptoAccount(null, null);
        
        // Then
        assertNull(result);
    }
}
