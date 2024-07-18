package org.practice.mapper;

import org.practice.dto.AccountsDto;
import org.practice.dto.CustomerDto;
import org.practice.entity.Accounts;
import org.practice.entity.Customer;

public class AccountMapper {

    public static AccountsDto mapsToAccountDto(Accounts account, AccountsDto accountsDto) {
        accountsDto.setAccountNumber(account.getAccountNumber());
        accountsDto.setAccountType(account.getAccountType());
        accountsDto.setBranchAddress(account.getBranchAddress());
        return accountsDto;
    }

    public static Accounts mapsToAccount(AccountsDto accountsDto, Accounts accounts) {
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }


}
