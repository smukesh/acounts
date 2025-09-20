package com.eazybytes.acounts.mapper;

import com.eazybytes.acounts.dto.AccountsDto;
import com.eazybytes.acounts.entity.Accounts;

public class AccountMapper {

    public static AccountsDto maptoAccountDto(Accounts account, AccountsDto accountdto){

        accountdto.setAccountNumber(account.getAccountNumber());
        accountdto.setAccountType(account.getAccountType());
        accountdto.setBranchAddress(account.getBranchAddress());
        return accountdto;
    }

    public static Accounts maptoAccount(AccountsDto accountdto, Accounts account){

        account.setAccountNumber(accountdto.getAccountNumber());
        account.setAccountType(accountdto.getAccountType());
        account.setBranchAddress(accountdto.getBranchAddress());
        return account;
    }
}
