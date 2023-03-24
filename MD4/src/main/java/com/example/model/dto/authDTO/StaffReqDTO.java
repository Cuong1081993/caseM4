package com.example.model.dto.authDTO;

import com.example.model.auth.Staff;
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