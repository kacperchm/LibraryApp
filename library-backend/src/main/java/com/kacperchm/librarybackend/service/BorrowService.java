package com.kacperchm.librarybackend.service;

import com.kacperchm.librarybackend.mapper.BorrowMapper;
import com.kacperchm.librarybackend.model.*;
import com.kacperchm.librarybackend.model.filter.UserFilter;
import com.kacperchm.librarybackend.model.responses.BorrowResponse;
import com.kacperchm.librarybackend.repository.BooksRepository;
import com.kacperchm.librarybackend.repository.BorrowRepository;
import com.kacperchm.librarybackend.repository.MembersRepository;
import com.kacperchm.librarybackend.repository.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public BorrowToTransfer borrowBook(Long userId, Long bookId) {
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

            return BorrowMapper.mapToBorrowToTransfer(borrow);
        }

        return new BorrowToTransfer();
    }

    public BorrowToTransfer returnBook(Long borrowId) {
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

            return BorrowMapper.mapToBorrowToTransfer(borrow);
        }
        return new BorrowToTransfer();
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

    public List<BorrowToTransfer> getAllBooksBorrowedByUser(int page, int limit, String sort, String order, Long memberId) {
        List<BorrowToTransfer> borrowedList = null;

        sort = sort.toUpperCase();
        Sort.Direction direction;
        if (sort.equals("ASC")) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, order));

        Page<Borrow> allBorrows = borrowRepository.findByMemberId(memberId, pageable);
        if(allBorrows.getSize() > 0) {
            borrowedList = new ArrayList<>();
            for (Borrow b: allBorrows.getContent()) {
                borrowedList.add(BorrowMapper.mapToBorrowToTransfer(b));
            }
        }
        return  borrowedList;
    }

    public int getQuantityOfBooksBorrowedByUser(Long id) {
        List<Borrow> borrows;
        borrows = borrowRepository.findByMemberId(id);
        return borrows.size();
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
