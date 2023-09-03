package com.kacperchm.librarybackend.service;

import com.kacperchm.librarybackend.mapper.UserMapper;
import com.kacperchm.librarybackend.model.*;
import com.kacperchm.librarybackend.model.filter.UserFilter;
import com.kacperchm.librarybackend.repository.AddressesRepository;
import com.kacperchm.librarybackend.repository.MembersRepository;
import com.kacperchm.librarybackend.repository.UsersRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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
    private MembersRepository membersRepository;

    public UserService(UsersRepository usersRepository, AddressesRepository addressesRepository, MembersRepository membersRepository) {
        this.usersRepository = usersRepository;
        this.addressesRepository = addressesRepository;
        this.membersRepository = membersRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createAdmin() {
        if(!usersRepository.existsByUsername("admin")) {
            usersRepository.save(new User("admin", "admin@gmail.pl","","haslo123","ROLE_ADMIN", new Address(), new LibraryMember()));
        }
    }

    public UserToTransfer getUserInfo(Long id) {
        User user = null;
        if (usersRepository.existsById(id)) {
            user = usersRepository.findById(id).get();
        }
        return UserMapper.mapToUserToTransfer(user);
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

    public UserToTransfer changePhoneNumber(Long userId, String newNumber) {
        if (usersRepository.existsById(userId)) {
            newNumber = newNumber.substring(16,25);
            User user = usersRepository.findById(userId).get();
            user.setPhoneNumber(newNumber);
            usersRepository.save(user);
            return UserMapper.mapToUserToTransfer(user);
        }
        return new UserToTransfer();
    }

    public UserToTransfer changeRole(Long userId, String newRole) {
        if (usersRepository.existsById(userId)) {
            User user = usersRepository.findById(userId).get();
            if(newRole.contains("ROLE_ADMIN")) {
                user.setRole("ROLE_ADMIN");
            } else if (newRole.contains("ROLE_USER")) {
                user.setRole("ROLE_USER");
            }
            usersRepository.save(user);
            return UserMapper.mapToUserToTransfer(user);
        }
        return new UserToTransfer();
    }

    public UserToTransfer changePassword(Long userId, String oldPassword, String newPassword) {
        if (usersRepository.existsById(userId)) {
            User user = usersRepository.findById(userId).get();
            if (user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
                usersRepository.save(user);
                return UserMapper.mapToUserToTransfer(user);
            } else {
                return new UserToTransfer();
            }
        }
        return new UserToTransfer();
    }


    public UserToTransfer changeAddress(Long userId, Address address) {
        Address existingAddress = null;
        User user = null;
        if (usersRepository.existsById(userId)) {
            user = usersRepository.findById(userId).get();
            existingAddress = user.getAddress();
        } else {
            return new UserToTransfer();
        }

        if ( !address.getCity().isEmpty() && !address.getCity().isBlank() && !address.getCity().equals(existingAddress.getCity())) {
            existingAddress.setCity(address.getCity());
        }
        if ( !address.getStreet().isEmpty() && !address.getStreet().isBlank() && !address.getStreet().equals(existingAddress.getStreet())) {
            existingAddress.setStreet(address.getStreet());
        }
        if (!address.getZipCode().isEmpty() && !address.getZipCode().isBlank() && !address.getZipCode().equals(existingAddress.getZipCode())) {
            existingAddress.setZipCode(address.getZipCode());
        }
        if (!address.getHouseNumber().isEmpty() && !address.getHouseNumber().isBlank() && !address.getHouseNumber().equals(existingAddress.getHouseNumber())) {
            existingAddress.setHouseNumber(address.getHouseNumber());
        }
        addressesRepository.save(existingAddress);
        user.setAddress(existingAddress);
        return UserMapper.mapToUserToTransfer(user);
    }

    public UserToTransfer removeUser(Long id) {
        String message = "";
        User user = null;
        if (usersRepository.existsById(id)) {
            user = usersRepository.findById(id).get();
            if (user.getLibraryMember().getNumOfBorrowedBooks() == 0) {
                usersRepository.deleteById(id);
                addressesRepository.deleteById(user.getAddress().getId());
                membersRepository.deleteById(user.getLibraryMember().getId());
                message = "User removed successfully";
            } else {
                message = "User cannot be removed because he has borrowed books";
            }
        }
        if (message.isEmpty()) {
            message = "User does not exist";
        }
        if(message.equals("User removed successfully")){
            return UserMapper.mapToUserToTransfer(user);
        }
        return new UserToTransfer();
    }

    private boolean isStringCorrect(String strToVerify) {
        if (strToVerify.isBlank() || strToVerify.isEmpty()) {
            return false;
        }
        return true;
    }
}
