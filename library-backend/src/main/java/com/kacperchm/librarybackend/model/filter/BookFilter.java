package com.kacperchm.librarybackend.model.filter;

public class BookFilter {
    private String category;
    private String author;
    private String title;

    public BookFilter(String category, String author, String title) {
        this.category = category;
        this.author = author;
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
}
