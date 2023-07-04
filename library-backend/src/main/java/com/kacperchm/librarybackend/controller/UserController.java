package com.kacperchm.librarybackend.controller;

import com.kacperchm.librarybackend.model.User;
import com.kacperchm.librarybackend.model.filter.UserFilter;
import com.kacperchm.librarybackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<User> getUserDetails(@PathVariable Long id) {
        ResponseEntity<User> response;
        User user = service.getUserInfo(id);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/details")
    public ResponseEntity<List<User>> getUsersDetails() {
        List<User> users = service.getAllUsersInfo();
        if(users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/filtered-details")
    public ResponseEntity<List<User>> getUsersDetailsFiltered(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "mail", required = false) String mail,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber) {
        UserFilter filter = new UserFilter(username,mail,phoneNumber);
        List<User> users = service.getAllFilteredUsersInfo(filter);
        if(users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


}
