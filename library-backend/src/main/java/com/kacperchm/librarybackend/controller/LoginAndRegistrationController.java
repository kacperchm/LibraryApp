package com.kacperchm.librarybackend.controller;

import com.kacperchm.librarybackend.model.LoginCredential;
import com.kacperchm.librarybackend.model.RegisterUser;
import com.kacperchm.librarybackend.model.User;
import com.kacperchm.librarybackend.model.responses.RegistrationResponse;
import com.kacperchm.librarybackend.service.LoginAndRegistrationService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<User> registerUser(@RequestBody RegisterUser user) {
        ResponseEntity<User> response = null;
        User createdUser = service.registerUser(user);
        if (createdUser != null) {
            if (createdUser.getLibraryMember() != null) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(createdUser);
            } else {
                response = ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(null);
            }
        } else {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
        return response;
    }


    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginCredential credential) {
        ResponseEntity<User> response;
        if (service.loginUser(credential) != null) {
            User user = service.loginUser(credential);
            response = ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return response;
    }
}
