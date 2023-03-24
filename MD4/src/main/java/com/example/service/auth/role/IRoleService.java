package com.example.service.auth.role;

import com.example.model.auth.Role;
import com.example.service.IGeneralService;

import java.util.Optional;

public interface IRoleService extends IGeneralService<Role> {
    Optional<Role> findByCode(String code);
}
