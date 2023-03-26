package com.example.model.customer;

import com.example.model.BaseEntity;
import com.example.model.dto.customerDTO.CustomerAvatarDTO;
import com.example.model.dto.customerDTO.CustomerDTO;
import com.example.model.dto.customerDTO.CustomerResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;


    @ManyToOne
    @JoinColumn(name = "location_region_id", referencedColumnName = "id", nullable = false)
    private LocationRegion locationRegion;

    public CustomerDTO toCustomerDTO(){
        return new CustomerDTO()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setLocationRegion(locationRegion.toLocationRegionDTO())
                ;
    }

    public CustomerDTO toCustomerDTO(CustomerAvatarDTO customerAvatarDTO){
        return new CustomerDTO()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                .setCustomerAvatar(customerAvatarDTO)
                .setLocationRegion(locationRegion.toLocationRegionDTO())
                ;
    }
}
