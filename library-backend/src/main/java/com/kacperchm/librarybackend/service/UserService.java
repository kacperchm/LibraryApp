package com.kacperchm.librarybackend.service;

import com.kacperchm.librarybackend.model.Address;
import com.kacperchm.librarybackend.model.User;
import com.kacperchm.librarybackend.model.filter.UserFilter;
import com.kacperchm.librarybackend.repository.AddressesRepository;
import com.kacperchm.librarybackend.repository.UsersRepository;
import org.springframework.stereotype.Service;

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
        if(usersRepository.existsById(id)) {
            user = usersRepository.findById(id).get();
        }
        return user;
    }

    public List<User> getAllUsersInfo() {
        return usersRepository.findAll();
    }

    public List<User> getAllFilteredUsersInfo(UserFilter filter) {
        if(!isStringCorrect(filter.getUsername())) {
            filter.setUsername("");
            if(!isStringCorrect(filter.getPhoneNumber())) {
                filter.setPhoneNumber("");
                if (!isStringCorrect(filter.getMail())) {
                    filter.setMail("");
                }
            }
        }
       return usersRepository.findUsersByUsernamePhoneNumberAndMail(filter.getUsername(), filter.getPhoneNumber(), filter.getMail());
    }



    public String changeAddress(Address address, Long userId) {
        Address existingAddress = null;
        if(usersRepository.existsById(userId)) {
            existingAddress = usersRepository.findById(userId).get().getAddress();
        }
        if(existingAddress == null) {
            return "User not exist";
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

    private boolean isStringCorrect(String strToVerify) {
        if(strToVerify.isBlank() || strToVerify == null) {
            return false;
        }
        return true;
    }
}
