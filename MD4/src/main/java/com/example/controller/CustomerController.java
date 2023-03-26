package com.example.controller;

import com.example.exception.UnauthorizedException;
import com.example.model.dto.authDTO.StaffInfoDTO;
import com.example.service.auth.staff.IStaffService;
import com.example.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private IStaffService staffService;
    @Autowired
    private AppUtils appUtils;

    @GetMapping
    public String showListCustomer(Model model){

        String username = appUtils.getPrincipalUsername();
        Optional<StaffInfoDTO> staffInfoDTO = staffService.getStaffInfoByUsername(username);

        if (!staffInfoDTO.isPresent()){
            throw new UnauthorizedException("Staff doesn't exists");
        }
        String fullName = staffInfoDTO.get().getFullName();
        model.addAttribute("fullName", fullName);
        return "customer/listCustomer";
    }
}
