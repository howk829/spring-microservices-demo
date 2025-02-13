package com.example.microservices.user_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;

}
