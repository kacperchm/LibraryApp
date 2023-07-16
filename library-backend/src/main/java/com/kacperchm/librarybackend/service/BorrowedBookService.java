package com.kacperchm.librarybackend.service;

import com.kacperchm.librarybackend.repository.BooksRepository;
import com.kacperchm.librarybackend.repository.BorrowedBooksRepository;
import com.kacperchm.librarybackend.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class BorrowedBookService {

    private BorrowedBooksRepository borrowedBooksRepository;
    private UsersRepository usersRepository;
    private BooksRepository booksRepository;

    public BorrowedBookService(BorrowedBooksRepository borrowedBooksRepository, UsersRepository usersRepository, BooksRepository booksRepository) {
        this.borrowedBooksRepository = borrowedBooksRepository;
        this.usersRepository = usersRepository;
        this.booksRepository = booksRepository;
    }

    public String borrowBook(Long userId, Long bookId) {
        if(booksRepository.existsById(userId)) {

        }
    }
}
