package com.kacperchm.librarybackend.model.filter;

public class UserFilter {
    private String username;
    private String mail;
    private String phoneNumber;

    public UserFilter(String username, String mail, String phoneNumber) {
        this.username = username;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
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
}
