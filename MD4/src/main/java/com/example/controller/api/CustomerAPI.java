package com.example.controller.api;


import com.example.exception.DataInputException;
import com.example.exception.EmailExistsException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.customer.Customer;
import com.example.model.customer.CustomerAvatar;
import com.example.model.dto.customerDTO.*;
import com.example.service.customer.customer.ICustomerService;
import com.example.service.customer.customerAvatar.ICustomerAvatarService;
import com.example.service.uploadMedia.IUploadService;
import com.example.utils.AppUtils;
import com.example.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerAPI {

    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ICustomerAvatarService customerAvatarService;
    @Autowired
    private AppUtils appUtils;

    @Autowired
    private UploadUtils uploadUtils;
    @Autowired
    private IUploadService uploadService;

    @GetMapping
    public ResponseEntity<?> getAll() {

        List<CustomerResDTO> customerResDTOS = customerService.findAllByDeletedIsFalse();
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

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getById(@PathVariable Long customerId) {

        Optional<CustomerResDTO> customerResDTO = customerService.findCustomerResDTOById(customerId);
        if (!customerResDTO.isPresent()) {
            throw new ResourceNotFoundException("Customer is Invalid");
        }
        CustomerAvatarDTO customerAvatarDTO = new CustomerAvatarDTO();
        customerAvatarDTO.setId(customerResDTO.get().getAvatarId());
        customerAvatarDTO.setFileFolder(customerResDTO.get().getFileFolder());
        customerAvatarDTO.setFileName(customerResDTO.get().getFileName());
        customerAvatarDTO.setFileUrl(customerResDTO.get().getFileUrl());

        CustomerDTO customerDTO = customerResDTO.get().toCustomerDTO(customerAvatarDTO);
        return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CustomerDTO customerDTO) {
        Boolean existEmail = customerService.existsByEmail(customerDTO.getEmail());
        if (existEmail) {
            throw new EmailExistsException("Email is exists");
        }

        customerDTO.setId(null);
        customerDTO.getLocationRegion().setId(null);

        Customer customer = customerDTO.toCustomer();
        customerService.save(customer);

        customerDTO = customer.toCustomerDTO();

        return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);
    }

    @PostMapping("/create-with-avatar")
    public ResponseEntity<?> createWithAvatar(CustomerCreateAvatarReqDTO customerCreateAvatarReqDTO) {

        MultipartFile file = customerCreateAvatarReqDTO.getFile();

        LocationRegionDTO locationRegionDTO = customerCreateAvatarReqDTO.toLocationRegionDTO();
        CustomerDTO customerDTO = customerCreateAvatarReqDTO.toCustomerDTO(locationRegionDTO);

        Boolean existsByEmail = customerService.existsByEmail(customerDTO.getEmail());

        if (existsByEmail) {
            throw new EmailExistsException("Email is exists");
        }
        if (file != null) {
            Customer customer = customerDTO.toCustomer();
            CustomerCreateAvatarResDTO customerCreateAvatarResDTO = customerService.createWithAvatar(customer, file);
            return new ResponseEntity<>(customerCreateAvatarResDTO, HttpStatus.CREATED);
        } else {
            customerDTO.setId(null);
            customerDTO.getLocationRegion().setId(null);

            Customer customer = customerDTO.toCustomer();
            customerService.save(customer);

            customerDTO = customer.toCustomerDTO();
            return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);
        }
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<?> update(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO) {

        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            throw new ResourceNotFoundException("Customer is Invalid");
        }

        Customer customer = customerDTO.toCustomer();
        customer.setId(null);
        customerService.save(customer);

        return new ResponseEntity<>(customer.toCustomerDTO(), HttpStatus.OK);
    }

    @PatchMapping("/update-w-avatar/{customerId}")
    public ResponseEntity<?> updateWithAvatar(@PathVariable Long customerId, MultipartFile file, CustomerUpdateReqDTO customerUpdateReqDTO) throws IOException {

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (!customerOptional.isPresent()) {
            throw new ResourceNotFoundException("Customer not found");
        }
        if (file == null) {
            LocationRegionDTO locationRegionDTO = customerUpdateReqDTO.toLocationRegionDTO();

            CustomerDTO customerDTO = customerUpdateReqDTO.toCustomerDTO(locationRegionDTO);
            Customer customer = customerDTO.toCustomer();
            customer.setId(customerId);

            CustomerUpdateAvatarResDTO customerUpdateAvatarResDTO = customerService.update(customer);
            return new ResponseEntity<>(customerUpdateAvatarResDTO, HttpStatus.OK);
        } else {
            LocationRegionDTO locationRegionDTO = customerUpdateReqDTO.toLocationRegionDTO();

            CustomerDTO customerDTO = customerUpdateReqDTO.toCustomerDTO(locationRegionDTO);
            Customer customer = customerDTO.toCustomer();
            customer.setId(customerId);

            CustomerUpdateAvatarResDTO customerUpdateAvatarResDTO = customerService.updateWithAvatar(customer, file);

            return new ResponseEntity<>(customerUpdateAvatarResDTO, HttpStatus.OK);
        }
    }

    @DeleteMapping("delete-avatar/{customerId}")
    public ResponseEntity<?> deleteAvatar(@PathVariable Long customerId) throws IOException {
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            throw new ResourceNotFoundException("Customer  is invalid");
        }
        Optional<CustomerAvatar> customerAvatar = customerAvatarService.findByCustomer(customerOptional.get());
        String publicId = customerAvatar.get().getCloudId();
        uploadService.destroyImage(publicId, uploadUtils.buildImageUploadParams(customerAvatar.get()));

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/de-active/{customerId}")
    public ResponseEntity<?> deActive(@PathVariable Long customerId){
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()){
            throw new DataInputException("Customer is Invalid");
        }
        customerService.deActive(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/active/{customerId}")
    public ResponseEntity<?> active(@PathVariable Long customerId){
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()){
            throw new DataInputException("Customer is Invalid");
        }
        customerService.active(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
