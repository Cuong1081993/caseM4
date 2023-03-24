package com.example.model.dto;

import com.example.model.Role;
import com.example.model.Staff;
import com.example.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class StaffReqDTO {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String email;

    public Staff toStaff() {
        return new Staff()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                ;
    }
}