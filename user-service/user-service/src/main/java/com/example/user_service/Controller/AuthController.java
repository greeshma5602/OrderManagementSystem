package com.example.user_service.Controller;


import com.example.user_service.Service.JwtService;
import com.example.user_service.Service.UserService;
import com.example.user_service.dto.request.LoginRequest;


import com.example.user_service.dto.request.RegisterRequest;
import com.example.user_service.entity.User;


import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")

public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User registered successfully");
    }


    @PostMapping("/Adminregister")
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody RegisterRequest request) {
        userService.registerAdmin(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Admin registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userService.findByEmail(request.getEmail());
        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(token);
    }
    @GetMapping("/{email}")
    public User findUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);

    }
}