package com.kacperchm.librarybackend.model;

public class UserToTransfer {
    private Long id;
    private String username;
    private String mail;
    private String phoneNumber;
    private String password;
    private String role;
    private Long addressId;
    private String city;
    private String zipCode;
    private String street;
    private String houseNumber;
    private Long memberId;
    private String name;
    private String surname;
    private int numOfBorrowedBooks;
    private boolean blockade;

    public UserToTransfer(Long id, String username, String mail, String phoneNumber, String password, String role, Long addressId, String city, String zipCode, String street, String houseNumber, Long memberId, String name, String surname, int numOfBorrowedBooks, boolean blockade) {
        this.id = id;
        this.username = username;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
        this.addressId = addressId;
        this.city = city;
        this.zipCode = zipCode;
        this.street = street;
        this.houseNumber = houseNumber;
        this.memberId = memberId;
        this.name = name;
        this.surname = surname;
        this.numOfBorrowedBooks = numOfBorrowedBooks;
        this.blockade = blockade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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
}
