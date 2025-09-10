package com.eazybytes.acounts.mapper;


import com.eazybytes.acounts.dto.CustomerDto;
import com.eazybytes.acounts.entity.Customer;

public class CustomerMapper {

    public static CustomerDto maptoCustomerDto(Customer customer, CustomerDto customerDto) {
        if (customer == null || customerDto == null) {
            return null;
        }

        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static Customer maptoCustomer(CustomerDto customerDto, Customer customer) {
        if (customerDto == null || customer == null) {
            return null;
        }

        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }
}
