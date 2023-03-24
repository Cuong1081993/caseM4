package com.example.service.auth.staff;

import com.example.model.auth.Staff;
import com.example.model.dto.authDTO.StaffInfoDTO;
import com.example.model.dto.authDTO.StaffReqDTO;
import com.example.service.IGeneralService;

import java.util.Optional;

public interface IStaffService extends IGeneralService<Staff> {

    Optional<StaffInfoDTO> getStaffInfoByUsername(String name);
    void create(StaffReqDTO staffReqDTO);
    Boolean existsByEmail(String email);

    void register(StaffReqDTO staffReqDTO);

}
