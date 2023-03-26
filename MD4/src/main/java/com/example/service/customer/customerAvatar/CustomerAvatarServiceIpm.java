package com.example.service.customer.customerAvatar;

import com.example.model.customer.Customer;
import com.example.model.customer.CustomerAvatar;
import com.example.repository.CustomerAvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerAvatarServiceIpm implements ICustomerAvatarService {
    @Autowired
    private CustomerAvatarRepository customerAvatarRepository;
    @Override
    public List<CustomerAvatar> findAll() {
        return customerAvatarRepository.findAll();
    }

    @Override
    public Optional<CustomerAvatar> findById(String id) {
        return customerAvatarRepository.findById(id);
    }


    @Override
    public Boolean existById(String id) {
        return null;
    }

    @Override
    public CustomerAvatar save(CustomerAvatar customerAvatar) {
        return customerAvatarRepository.save(customerAvatar);
    }

    @Override
    public void delete(CustomerAvatar customerAvatar) {

    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public Optional<CustomerAvatar> findByCustomer(Customer customer) {
        return customerAvatarRepository.findByCustomer(customer);
    }
}
