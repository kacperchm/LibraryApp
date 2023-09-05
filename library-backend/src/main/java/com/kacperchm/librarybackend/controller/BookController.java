package com.kacperchm.librarybackend.controller;

import com.kacperchm.librarybackend.model.Book;
import com.kacperchm.librarybackend.model.filter.BookFilter;
import com.kacperchm.librarybackend.model.responses.BookResponse;
import com.kacperchm.librarybackend.service.BookService;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book bookResponse = service.addBook(book);
        if(bookResponse.getId() != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(bookResponse);
        }
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(bookResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long id) {
        BookResponse bookResponse = service.deleteBook(id);
        return ResponseEntity
                .status(bookResponse.getStatus())
                .body(bookResponse.getMessage());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "ASC") String sort,
            @RequestParam(defaultValue = "title") String order
            ) {
        ResponseEntity<List<Book>> response;
        List<Book> books = service.getAllBooks(page, limit, sort, order);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Total-Count", String.valueOf(service.getQuantityOfBooks()));
        response = ResponseEntity.status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(books);
        return response;
    }

    @GetMapping("/size")
    public ResponseEntity<Integer> getQuantityOfBooks() {
         int size = service.getQuantityOfBooks();
        return ResponseEntity.status(HttpStatus.OK)
                .body(size);
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

    @GetMapping("/filter")
    public ResponseEntity<List<Book>> getFilteredBooks(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "5") int limit,
                                                       @RequestParam(defaultValue = "ASC") String sort,
                                                       @RequestParam(defaultValue = "title") String order,
                                                       @RequestParam(defaultValue = "") String filter,
                                                       @RequestParam(defaultValue = "") String category) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getFilteredBooks(page, limit, sort, order, new BookFilter(category, filter, filter)));
    }

    @GetMapping("/size-filtered")
    public ResponseEntity<Integer> getQuantityOfFilteredBooks(
            @RequestParam String filter,
            @RequestParam String category
    ) {
        int size = service.getQuantityOfFilteredBooks(new BookFilter(category, filter, filter));
        return ResponseEntity.status(HttpStatus.OK)
                .body(size);
    }
}
