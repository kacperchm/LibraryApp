package com.kacperchm.librarybackend.mapper;

import com.kacperchm.librarybackend.model.Address;
import com.kacperchm.librarybackend.model.LibraryMember;
import com.kacperchm.librarybackend.model.User;
import com.kacperchm.librarybackend.model.dto.UserDto;

public class UserMapper {
    public static User mapToUser(UserDto dto) {
        Address address = new Address(dto.getCity(), dto.getZipCode(),
                dto.getStreet(), dto.getHouseNumber());
        LibraryMember member = new LibraryMember(dto.getName(), dto.getSurname());
        User user = new User(dto.getUsername(), dto.getMail(), dto.getPhoneNumber(),
                dto.getPassword(), dto.getRole(), address,member);
        return user;
    }

    public static UserDto mapToUserDto(User user) {
        UserDto dto = new UserDto(user.getUsername(), user.getPassword(), user.getMail(),
                user.getPhoneNumber(), user.getRole(), user.getLibraryMember().getName(),
                user.getLibraryMember().getSurname(),
                user.getLibraryMember().getNumOfBorrowedBooks(),
                user.getLibraryMember().isBlockade(),
                user.getLibraryMember().getBorrowedBookList(),
                user.getAddress().getCity(), user.getAddress().getZipCode(),
                user.getAddress().getStreet(), user.getAddress().getHouseNumber());
        return dto;
    }
}
