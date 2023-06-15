package com.kacperchm.librarybackend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(schema = "Library", name = "Members")
public class LibraryMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private int numOfBorrowedBooks;
    private boolean blockade;

    @OneToMany(mappedBy = "member")
    private List<BorrowedBook> borrowedBookList;

    public LibraryMember() {
    }

    public LibraryMember(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.numOfBorrowedBooks = 0;
        this.blockade = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<BorrowedBook> getBorrowedBookList() {
        return borrowedBookList;
    }

    public void setBorrowedBookList(List<BorrowedBook> borrowedBookList) {
        this.borrowedBookList = borrowedBookList;
    }
}
