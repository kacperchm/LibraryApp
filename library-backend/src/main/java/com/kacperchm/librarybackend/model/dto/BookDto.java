package com.kacperchm.librarybackend.model.dto;

public class BookDto {
    private String author;
    private String title;
    private int publicationYear;
    private String category;

    private boolean availability;

    public BookDto(String author, String title, int publicationYear, String category) {
        this.author = author;
        this.title = title;
        this.publicationYear = publicationYear;
        this.category = category;
        this.availability = true;
    }

    public BookDto(String author, String title, int publicationYear, String category, boolean availability) {
        this.author = author;
        this.title = title;
        this.publicationYear = publicationYear;
        this.category = category;
        this.availability = availability;
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
}
