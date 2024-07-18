package org.practice.service;

import org.practice.dto.AccountsDto;
import org.practice.dto.CustomerDto;

public interface IAccountsService {

    void createAccount(CustomerDto customerDto);

    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteByMobileNumber(String mobileNumber);
}
