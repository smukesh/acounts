package com.eazybytes.acounts.mapper;

import com.eazybytes.acounts.dto.CustomerDto;
import com.eazybytes.acounts.entity.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    @Test
    void testMapToCustomerDto() {
        // Given
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setMobileNumber("1234567890");
        
        // When
        CustomerDto customerDto = CustomerMapper.maptoCustomerDto(customer, new CustomerDto());
        
        // Then
        assertNotNull(customerDto);
        assertEquals(customer.getName(), customerDto.getName());
        assertEquals(customer.getEmail(), customerDto.getEmail());
        assertEquals(customer.getMobileNumber(), customerDto.getMobileNumber());
    }
    
    @Test
    void testMapToCustomer() {
        // Given
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("John Doe");
        customerDto.setEmail("john.doe@example.com");
        customerDto.setMobileNumber("1234567890");
        
        // When
        Customer customer = CustomerMapper.maptoCustomer(customerDto, new Customer());
        
        // Then
        assertNotNull(customer);
        assertEquals(customerDto.getName(), customer.getName());
        assertEquals(customerDto.getEmail(), customer.getEmail());
        assertEquals(customerDto.getMobileNumber(), customer.getMobileNumber());
    }
    
    @Test
    void testMapToCustomerDto_WithNullInput_ShouldReturnNull() {
        // When
        CustomerDto result = CustomerMapper.maptoCustomerDto(null, null);
        
        // Then
        assertNull(result);
    }
    
    @Test
    void testMapToCustomer_WithNullInput_ShouldReturnNull() {
        // When
        Customer result = CustomerMapper.maptoCustomer(null, null);
        
        // Then
        assertNull(result);
    }
}
