package com.kacperchm.librarybackend.controller;

import com.kacperchm.librarybackend.model.dto.UserDto;
import com.kacperchm.librarybackend.model.responses.RegistrationResponse;
import com.kacperchm.librarybackend.model.User;
import com.kacperchm.librarybackend.service.LoginAndRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginAndRegistrationController {

    private LoginAndRegistrationService service;

    public LoginAndRegistrationController(LoginAndRegistrationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto user) {
        RegistrationResponse registrationResponse = service.registerUser(user);
        return ResponseEntity
                .status(registrationResponse.getStatus())
                .body(registrationResponse.getMessage());
    }
}
