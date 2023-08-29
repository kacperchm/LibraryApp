package com.kacperchm.librarybackend.controller;

import com.kacperchm.librarybackend.model.Book;
import com.kacperchm.librarybackend.model.filter.BookFilter;
import com.kacperchm.librarybackend.model.responses.BookResponse;
import com.kacperchm.librarybackend.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        BookResponse bookResponse = service.addBook(book);
        return ResponseEntity
                .status(bookResponse.getStatus())
                .body(bookResponse.getMessage());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long id) {
        BookResponse bookResponse = service.deleteBook(id);
        return ResponseEntity
                .status(bookResponse.getStatus())
                .body(bookResponse.getMessage());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") Long id) {
        ResponseEntity<Book> response;
        Book book = service.getBook(id);
        if(book != null) {
            response = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(book);
        } else {
            response = ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return response;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getAllCategories());
    }

    @PostMapping("/filter")
    public ResponseEntity<List<Book>> getFilteredBooks(@RequestBody BookFilter bookFilter) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getFilteredBooks(bookFilter));
    }
}
