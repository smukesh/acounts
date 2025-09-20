package com.eazybytes.acounts.service;

import com.eazybytes.acounts.constant.AccountsConstants;
import com.eazybytes.acounts.dto.AccountsDto;
import com.eazybytes.acounts.dto.CustomerDto;
import com.eazybytes.acounts.entity.Accounts;
import com.eazybytes.acounts.entity.Customer;
import com.eazybytes.acounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.acounts.exception.ResourceNotFoundException;
import com.eazybytes.acounts.impl.IAccountsService;
import com.eazybytes.acounts.mapper.AccountMapper;
import com.eazybytes.acounts.mapper.CustomerMapper;
import com.eazybytes.acounts.repository.AccountRepository;
import com.eazybytes.acounts.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsService implements IAccountsService {


    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = CustomerMapper.maptoCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer= customerRepository.findByMobileNumber(customer.getMobileNumber());
        if(optionalCustomer.isPresent())
        {
            throw new CustomerAlreadyExistsException("customer already exists with the given mobile number " + customer.getMobileNumber());
        }

        Customer savedCustomer= customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));

    }

    private Accounts createNewAccount(Customer customer)
    {
        Accounts newAccount= new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber= 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);

        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber)
    {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","Mobile Number",mobileNumber)
        );

        Accounts account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "Customer Id", String.valueOf(customer.getCustomerId()))
        );

        CustomerDto customerDto = CustomerMapper.maptoCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountMapper.maptoAccountDto(account, new AccountsDto()));

        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto)
    {
        boolean isUpdate=false;
        AccountsDto accountDto=customerDto.getAccountsDto();

        if(accountDto!=null)
        {
            Accounts account=accountRepository.findById(accountDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account ", "AccountNumber", String.valueOf(accountDto.getAccountNumber()) )
            );
            account=AccountMapper.maptoAccount(accountDto,account);
            accountRepository.save(account);

            Long customerId=account.getCustomerId();

            Customer customer=customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer",  "CustomerId", String.valueOf(customerId))
            );

            customer=CustomerMapper.maptoCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdate=true;
        }

        return isUpdate;
    }

    @Override
    public boolean deleteAccount(String mobileNumber)
    {
        boolean isDeleted=false;

        Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Account", "Mobile Number", mobileNumber )
        );

        if(customer!=null)
        {
            customerRepository.deleteById(customer.getCustomerId());
            accountRepository.deleteByCustomerId(customer.getCustomerId());
            isDeleted=true;
        }
        return isDeleted;
    }
}
