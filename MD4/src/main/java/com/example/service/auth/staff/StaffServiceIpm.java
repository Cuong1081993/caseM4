package com.example.service.auth.staff;

import com.example.model.auth.Role;
import com.example.model.auth.Staff;
import com.example.model.auth.User;
import com.example.model.dto.authDTO.StaffInfoDTO;
import com.example.model.dto.authDTO.StaffReqDTO;
import com.example.repository.StaffRepository;
import com.example.repository.UserRepository;
import com.example.service.auth.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StaffServiceIpm implements IStaffService {

    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IRoleService roleService;

    @Override
    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    @Override
    public Optional<Staff> findById(Long id) {
        return staffRepository.findById(id);
    }


    @Override
    public Staff save(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    public void deleted(Staff staff) {

    }

    @Override
    public void deletedById(Long id) {

    }

    @Override
    public boolean existById(Long id) {
        return false;
    }

    @Override
    public Optional<StaffInfoDTO> getStaffInfoByUsername(String name) {
        return staffRepository.getStaffInfoByUserName(name);
    }

    @Override
    public void create(StaffReqDTO staffReqDTO) {
        User user = new User();
        user.setId(staffReqDTO.getId());
        user.setUsername(staffReqDTO.getUsername());
        user.setPassword(passwordEncoder.encode(staffReqDTO.getPassword()));
        userRepository.save(user);

        Staff staff = staffReqDTO.toStaff();
        staffRepository.save(staff);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return staffRepository.existsByEmail(email);
    }

    @Override
    public void register(StaffReqDTO staffReqDTO) {

        Staff staff = staffReqDTO.toStaff();
        User user = new User();
        user.setId(null);
        user.setUsername(staffReqDTO.getUsername());
        user.setPassword(passwordEncoder.encode(staffReqDTO.getPassword()));
        Optional<Role> roleOptional = roleService.findByCode("USER");
        user.setRole(roleOptional.get());
        user = userRepository.save(user);
        staff.setUser(user);
        staffRepository.save(staff);

    }

}
