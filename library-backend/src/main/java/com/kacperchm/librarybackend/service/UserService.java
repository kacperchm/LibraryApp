package com.kacperchm.librarybackend.service;

import com.kacperchm.librarybackend.mapper.UserMapper;
import com.kacperchm.librarybackend.model.Address;
import com.kacperchm.librarybackend.model.User;
import com.kacperchm.librarybackend.model.dto.UserDto;
import com.kacperchm.librarybackend.model.filter.UserFilter;
import com.kacperchm.librarybackend.repository.AddressesRepository;
import com.kacperchm.librarybackend.repository.UsersRepository;
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

    public UserDto getUserInfo(Long id) {
        User user = null;
        UserDto dto = null;
        if(usersRepository.existsById(id)) {
            user = usersRepository.findById(id).get();
            dto = UserMapper.mapToUserDto(user);
        }
        return dto;
    }

    public List<UserDto> getAllUsersInfo() {
        List<UserDto> dtoList = new ArrayList<>();
        List<User> users = usersRepository.findAll();
        users.forEach(user -> dtoList.add(UserMapper.mapToUserDto(user)));
        return dtoList;
    }

    public List<UserDto> getAllFilteredUsersInfo(UserFilter filter) {
        List<UserDto> dtoList = new ArrayList<>();
        List<User> users;
        if(!isStringCorrect(filter.getUsername())) {
            filter.setUsername("");
            if(!isStringCorrect(filter.getPhoneNumber())) {
                filter.setPhoneNumber("");
                if (!isStringCorrect(filter.getMail())) {
                    filter.setMail("");
                }
            }
        }
        users = usersRepository.findUsersByUsernamePhoneNumberAndMail(filter.getUsername(), filter.getPhoneNumber(), filter.getMail());
        users.forEach(user -> dtoList.add(UserMapper.mapToUserDto(user)));
        return dtoList;
    }

    public String changePhoneNumber(Long userId, String newNumber) {
        if(usersRepository.existsById(userId)) {
            if(newNumber.length() == 9) {
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
        if(usersRepository.existsById(userId)) {
            User user = usersRepository.findById(userId).get();
            user.setRole(newRole);
            usersRepository.save(user);
            return "Role changed successfully";
        }
        return "User does not exist";
    }

    public String changePassword(Long userId, String oldPassword, String newPassword) {
        if(usersRepository.existsById(userId)) {
            User user = usersRepository.findById(userId).get();
            if(user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
            } else {
                return "Old password is incorrect";
            }
            usersRepository.save(user);
            return "Password changed successfully";
        }
        return "User does not exist";
    }


    public String changeAddress( Long userId, Address address) {
        Address existingAddress = null;
        if(usersRepository.existsById(userId)) {
            existingAddress = usersRepository.findById(userId).get().getAddress();
        }
        if(existingAddress == null) {
            return "User does not exist";
        }
        if(isStringCorrect(address.getCity())) {
            existingAddress.setCity(address.getCity());
        }
        if(isStringCorrect(address.getStreet())) {
            existingAddress.setStreet(address.getStreet());
        }
        if(isStringCorrect(address.getZipCode())) {
            existingAddress.setZipCode(address.getZipCode());
        }
        if(isStringCorrect(address.getHouseNumber())) {
            existingAddress.setHouseNumber(address.getHouseNumber());
        }
        addressesRepository.save(existingAddress);
        return "Address update pass successfully";
    }

    public String removeUser(Long id) {
        String message = "";
        if (usersRepository.existsById(id)){
            User user = usersRepository.findById(id).get();
            if (user.getLibraryMember().getNumOfBorrowedBooks() == 0) {
                usersRepository.deleteById(id);
                message = "User removed successfully";
            } else {
                message = "User cannot be removed because he has borrowed books";
            }
        }
        if(message.isEmpty()) {
            message = "User does not exist";
        }
        return message;
    }

    private boolean isStringCorrect(String strToVerify) {
        if(strToVerify.isBlank() || strToVerify == null) {
            return false;
        }
        return true;
    }
}
