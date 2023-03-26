package com.example.controller.api;

import com.example.model.dto.customerDTO.CustomerAvatarDTO;
import com.example.model.dto.customerDTO.CustomerDTO;
import com.example.model.dto.customerDTO.CustomerResDTO;
import com.example.service.customer.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/de-activeCustomers")
public class CustomerDeActiveAPI {
    @Autowired
    private ICustomerService customerService;
    @GetMapping
    public ResponseEntity<?> getAllCustomerDeActive(){
        List<CustomerResDTO> customerResDTOS =customerService.findAllByDeletedIsTrue();

        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for (CustomerResDTO item : customerResDTOS) {
            CustomerAvatarDTO customerAvatarDTO = new CustomerAvatarDTO();
            customerAvatarDTO.setId(item.getAvatarId());
            customerAvatarDTO.setFileFolder(item.getFileFolder());
            customerAvatarDTO.setFileName(item.getFileName());
            customerAvatarDTO.setFileUrl(item.getFileUrl());
            CustomerDTO customerDTO = item.toCustomerDTO(customerAvatarDTO);
            customerDTOS.add(customerDTO);
        }
        return new ResponseEntity<>(customerDTOS, HttpStatus.OK);
    }
}
