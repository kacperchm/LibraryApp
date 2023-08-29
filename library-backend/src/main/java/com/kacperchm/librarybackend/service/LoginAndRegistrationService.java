package com.kacperchm.librarybackend.service;

import com.kacperchm.librarybackend.model.LoginCredential;
import com.kacperchm.librarybackend.model.User;
import com.kacperchm.librarybackend.model.responses.RegistrationResponse;
import com.kacperchm.librarybackend.repository.AddressesRepository;
import com.kacperchm.librarybackend.repository.MembersRepository;
import com.kacperchm.librarybackend.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginAndRegistrationService {

    private UsersRepository usersRepository;
    private AddressesRepository addressesRepository;
    private MembersRepository membersRepository;

    public LoginAndRegistrationService(UsersRepository usersRepository, AddressesRepository addressesRepository, MembersRepository membersRepository) {
        this.usersRepository = usersRepository;
        this.addressesRepository = addressesRepository;
        this.membersRepository = membersRepository;
    }

    public User loginUser(LoginCredential credential) {
        User user = null;
        if (usersRepository.existsByMail(credential.getEmail())) {
            user = usersRepository.findByMail(credential.getEmail()).get(0);
            boolean samePassword = user.getPassword().equals(credential.getPassword());
            if(!samePassword){
                user = null;
            }
        }
        return user;
    }

    public RegistrationResponse registerUser(User user) {
        try {
            if (areDataUnique(user)) {
                usersRepository.save(user);
                addressesRepository.save(user.getAddress());
                membersRepository.save(user.getLibraryMember());
                return new RegistrationResponse("User create successfully", HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new RegistrationResponse("Failed to create user. Server-side error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new RegistrationResponse(getWrongMessage(user), HttpStatus.CONFLICT);
    }

    public String getWrongMessage(User user) {
        List<String> wrongData = getWrongData(user);
        String message;
        switch (wrongData.size()) {
            case 1: {
                message = String.format("User with this %s already exists", wrongData.get(0));
            }
            break;
            case 2: {
                message = String.format("User with this %s and %s already exists", wrongData.get(0), wrongData.get(1));
            }
            break;
            case 3: {
                message = String.format("User with this %s, %s and %s already exists", wrongData.get(0), wrongData.get(1), wrongData.get(2));
            }
            break;
            default:
                message = null;
        }
        return message;
    }

    private boolean areDataUnique(User user) {
        boolean usernameExist = !usersRepository.existsByUsername(user.getUsername());
        boolean mailExist = !usersRepository.existsByMail(user.getMail());
        boolean numberExist = !usersRepository.existsByPhoneNumber(user.getPhoneNumber());
        if (usernameExist && mailExist && numberExist) {
            return true;
        }
        return false;
    }

    private List<String> getWrongData(User user) {
        List<String> wrongData = new ArrayList<>();
        if (usersRepository.existsByUsername(user.getUsername())) {
            wrongData.add("username");
        }
        if (usersRepository.existsByMail(user.getMail())) {
            wrongData.add("mail");
        }
        if (usersRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            wrongData.add("phone number");
        }
        return wrongData;
    }
}
