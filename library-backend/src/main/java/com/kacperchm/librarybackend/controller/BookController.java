package com.kacperchm.librarybackend.controller;

import com.kacperchm.librarybackend.service.BookService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private BookService service;

    public BookController(BookService service) {
        this.service = service;
    }
}
