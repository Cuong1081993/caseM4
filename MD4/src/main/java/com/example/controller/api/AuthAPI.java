package com.example.controller.api;


import com.example.exception.DataInputException;
import com.example.exception.EmailExistsException;
import com.example.model.JwtResponse;
import com.example.model.Role;
import com.example.model.Staff;
import com.example.model.User;
import com.example.model.dto.UserDTO;
import com.example.model.dto.UserLoginDTO;
import com.example.service.jwt.JwtService;
import com.example.service.role.IRoleService;
import com.example.service.staff.IStaffService;
import com.example.service.user.IUserService;
import com.example.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Stack;

@RestController
@RequestMapping("/api/auth")
public class AuthAPI {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IUserService userService;
    @Autowired
    private IStaffService staffService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AppUtils appUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody UserDTO userDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Optional<Role> roleOptional = roleService.findById(userDTO.getId());
        if (!roleOptional.isPresent()){
            throw new DataInputException("Role is invalid");
        }
        Boolean existsUsername = userService.existsByUsername(userDTO.getUsername());
        if (existsUsername){
            throw new EmailExistsException("User name is exists");
        }
        try {
            Staff staff = new Staff();
            User user = userService.save(userDTO.toUser());
            staff.setUser(user);
            staffService.save(staff);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataIntegrityViolationException e){
            throw new DataInputException("Account information is not valid, please check again");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtService.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User currentUser = userService.getByUsername(userLoginDTO.getUsername());

            JwtResponse jwtResponse = new JwtResponse(
                    jwt,
                    currentUser.getId(),
                    userDetails.getUsername(),
                    currentUser.getUsername(),
                    userDetails.getAuthorities()
            );

            ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
                    .httpOnly(false)
                    .secure(false)
                    .path("/")
                    .maxAge(60 * 1000)
                    .domain("localhost")
                    .build();

            System.out.println(jwtResponse);

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                    .body(jwtResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
