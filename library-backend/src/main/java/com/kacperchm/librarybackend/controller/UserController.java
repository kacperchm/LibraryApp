package com.kacperchm.librarybackend.controller;

import com.kacperchm.librarybackend.mapper.UserMapper;
import com.kacperchm.librarybackend.model.Address;
import com.kacperchm.librarybackend.model.User;
import com.kacperchm.librarybackend.model.dto.UserDto;
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
    public ResponseEntity<UserDto> getUserDetails(@PathVariable Long id) {
        ResponseEntity<UserDto> response;
        UserDto dto = service.getUserInfo(id);
        if(dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/details")
    public ResponseEntity<List<UserDto>> getUsersDetails() {
        List<UserDto> users = service.getAllUsersInfo();
        if(users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/filtered-details")
    public ResponseEntity<List<UserDto>> getUsersDetailsFiltered(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "mail", required = false) String mail,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber) {
        UserFilter filter = new UserFilter(username,mail,phoneNumber);
        List<UserDto> users = service.getAllFilteredUsersInfo(filter);
        if(users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping("/change-number/{id}")
    public ResponseEntity<String> changeNumber(@PathVariable Long id, @RequestBody String number) {
        String response = service.changePhoneNumber(id, number);
        if(response.equals("Number changed successfully")) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @PostMapping("/change-role/{id}")
    public ResponseEntity<String> changeRole(@PathVariable Long id, @RequestBody String role) {
        String response = service.changeRole(id, role);
        if(response.equals("Role changed successfully")) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @PostMapping("/change-password/{id}")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestBody String oldPassword, @RequestBody String newPassword) {
        String response = service.changePassword(id, oldPassword, newPassword);
        if(response.equals("Password changed successfully")) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @PostMapping("/change-address/{id}")
    public ResponseEntity<String> changeAddress(@PathVariable Long id, @RequestBody Address address) {
        String response = service.changeAddress(id, address);
        if(response.equals("Address update pass successfully")) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        String response = service.removeUser(id);
        if (response.equals("User removed successfully") || response.equals("User does not exist")) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
