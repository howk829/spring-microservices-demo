package com.example.microservices.user_service.dto;

import com.example.microservices.user_service.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @ValidPassword
    private String password;

    @Email(message = "Email should be valid")
    private String email;

}