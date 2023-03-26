package com.example.service.customer.customer;

import com.example.exception.DataInputException;
import com.example.model.customer.Customer;
import com.example.model.customer.CustomerAvatar;
import com.example.model.customer.LocationRegion;
import com.example.model.customer.LocationRegionRepository;
import com.example.model.dto.customerDTO.CustomerCreateAvatarResDTO;
import com.example.model.dto.customerDTO.CustomerDTO;
import com.example.model.dto.customerDTO.CustomerResDTO;
import com.example.model.dto.customerDTO.CustomerUpdateAvatarResDTO;
import com.example.repository.CustomerAvatarRepository;
import com.example.repository.CustomerRepository;
import com.example.service.uploadMedia.IUploadService;
import com.example.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceIpm implements ICustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LocationRegionRepository locationRegionRepository;
    @Autowired
    private CustomerAvatarRepository customerAvatarRepository;
    @Autowired
    private IUploadService uploadService;

    @Autowired
    private UploadUtils uploadUtils;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        LocationRegion locationRegion = customer.getLocationRegion();
        locationRegionRepository.save(locationRegion);

        customer.setLocationRegion(locationRegion);

        return customerRepository.save(customer);
    }

    @Override
    public void deleted(Customer customer) {

    }

    @Override
    public void deActive(Long customerId) {
        customerRepository.deActive(customerId);
    }

    @Override
    public void deletedById(Long id) {

    }

    @Override
    public boolean existById(Long id) {
        return false;
    }

    @Override
    public Optional<CustomerResDTO> findCustomerResDTOById(Long id) {
        return customerRepository.findCustomerResDTOById(id);
    }

    @Override
    public List<CustomerResDTO> findAllByDeletedIsFalse() {
        return customerRepository.findAllByDeletedIsFalse();
    }

    @Override
    public List<CustomerDTO> findAllCustomerDTO() {
        return customerRepository.findAllCustomerDTO();
    }

    @Override
    public List<Customer> findAllByIdNot(Long id) {
        return customerRepository.findAllByIdNot(id);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return customerRepository.existsByEmailEquals(email);
    }

    @Override
    public CustomerCreateAvatarResDTO createWithAvatar(Customer customer, MultipartFile avatarFile) {

        LocationRegion locationRegion = customer.getLocationRegion();
        locationRegionRepository.save(locationRegion);

        customer.setLocationRegion(locationRegion);
        customerRepository.save(customer);

        CustomerAvatar customerAvatar = new CustomerAvatar();
        customerAvatar.setCustomer(customer);

        customerAvatarRepository.save(customerAvatar);
        uploadAndSaveCustomerAvatar(avatarFile, customerAvatar);

        return new CustomerCreateAvatarResDTO(customer, locationRegion, customerAvatar.toCustomerAvatarDTO());
    }

    @Override
    public CustomerUpdateAvatarResDTO update(Customer customer) {

        LocationRegion locationRegion = customer.getLocationRegion();
        locationRegionRepository.save(locationRegion);

        customer.setLocationRegion(locationRegion);
        customerRepository.save(customer);

        CustomerAvatar customerAvatar = customerAvatarRepository.findByCustomer(customer).get();
        return new CustomerUpdateAvatarResDTO(customer, locationRegion, customerAvatar.toCustomerAvatarDTO());
    }

    @Override
    public CustomerUpdateAvatarResDTO updateWithAvatar(Customer customer, MultipartFile avatarFile) throws IOException {

        LocationRegion locationRegion = customer.getLocationRegion();
        locationRegionRepository.save(locationRegion);

        customer.setLocationRegion(locationRegion);
        customerRepository.save(customer);

        Optional<CustomerAvatar> customerAvatarOptional = customerAvatarRepository.findByCustomer(customer);

        CustomerAvatar customerAvatar = new CustomerAvatar();

        if (!customerAvatarOptional.isPresent()) {
            customerAvatar.setCustomer(customer);
            customerAvatarRepository.save(customerAvatar);
            uploadAndSaveCustomerAvatar(avatarFile, customerAvatar);

        } else {
            customerAvatar = customerAvatarOptional.get();
            uploadService.destroyImage(customerAvatar.getCloudId(), uploadUtils.buildImageUploadParams(customerAvatar));
            uploadAndSaveCustomerAvatar(avatarFile, customerAvatar);
        }
        return new CustomerUpdateAvatarResDTO(customer, locationRegion, customerAvatar.toCustomerAvatarDTO());
    }

    private void uploadAndSaveCustomerAvatar(MultipartFile file, CustomerAvatar customerAvatar) {
        try {
            Map uploadResult = uploadService.uploadImage(file, uploadUtils.buildImageUploadParams(customerAvatar));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            customerAvatar.setFileName(customerAvatar.getId() + "." + fileFormat);
            customerAvatar.setFileUrl(fileUrl);
            customerAvatar.setFileFolder(uploadUtils.IMAGE_UPLOAD_FOLDER);
            customerAvatar.setCloudId(customerAvatar.getFileFolder() + "/" + customerAvatar.getId());
            customerAvatarRepository.save(customerAvatar);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }
}
