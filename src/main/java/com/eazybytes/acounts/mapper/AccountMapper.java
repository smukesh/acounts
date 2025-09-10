package com.eazybytes.acounts.mapper;

import com.eazybytes.acounts.dto.AccountDto;
import com.eazybytes.acounts.entity.Account;

public class AccountMapper {

    public static AccountDto maptoAccountDto(Account account, AccountDto accountdto){

        accountdto.setAccountNumber(account.getAccountNumber());
        accountdto.setAccountType(account.getAccountType());
        accountdto.setBranchAddress(account.getBranchAddress());
        return accountdto;
    }

    public static Account maptoAccount(Account account, AccountDto accountdto){

        account.setAccountNumber(accountdto.getAccountNumber());
        account.setAccountType(accountdto.getAccountType());
        account.setBranchAddress(accountdto.getBranchAddress());
        return account;
    }
}
