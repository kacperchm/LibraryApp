package com.kacperchm.librarybackend.service;

import com.kacperchm.librarybackend.model.Book;
import com.kacperchm.librarybackend.model.BorrowedBook;
import com.kacperchm.librarybackend.model.User;
import com.kacperchm.librarybackend.model.responses.BookResponse;
import com.kacperchm.librarybackend.model.responses.BorrowedResponse;
import com.kacperchm.librarybackend.repository.BooksRepository;
import com.kacperchm.librarybackend.repository.BorrowedBooksRepository;
import com.kacperchm.librarybackend.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public BorrowedResponse borrowBook(Long userId, Long bookId) {
        List<String> messages = new ArrayList<>();
        Book book = null;
        boolean bookStatus = false;
        User user = null;
        boolean blockade = true;
        if(booksRepository.existsById(bookId)) {
            book = booksRepository.findById(bookId).get();
            bookStatus = book.isAvailability();
        }
        if(usersRepository.existsById(userId)) {
            user = usersRepository.findById(userId).get();
            blockade = user.getLibraryMember().isBlockade();
        }
        if(book != null && bookStatus && user != null && !blockade) {
            BorrowedBook borrowedBook = new BorrowedBook(user.getLibraryMember(),book);
            borrowedBooksRepository.save(borrowedBook);
            user.getLibraryMember().setNumOfBorrowedBooks(user.getLibraryMember().getNumOfBorrowedBooks() + 1);
            if(user.getLibraryMember().getNumOfBorrowedBooks() >= 5) {
                user.getLibraryMember().setBlockade(true);
            }
            book.setAvailability(false);
            booksRepository.save(book);
            usersRepository.save(user);

            messages.add("Book has been borrowed");

            return new BorrowedResponse(messages, HttpStatus.CREATED);
        }
        if(user == null) {
            messages.add("User does not exist");
        }
        if(book == null) {
            messages.add("Book does not exist");
        }
        if(!bookStatus) {
            messages.add("Book is not available");
        }
        if(blockade) {
            messages.add("User has blocade");
        }
        return new BorrowedResponse(messages, HttpStatus.CONFLICT);
    }

    public String returnBook(Long borrowId) {
            return "";
    }

}
