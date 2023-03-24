package com.example.service.customer.customerAvatar;

import com.example.model.customer.Customer;
import com.example.model.customer.CustomerAvatar;
import com.example.service.IGeneralServiceString;

import java.util.Optional;

public interface ICustomerAvatarService extends IGeneralServiceString<CustomerAvatar> {
    Optional<CustomerAvatar> findByCustomer(Customer customer);
}
