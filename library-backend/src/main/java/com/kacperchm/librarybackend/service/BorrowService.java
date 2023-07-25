package com.kacperchm.librarybackend.service;

import com.kacperchm.librarybackend.model.Book;
import com.kacperchm.librarybackend.model.Borrow;
import com.kacperchm.librarybackend.model.LibraryMember;
import com.kacperchm.librarybackend.model.User;
import com.kacperchm.librarybackend.model.responses.BorrowResponse;
import com.kacperchm.librarybackend.repository.BooksRepository;
import com.kacperchm.librarybackend.repository.BorrowRepository;
import com.kacperchm.librarybackend.repository.MembersRepository;
import com.kacperchm.librarybackend.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BorrowService {

    private BorrowRepository borrowRepository;
    private UsersRepository usersRepository;
    private BooksRepository booksRepository;
    private MembersRepository membersRepository;

    public BorrowService(BorrowRepository borrowRepository, UsersRepository usersRepository, BooksRepository booksRepository, MembersRepository membersRepository) {
        this.borrowRepository = borrowRepository;
        this.usersRepository = usersRepository;
        this.booksRepository = booksRepository;
        this.membersRepository = membersRepository;
    }

    public BorrowResponse borrowBook(Long userId, Long bookId) {
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
            Borrow borrow = new Borrow(user.getLibraryMember(),book);
            borrowRepository.save(borrow);
            user.getLibraryMember().setNumOfBorrowedBooks(user.getLibraryMember().getNumOfBorrowedBooks() + 1);
            if(user.getLibraryMember().getNumOfBorrowedBooks() >= 5) {
                user.getLibraryMember().setBlockade(true);
            }
            book.setAvailability(false);
            booksRepository.save(book);
            usersRepository.save(user);

            messages.add("Book has been borrowed");

            return new BorrowResponse(messages, HttpStatus.CREATED);
        }
        if(user == null) {
            messages.add("User does not exist");
        } else if(blockade) {
            messages.add("User has blockade");
        }

        if(book == null) {
            messages.add("Book does not exist");
        } else if(!bookStatus) {
            messages.add("Book is not available");
        }

        return new BorrowResponse(messages, HttpStatus.CONFLICT);
    }

    public BorrowResponse returnBook(Long borrowId) {
        List<String> messages = new ArrayList<>();
        if(borrowRepository.existsById(borrowId)) {
            Borrow borrow = borrowRepository.findById(borrowId).get();
            borrow.setReturned(true);
            Book book = borrow.getBook();
            book.setAvailability(true);
            booksRepository.save(book);

            LibraryMember member = borrow.getMember();
            member.setNumOfBorrowedBooks(member.getNumOfBorrowedBooks() - 1);
            int ownedBooks = member.getNumOfBorrowedBooks();
            if(ownedBooks < 5 && member.isBlockade()) {
                member.setBlockade(false);
            }
            membersRepository.save(member);

            messages.add("Book has been returned");
            return new BorrowResponse(messages, HttpStatus.OK);
        }
        messages.add("There is no such borrow ID");
        return new BorrowResponse(messages, HttpStatus.CONFLICT);
    }

    public List<Borrow> getNotReturnedBorrowedBook() {
        List<Borrow> borrowedList = new ArrayList<>();
        borrowRepository.findAll().forEach(borrowedBook -> {
            if(!borrowedBook.isReturned()) {
                borrowedList.add(borrowedBook);
            }
        });
        return  borrowedList;
    }

    public List<Borrow> getAllBorrowedBook() {
        return borrowRepository.findAll();
    }

    public List<Borrow> getAllBooksBorrowedByUser(Long memberId) {
        List<Borrow> borrowedList = new ArrayList<>();
        List<Borrow> allBorrows = borrowRepository.findAll();
        allBorrows.forEach(b -> {
            if(b.getMember().getId() == memberId) {
                borrowedList.add(b);
            }
        });
        return  borrowedList;
    }

    public List<Borrow> getAllNotReturnedBooksBorrowedByUser(Long memberId) {
        List<Borrow> borrowedList = new ArrayList<>();
        borrowRepository.findAll().forEach(borrowedBook -> {
            if(borrowedBook.getMember().getId() == memberId) {
                if (!borrowedBook.isReturned()) {
                    borrowedList.add(borrowedBook);
                }
            }
        });
        return  borrowedList;
    }

    public List<Borrow> getAllReturnedBooksBorrowedByUser(Long memberId) {
        List<Borrow> borrowedList = new ArrayList<>();
        borrowRepository.findAll().forEach(borrowedBook -> {
            if(borrowedBook.getMember().getId() == memberId) {
                if (borrowedBook.isReturned()) {
                    borrowedList.add(borrowedBook);
                }
            }
        });
        return  borrowedList;
    }

}
