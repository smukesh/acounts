package com.eazybytes.acounts.mapper;


import com.eazybytes.acounts.dto.CustomerDto;
import com.eazybytes.acounts.entity.Customer;

public class CustomerMapper {

    public static CustomerDto maptoCustomerDto(Customer customer, CustomerDto customerdto){

        customerdto.setName(customer.getName());
        customerdto.setEmail(customer.getEmail());
        customerdto.setMobileNumber(customer.getMobileNumber());
        return customerdto;
    }

    public static Customer maptoCustomer(CustomerDto customerdto,Customer customer){

        customer.setName(customerdto.getName());
        customer.setEmail(customerdto.getEmail());
        customer.setMobileNumber(customerdto.getMobileNumber());
        return customer;
    }
}
