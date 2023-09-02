package com.kacperchm.librarybackend.controller;

import com.kacperchm.librarybackend.model.Address;
import com.kacperchm.librarybackend.model.User;
import com.kacperchm.librarybackend.model.UserToTransfer;
import com.kacperchm.librarybackend.model.filter.BookFilter;
import com.kacperchm.librarybackend.model.filter.UserFilter;
import com.kacperchm.librarybackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<User> getUserDetails(@PathVariable Long id) {
        ResponseEntity<User> response;
        User dto = service.getUserInfo(id);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/details")
    public ResponseEntity<List<UserToTransfer>> getUsersDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "ASC") String sort,
            @RequestParam(defaultValue = "username") String order) {
        List<UserToTransfer> users = service.getAllUsersInfo(page, limit, sort, order);
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/size")
    public ResponseEntity<Integer> getQuantityOfBooks() {
        int size = service.getQuantityOfUsers();
        return ResponseEntity.status(HttpStatus.OK)
                .body(size);
    }

    @GetMapping("/filtered-details")
    public ResponseEntity<List<UserToTransfer>> getUsersDetailsFiltered(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "ASC") String sort,
            @RequestParam(defaultValue = "username") String order,
            @RequestParam(defaultValue = "") String filter) {
        UserFilter userFilter = new UserFilter(filter, filter, filter);
        List<UserToTransfer> users = service.getAllFilteredUsersInfo(page, limit, sort, order, userFilter);
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/size-filtered")
    public ResponseEntity<Integer> getQuantityOfFilteredBooks(
            @RequestParam String filter,
            @RequestParam String category
    ) {
        int size = service.getQuantityOfFilteredUsers(new UserFilter(category, filter, filter));
        return ResponseEntity.status(HttpStatus.OK)
                .body(size);
    }

    @PostMapping("/change-number/{id}")
    public ResponseEntity<String> changeNumber(@PathVariable Long id, @RequestBody String number) {
        String response = service.changePhoneNumber(id, number);
        if (response.equals("Number changed successfully")) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @PostMapping("/change-role/{id}")
    public ResponseEntity<String> changeRole(@PathVariable Long id, @RequestBody String role) {
        String response = service.changeRole(id, role);
        if (response.equals("Role changed successfully")) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @PostMapping("/change-password/{id}")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestBody String oldPassword, @RequestBody String newPassword) {
        String response = service.changePassword(id, oldPassword, newPassword);
        if (response.equals("Password changed successfully")) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @PostMapping("/change-address/{id}")
    public ResponseEntity<String> changeAddress(@PathVariable Long id, @RequestBody Address address) {
        String response = service.changeAddress(id, address);
        if (response.equals("Address update pass successfully")) {
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
