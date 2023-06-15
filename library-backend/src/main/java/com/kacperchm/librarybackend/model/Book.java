package com.kacperchm.librarybackend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(schema = "Library", name = "Books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    private String title;
    private int publicationYear;
    private String category;

    @OneToMany(mappedBy = "book")
    private List<BorrowedBook> borrowedList;

    private boolean availability;

    public Book() {
    }

    public Book(String author, String title, int publicationYear, String category) {
        this.author = author;
        this.title = title;
        this.publicationYear = publicationYear;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public List<BorrowedBook> getBorrowedList() {
        return borrowedList;
    }

    public void setBorrowedList(List<BorrowedBook> borrowedList) {
        this.borrowedList = borrowedList;
    }
}
