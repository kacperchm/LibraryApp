package com.kacperchm.librarybackend.controller;

import com.kacperchm.librarybackend.model.Borrow;
import com.kacperchm.librarybackend.model.BorrowToTransfer;
import com.kacperchm.librarybackend.model.responses.BorrowResponse;
import com.kacperchm.librarybackend.service.BorrowService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<BorrowToTransfer> borrowBook(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "bookId") Long bookId){

        BorrowToTransfer borrow = service.borrowBook(userId, bookId);
        if(borrow.getId() != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(borrow);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(borrow);
        }
    }

    @PostMapping("/borrow/return")
    public ResponseEntity<BorrowToTransfer> returnBook(
            @RequestParam() Long borrowId){
        BorrowToTransfer borrow = service.returnBook(borrowId);
        if(borrow.getId() != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(borrow);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(borrow);
        }
    }

    @GetMapping("/all-not-returned")
    public List<Borrow> getBorrowDetailsNotReturnedBook () {
        return service.getNotReturnedBorrowedBook();
    }

    @GetMapping("/all-borrows")
    public List<Borrow> getAllBorrowDetails () {
        return service.getAllBorrowedBook();
    }

    @GetMapping("/all/borrows/{memberId}")
    public ResponseEntity<List<BorrowToTransfer>> getAllBorrowDetailsForUser (@PathVariable("memberId") Long id,
                                                                              @RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "5") int limit,
                                                                              @RequestParam(defaultValue = "DESC") String sort,
                                                                              @RequestParam(defaultValue = "returnDate") String order) {
        List<BorrowToTransfer> borrowList = service.getAllBooksBorrowedByUser(page, limit, sort, order, id);
        if (borrowList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(borrowList);
    }

    @GetMapping("/all/borrows/size/{memberId}")
    public ResponseEntity<Integer> getQuantityOfBooks(@PathVariable("memberId") Long id) {
        int size = service.getQuantityOfBooksBorrowedByUser(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(size);
    }

    @GetMapping("/all/notreturned/{userId}")
    public List<Borrow> getBorrowDetailsNotReturnedBookForUser (@PathVariable("userId") Long id) {
        return service.getAllNotReturnedBooksBorrowedByUser(id);
    }

    @GetMapping("/all/returned/{userId}")
    public List<Borrow> getBorrowDetailsReturnedBookForUser (@PathVariable("userId") Long id) {
        return service.getAllReturnedBooksBorrowedByUser(id);
    }
}
