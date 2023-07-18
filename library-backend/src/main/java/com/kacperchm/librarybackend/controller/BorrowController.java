package com.kacperchm.librarybackend.controller;

import com.kacperchm.librarybackend.model.Borrow;
import com.kacperchm.librarybackend.model.responses.BorrowResponse;
import com.kacperchm.librarybackend.service.BorrowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class BorrowController {

    private BorrowService service;

    public BorrowController(BorrowService service) {
        this.service = service;
    }

    @PostMapping("/borrow")
    public ResponseEntity<List<String>> borrowBook(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "bookId") Long bookId){

        BorrowResponse borrowResponse = service.borrowBook(userId, bookId);
        return ResponseEntity
                .status(borrowResponse.getStatus())
                .body(borrowResponse.getMessage());
    }

    @PutMapping("/return")
    public ResponseEntity<List<String>> returnBook(
            @RequestParam(value = "bookId") Long bookId){

        BorrowResponse borrowResponse = service.returnBook(bookId);
        return ResponseEntity
                .status(borrowResponse.getStatus())
                .body(borrowResponse.getMessage());
    }

    @GetMapping("/all-not-returned")
    public List<Borrow> getBorrowDetailsNotReturnedBook () {
        return service.getNotReturnedBorrowedBook();
    }

    @GetMapping("/all-borrows")
    public List<Borrow> getAllBorrowDetails () {
        return service.getAllBorrowedBook();
    }

    @GetMapping("/all-borrows/{userId}")
    public List<Borrow> getAllBorrowDetailsForUser (@PathVariable("userId") Long id) {
        return service.getAllBooksBorrowedByUser(id);
    }

    @GetMapping("/all-not-returned/{userId}")
    public List<Borrow> getBorrowDetailsNotReturnedBookForUser (@PathVariable("userId") Long id) {
        return service.getAllNotReturnedBooksBorrowedByUser(id);
    }

    @GetMapping("/all-returned/{userId}")
    public List<Borrow> getBorrowDetailsReturnedBookForUser (@PathVariable("userId") Long id) {
        return service.getAllReturnedBooksBorrowedByUser(id);
    }
}
