package com.eazybytes.acounts.mapper;

import com.eazybytes.acounts.dto.AccountDto;
import com.eazybytes.acounts.entity.Account;

public class AccountMapper {

    public static AccountDto maptoAccountDto(Account account, AccountDto accountDto) {
        if (account == null || accountDto == null) {
            return null;
        }

        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBranchAddress(account.getBranchAddress());
        return accountDto;
    }

    public static Account maptoAccount(Account account, AccountDto accountDto) {
        if (account == null || accountDto == null) {
            return null;
        }

        account.setAccountNumber(accountDto.getAccountNumber());
        account.setAccountType(accountDto.getAccountType());
        account.setBranchAddress(accountDto.getBranchAddress());
        return account;
    }
}
