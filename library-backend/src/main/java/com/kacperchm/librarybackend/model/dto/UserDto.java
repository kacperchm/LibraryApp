package com.kacperchm.librarybackend.model.dto;

import com.kacperchm.librarybackend.model.Borrow;

import java.util.Collections;
import java.util.List;

public class UserDto {
    private String username;
    private String password;
    private String mail;
    private String phoneNumber;
    private String role;
    private String name;
    private String surname;
    private int numOfBorrowedBooks;
    private boolean blockade;
    private List<Borrow> borrowList;
    private String city;
    private String zipCode;
    private String street;
    private String houseNumber;

    public UserDto(String username, String password, String mail, String phoneNumber, String role, String name, String surname,
                   String city, String zipCode, String street, String houseNumber) {
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.numOfBorrowedBooks = 0;
        this.blockade = false;
        this.borrowList = Collections.emptyList();
        this.city = city;
        this.zipCode = zipCode;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public UserDto(String username, String password, String mail, String phoneNumber, String role, String name,
                   String surname, int numOfBorrowedBooks, boolean blockade, List<Borrow> borrowList, String city, String zipCode, String street, String houseNumber) {
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.numOfBorrowedBooks = numOfBorrowedBooks;
        this.blockade = blockade;
        this.borrowList = borrowList;
        this.city = city;
        this.zipCode = zipCode;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getNumOfBorrowedBooks() {
        return numOfBorrowedBooks;
    }

    public void setNumOfBorrowedBooks(int numOfBorrowedBooks) {
        this.numOfBorrowedBooks = numOfBorrowedBooks;
    }

    public boolean isBlockade() {
        return blockade;
    }

    public void setBlockade(boolean blockade) {
        this.blockade = blockade;
    }

    public List<Borrow> getBorrowList() {
        return borrowList;
    }

    public void setBorrowList(List<Borrow> borrowList) {
        this.borrowList = borrowList;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
}
