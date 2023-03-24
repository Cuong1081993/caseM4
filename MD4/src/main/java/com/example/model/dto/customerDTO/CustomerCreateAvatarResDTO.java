package com.example.model.dto.customerDTO;

import com.example.model.customer.Customer;
import com.example.model.customer.CustomerAvatar;
import com.example.model.customer.LocationRegion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerCreateAvatarResDTO {

    private Long id;

    private String fullName;
    private String email;
    private String phone;

    private LocationRegionDTO locationRegion;

    private CustomerAvatarDTO customerAvatar;

    public CustomerCreateAvatarResDTO(Customer customer, LocationRegion locationRegion, CustomerAvatarDTO customerAvatarDTO) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.locationRegion = locationRegion.toLocationRegionDTO();
        this.customerAvatar = customerAvatarDTO;
    }

}
