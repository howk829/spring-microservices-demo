package com.example.microservices.user_service.service;

import com.example.microservices.user_service.dto.LoginRequestDto;
import com.example.microservices.user_service.dto.LoginResponseDto;
import com.example.microservices.user_service.dto.UserRequestDto;
import com.example.microservices.user_service.dto.UserResponseDto;
import com.example.microservices.user_service.entity.User;
import com.example.microservices.user_service.repository.UserRepository;
//import com.example.microservices.user_service.util.JwtUtil;
import com.example.microservices.user_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public Page<User> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    public UserResponseDto registerUser(UserRequestDto requestDto) {
        // Check if email already exists
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered.");
        }

        // Encrypt the password
        String encryptedPassword = passwordEncoder.encode(requestDto.getPassword());

        // Save the user
        User user = new User();
        user.setName(requestDto.getName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(encryptedPassword); // Ideally, hash the password
        user = userRepository.save(user);

        // Map to response DTO
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(user.getId());
        responseDto.setName(user.getName());
        responseDto.setEmail(user.getEmail());

        return responseDto;
    }

    public LoginResponseDto login(LoginRequestDto requestDto) {
        // Find user by email
        Optional<User> userOptional = userRepository.findByEmail(requestDto.getEmail());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        User user = userOptional.get();

        // Validate password
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail());
        return new LoginResponseDto(token);
    }

}
