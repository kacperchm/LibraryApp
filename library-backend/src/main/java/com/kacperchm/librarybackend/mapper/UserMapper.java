package com.kacperchm.librarybackend.mapper;

import com.kacperchm.librarybackend.model.User;
import com.kacperchm.librarybackend.model.UserToTransfer;

public class UserMapper {
    public static UserToTransfer mapToUserToTransfer(User user) {
        return new UserToTransfer(
                user.getId(),
                user.getUsername(),
                user.getMail(),
                user.getPhoneNumber(),
                user.getPassword(),
                user.getRole(),
                user.getAddress().getId(),
                user.getAddress().getCity(),
                user.getAddress().getZipCode(),
                user.getAddress().getStreet(),
                user.getAddress().getHouseNumber(),
                user.getLibraryMember().getId(),
                user.getLibraryMember().getName(),
                user.getLibraryMember().getSurname(),
                user.getLibraryMember().getNumOfBorrowedBooks(),
                user.getLibraryMember().isBlockade()
                );
    }
}
