package com.example.service.customer.customer;

import com.example.model.customer.Customer;
import com.example.model.dto.customerDTO.CustomerCreateAvatarResDTO;
import com.example.model.dto.customerDTO.CustomerDTO;
import com.example.model.dto.customerDTO.CustomerResDTO;
import com.example.model.dto.customerDTO.CustomerUpdateAvatarResDTO;
import com.example.service.IGeneralService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ICustomerService extends IGeneralService<Customer> {

    Optional<CustomerResDTO> findCustomerResDTOById(Long id);
    List<CustomerResDTO> findAllByDeletedIsFalse();

    List<CustomerDTO> findAllCustomerDTO();

    List<Customer> findAllByIdNot(Long id);

    Boolean existsByEmail(String email);

    CustomerCreateAvatarResDTO createWithAvatar(Customer customer, MultipartFile avatarFile);

    CustomerUpdateAvatarResDTO update(Customer customer);

    CustomerUpdateAvatarResDTO updateWithAvatar(Customer customer, MultipartFile avatarFile) throws IOException;

}
