package com.kacperchm.librarybackend.model;

import java.time.LocalDateTime;

public class BorrowToTransfer {
    private Long id;
    private String author;
    private String title;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private boolean returned;

    public BorrowToTransfer() {
    }

    public BorrowToTransfer(Long id, String author, String title, LocalDateTime borrowDate, LocalDateTime returnDate, boolean returned) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.returned = returned;
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

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDateTime borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}
