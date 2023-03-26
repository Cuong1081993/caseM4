package com.example.repository;

import com.example.model.customer.Customer;
import com.example.model.customer.CustomerAvatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerAvatarRepository extends JpaRepository<CustomerAvatar, String> {
    Optional<CustomerAvatar> findByCustomer(Customer customer);
}
