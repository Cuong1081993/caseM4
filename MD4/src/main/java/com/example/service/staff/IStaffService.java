package com.example.service.staff;

import com.example.model.Staff;
import com.example.model.dto.StaffInfoDTO;
import com.example.model.dto.StaffReqDTO;
import com.example.service.IGeneralService;

import java.util.Optional;

public interface IStaffService extends IGeneralService<Staff> {

    Optional<StaffInfoDTO> getStaffInfoByUsername(String name);
    void create(StaffReqDTO staffReqDTO);
    Boolean existsByEmail(String email);

    void register(StaffReqDTO staffReqDTO);

}
