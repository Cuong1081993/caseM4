package com.example.model.dto.customerDTO;

import com.example.model.customer.Customer;
import com.example.model.customer.LocationRegion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDTO  {

    private Long id;

    private String fullName;
    private String email;
    private String phone;
    private LocationRegionDTO locationRegion;

    private CustomerAvatarDTO customerAvatar;

    public CustomerDTO(Long id, String fullName, String email, String phone, LocationRegion locationRegion) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.locationRegion = locationRegion.toLocationRegionDTO();
    }

    public CustomerDTO(Long id, String fullName, String email, String phone, LocationRegion locationRegion, CustomerAvatarDTO customerAvatar) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.locationRegion = locationRegion.toLocationRegionDTO();
        this.customerAvatar = customerAvatar;
    }

    public Customer toCustomer(){
        return new Customer()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                .setLocationRegion(locationRegion.toLocationRegion());
    }
}