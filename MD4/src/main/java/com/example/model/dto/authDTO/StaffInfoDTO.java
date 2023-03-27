package com.example.model.dto.authDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StaffInfoDTO {

    @NotBlank(message = "Full Name is required")
    @Size(max = 50, message = "The length of Full Name must be between 5 and 50 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    private String email;

}
