package com.example.repository;

import com.example.model.customer.Customer;

import com.example.model.dto.customerDTO.CustomerDTO;
import com.example.model.dto.customerDTO.CustomerResDTO;
import com.example.model.dto.customerDTO.CustomerUpdateAvatarResDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT NEW com.example.model.dto.customerDTO.CustomerResDTO (" +
            "cus.id, " +
            "cus.fullName, " +
            "cus.email, " +
            "cus.phone, " +
            "ca.id, " +
            "ca.fileFolder, " +
            "ca.fileName, " +
            "ca.fileUrl, " +
            "cus.locationRegion) " +
            "FROM Customer AS cus " +
            "LEFT JOIN CustomerAvatar as ca " +
            "ON ca.customer = cus " +
            "WHERE cus.deleted = false " +
            "AND cus.id  = :id"
    )
    Optional<CustomerResDTO> findCustomerResDTOById(@Param("id") Long id);

    @Query("SELECT NEW com.example.model.dto.customerDTO.CustomerResDTO (" +
            "cus.id, " +
            "cus.fullName, " +
            "cus.email, " +
            "cus.phone, " +
            "ca.id," +
            "ca.fileFolder, " +
            "ca.fileName, " +
            "ca.fileUrl, " +
            "cus.locationRegion" +
            ")" +
            "FROM Customer AS cus " +
            "LEFT JOIN CustomerAvatar AS ca " +
            "ON ca.customer = cus " +
            "WHERE cus.deleted = false"
    )
    List<CustomerResDTO> findAllByDeletedIsFalse();

    @Query("SELECT NEW com.example.model.dto.customerDTO.CustomerDTO (" +
            "cus.id, " +
            "cus.fullName, " +
            "cus.email, " +
            "cus.phone, " +
            "cus.locationRegion)" +
            "FROM Customer AS cus"
    )
    List<CustomerDTO> findAllCustomerDTO();

    List<Customer> findAllByIdNot(Long id);

    Boolean existsByEmailEquals(String email);



    CustomerUpdateAvatarResDTO updateWithAvatar(Customer customer, MultipartFile avatarFile) throws IOException;
}
