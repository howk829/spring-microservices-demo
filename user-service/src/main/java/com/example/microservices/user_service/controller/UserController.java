package com.example.microservices.user_service.controller;

import com.example.microservices.user_service.dto.LoginRequestDto;
import com.example.microservices.user_service.dto.LoginResponseDto;
import com.example.microservices.user_service.dto.UserRequestDto;
import com.example.microservices.user_service.dto.UserResponseDto;
import com.example.microservices.user_service.entity.User;
import com.example.microservices.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        Page<User> users = userService.getAllUsers(page, size);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRequestDto requestDto) {
        UserResponseDto responseDto = userService.registerUser(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto) {
        LoginResponseDto responseDto = userService.login(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/profile")
    public ResponseEntity<String> getUserProfile(@RequestHeader("X-Authenticated-User") String username) {
        return ResponseEntity.ok("Welcome, " + username);
    }


}