package com.kacperchm.librarybackend.service;

import com.kacperchm.librarybackend.mapper.UserMapper;
import com.kacperchm.librarybackend.model.Address;
import com.kacperchm.librarybackend.model.Book;
import com.kacperchm.librarybackend.model.User;
import com.kacperchm.librarybackend.model.UserToTransfer;
import com.kacperchm.librarybackend.model.filter.BookFilter;
import com.kacperchm.librarybackend.model.filter.UserFilter;
import com.kacperchm.librarybackend.repository.AddressesRepository;
import com.kacperchm.librarybackend.repository.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private UsersRepository usersRepository;
    private AddressesRepository addressesRepository;

    public UserService(UsersRepository usersRepository, AddressesRepository addressesRepository) {
        this.usersRepository = usersRepository;
        this.addressesRepository = addressesRepository;
    }

    public User getUserInfo(Long id) {
        User user = null;
        if (usersRepository.existsById(id)) {
            user = usersRepository.findById(id).get();
        }
        return user;
    }

    public List<UserToTransfer> getAllUsersInfo(int page, int limit, String sort, String order) {
        sort = sort.toUpperCase();
        Sort.Direction direction;
        if (sort.equals("ASC")) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, order));
        Page<User> users = usersRepository.findAll(pageable);
        List<UserToTransfer> usersList = new ArrayList<>();
        for (User usersToChange : users.getContent()) {
            usersList.add(UserMapper.mapToUserToTransfer(usersToChange));
        }
        return usersList;
    }

    public int getQuantityOfUsers() {
        List<User> users = usersRepository.findAll();
        return users.size();
    }

    public List<UserToTransfer> getAllFilteredUsersInfo(int page, int limit, String sort, String order,UserFilter filter) {
        Page<User> users;
        if (!isStringCorrect(filter.getUsername())) {
            filter.setUsername("");
            if (!isStringCorrect(filter.getPhoneNumber())) {
                filter.setPhoneNumber("");
                if (!isStringCorrect(filter.getMail())) {
                    filter.setMail("");
                }
            }
        }

        sort = sort.toUpperCase();
        Sort.Direction direction;
        if (sort.equals("ASC")) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, order));

        users = usersRepository.findUsersByUsernamePhoneNumberAndMail(filter.getUsername(), filter.getPhoneNumber(), filter.getMail(), pageable);

        List<UserToTransfer> usersList = new ArrayList<>();
        for (User usersToChange : users.getContent()) {
            usersList.add(UserMapper.mapToUserToTransfer(usersToChange));
        }
        return usersList;
    }

    public int getQuantityOfFilteredUsers(UserFilter filter) {
        List<User> users;
        users = usersRepository.findUsersByFilter(filter.getUsername(), filter.getPhoneNumber(), filter.getMail());
        return users.size();
    }

    public String changePhoneNumber(Long userId, String newNumber) {
        if (usersRepository.existsById(userId)) {
            if (newNumber.length() == 9) {
                User user = usersRepository.findById(userId).get();
                user.setPhoneNumber(newNumber);
                usersRepository.save(user);
                return "Number changed successfully";
            } else {
                return "Wrong number format";
            }
        }
        return "User does not exist";
    }

    public String changeRole(Long userId, String newRole) {
        if (usersRepository.existsById(userId)) {
            User user = usersRepository.findById(userId).get();
            user.setRole(newRole);
            usersRepository.save(user);
            return "Role changed successfully";
        }
        return "User does not exist";
    }

    public String changePassword(Long userId, String oldPassword, String newPassword) {
        if (usersRepository.existsById(userId)) {
            User user = usersRepository.findById(userId).get();
            if (user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
            } else {
                return "Old password is incorrect";
            }
            usersRepository.save(user);
            return "Password changed successfully";
        }
        return "User does not exist";
    }


    public String changeAddress(Long userId, Address address) {
        Address existingAddress = null;
        if (usersRepository.existsById(userId)) {
            existingAddress = usersRepository.findById(userId).get().getAddress();
        }
        if (existingAddress == null) {
            return "User does not exist";
        }
        if (isStringCorrect(address.getCity())) {
            existingAddress.setCity(address.getCity());
        }
        if (isStringCorrect(address.getStreet())) {
            existingAddress.setStreet(address.getStreet());
        }
        if (isStringCorrect(address.getZipCode())) {
            existingAddress.setZipCode(address.getZipCode());
        }
        if (isStringCorrect(address.getHouseNumber())) {
            existingAddress.setHouseNumber(address.getHouseNumber());
        }
        addressesRepository.save(existingAddress);
        return "Address update pass successfully";
    }

    public String removeUser(Long id) {
        String message = "";
        if (usersRepository.existsById(id)) {
            User user = usersRepository.findById(id).get();
            if (user.getLibraryMember().getNumOfBorrowedBooks() == 0) {
                usersRepository.deleteById(id);
                message = "User removed successfully";
            } else {
                message = "User cannot be removed because he has borrowed books";
            }
        }
        if (message.isEmpty()) {
            message = "User does not exist";
        }
        return message;
    }

    private boolean isStringCorrect(String strToVerify) {
        if (strToVerify.isBlank() || strToVerify == null) {
            return false;
        }
        return true;
    }
}
