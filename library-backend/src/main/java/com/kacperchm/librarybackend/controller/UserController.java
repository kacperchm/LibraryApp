package com.kacperchm.librarybackend.controller;

import com.kacperchm.librarybackend.model.Address;
import com.kacperchm.librarybackend.model.User;
import com.kacperchm.librarybackend.model.UserToTransfer;
import com.kacperchm.librarybackend.model.filter.BookFilter;
import com.kacperchm.librarybackend.model.filter.UserFilter;
import com.kacperchm.librarybackend.model.request.ChangePasswordReq;
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
    public ResponseEntity<UserToTransfer> getUserDetails(@PathVariable Long id) {
        ResponseEntity<User> response;
        UserToTransfer dto = service.getUserInfo(id);
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

    @GetMapping("/filter")
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
            @RequestParam String filter
    ) {
        int size = service.getQuantityOfFilteredUsers(new UserFilter(filter, filter, filter));
        return ResponseEntity.status(HttpStatus.OK)
                .body(size);
    }

    @PostMapping("/change/number/{id}")
    public ResponseEntity<UserToTransfer> changeNumber(@PathVariable Long id, @RequestBody String number) {
        UserToTransfer response = service.changePhoneNumber(id, number);
        if (response.getId() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @PostMapping("/change/role/{id}")
    public ResponseEntity<UserToTransfer> changeRole(@PathVariable Long id, @RequestBody String role) {
        UserToTransfer response = service.changeRole(id, role);
        if (response.getId() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @PostMapping("/change/password/{id}")
    public ResponseEntity<UserToTransfer> changePassword(@PathVariable Long id, @RequestBody ChangePasswordReq passwords) {
        UserToTransfer response = service.changePassword(id, passwords.getOldPassword(), passwords.getNewPassword());
        if (response.getId() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @PostMapping("/change/address/{id}")
    public ResponseEntity<UserToTransfer> changeAddress(@PathVariable Long id, @RequestBody Address address) {
        UserToTransfer response = service.changeAddress(id, address);
        if (response.getId() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserToTransfer> deleteUser(@PathVariable Long id) {
        UserToTransfer response = service.removeUser(id);
        if (response.getId() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
