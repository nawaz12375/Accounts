package org.practice.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.practice.constants.AccountsConstant;
import org.practice.dto.AccountsDto;
import org.practice.dto.CustomerDto;
import org.practice.entity.Accounts;
import org.practice.entity.Customer;
import org.practice.exception.CustomerAlreadyExistsException;
import org.practice.exception.ResourceNotFoundException;
import org.practice.mapper.AccountMapper;
import org.practice.mapper.CustomerMapper;
import org.practice.repository.IAccountRepository;
import org.practice.repository.ICustomerRepository;
import org.practice.service.IAccountsService;
import org.springframework.stereotype.Service;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@Data
public class AccountsServiceImpl implements IAccountsService {

    private IAccountRepository iAccountRepository;
    private ICustomerRepository iCustomerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapttoCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = iCustomerRepository.findByMobileNumber(customer.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    + customerDto.getMobileNumber());
        }
        Customer savedCustomer = iCustomerRepository.save(customer);
        iAccountRepository.save(createNewAccount(savedCustomer));
    }

    public Accounts createNewAccount(Customer customer) {
        Accounts accounts = new Accounts();
        accounts.setCustomerId(customer.getCustomerId());
        long randomNumb = 100000000L + new Random().nextInt(900000);
        accounts.setAccountNumber(randomNumb);
        accounts.setAccountType(AccountsConstant.SAVINGS);
        accounts.setBranchAddress(AccountsConstant.ADDRESS);
        return accounts;
    }

    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = iCustomerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = iAccountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "CustomerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.maptoCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountMapper.mapsToAccountDto(accounts, new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto != null) {
            Accounts accounts = iAccountRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            iAccountRepository.save(AccountMapper.mapsToAccount(accountsDto, accounts));
            Long customerId = accounts.getCustomerId();
            Customer customer = iCustomerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            iCustomerRepository.save(CustomerMapper.mapttoCustomer(customerDto, customer));
            isUpdated = true;
        }

        return isUpdated;
    }


    public boolean deleteByMobileNumber(String mobileNumber) {
        Customer customer = iCustomerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        iAccountRepository.deleteByCustomerId(customer.getCustomerId());
        iCustomerRepository.deleteById(customer.getCustomerId());
        return true;
    }


}
