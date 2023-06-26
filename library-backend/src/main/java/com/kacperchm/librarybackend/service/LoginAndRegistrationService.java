package com.kacperchm.librarybackend.service;

import com.kacperchm.librarybackend.model.responses.RegistrationResponse;
import com.kacperchm.librarybackend.model.User;
import com.kacperchm.librarybackend.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginAndRegistrationService {

    private UsersRepository usersRepository;

    public LoginAndRegistrationService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public RegistrationResponse registerUser(User user) {
        try {
            if (areDataUnique(user)) {
                User savedUser = usersRepository.save(user);
                if (savedUser.getId() > 0) {
                    return new RegistrationResponse("User create successfully",HttpStatus.CREATED);
                }
            }
        } catch (Exception e) {
            return new RegistrationResponse("Failed to create user. Server-side error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new RegistrationResponse(getWrongMessage(user),HttpStatus.CONFLICT);
    }

    public String getWrongMessage(User user) {
        List<String> wrongData = getWrongData(user);
        String message;
        switch (wrongData.size()) {
            case 1: {
                message = String.format("User with this %s already exists",wrongData.get(0));
            } break;
            case 2: {
                message = String.format("User with this %s and %s already exists",wrongData.get(0),wrongData.get(1));
            } break;
            case 3: {
                message = String.format("User with this %s, %s and %s already exists",wrongData.get(0), wrongData.get(1) ,wrongData.get(2));
            } break;
            default:
                message = null;
        }
        return message;
    }

    private boolean areDataUnique(User user) {
        boolean usernameExist = usersRepository.existsByUsername(user.getUsername());
        boolean mailExist = usersRepository.existsByMail(user.getMail());
        boolean numberExist = usersRepository.existsByPhoneNumber(user.getPhoneNumber());
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
        if (usersRepository.existsByMail(user.getMail())){
            wrongData.add("mail");
        }
        if (usersRepository.existsByPhoneNumber(user.getPhoneNumber())){
            wrongData.add("phone number");
        }
        return wrongData;
    }
}
