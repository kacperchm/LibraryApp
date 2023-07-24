package com.kacperchm.librarybackend.service;

import com.kacperchm.librarybackend.model.*;
import com.kacperchm.librarybackend.model.responses.BorrowResponse;
import com.kacperchm.librarybackend.repository.BooksRepository;
import com.kacperchm.librarybackend.repository.BorrowRepository;
import com.kacperchm.librarybackend.repository.MembersRepository;
import com.kacperchm.librarybackend.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BorrowServiceTest {

    private BorrowService service;
    private BorrowRepository borrowRepository;
    private BooksRepository booksRepository;
    private UsersRepository usersRepository;
    private MembersRepository membersRepository;

    @BeforeEach
    public void setup() {
        borrowRepository = mock(BorrowRepository.class);
        booksRepository = mock(BooksRepository.class);
        usersRepository = mock(UsersRepository.class);
        membersRepository = mock(MembersRepository.class);
        service = new BorrowService(borrowRepository, usersRepository, booksRepository, membersRepository);
    }

    @Test
    public void should_return_success_message_when_book_has_been_borrowed() {
        // given
        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        User user = new User(10L, "adrew123", "adrew123@gmail.com", "555777666", "andrzej", "ROLE_USER", address1, member1);

        Book book = new Book(3L,"J. K. Rowling","Harry Potter i Kamień Filozoficzny",1998,"Fantasy");

        // mock repository
        when(booksRepository.existsById(book.getId())).thenReturn(true);
        when(booksRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(usersRepository.existsById(user.getId())).thenReturn(true);
        when(usersRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(borrowRepository.save(any(Borrow.class))).thenReturn(null);
        when(usersRepository.save(any(User.class))).thenReturn(null);

        // when
        BorrowResponse response = service.borrowBook(10L,3L);

        // then
        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertEquals("Book has been borrowed", response.getMessage().get(0));
        assertFalse(book.isAvailability());
        assertFalse(user.getLibraryMember().isBlockade());
    }

    @Test
    public void should_return_blockade_message_when_user_have_too_many_books() {
        // given
        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        member1.setNumOfBorrowedBooks(5);
        member1.setBlockade(true);
        User user = new User(10L, "adrew123", "adrew123@gmail.com", "555777666", "andrzej", "ROLE_USER", address1, member1);

        Book book = new Book(3L,"J. K. Rowling","Harry Potter i Kamień Filozoficzny",1998,"Fantasy");


        // mock repository
        when(booksRepository.existsById(book.getId())).thenReturn(true);
        when(booksRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(usersRepository.existsById(user.getId())).thenReturn(true);
        when(usersRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(borrowRepository.save(any(Borrow.class))).thenReturn(null);
        when(usersRepository.save(any(User.class))).thenReturn(null);

        // when
        BorrowResponse response = service.borrowBook(10L,3L);

        // then
        assertEquals(HttpStatus.CONFLICT, response.getStatus());
        assertEquals("User has blockade", response.getMessage().get(0));
    }

    @Test
    public void should_return_fail_message_when_book_is_not_available() {
        // given
        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        User user = new User(10L, "adrew123", "adrew123@gmail.com", "555777666", "andrzej", "ROLE_USER", address1, member1);

        Book book = new Book(3L,"J. K. Rowling","Harry Potter i Kamień Filozoficzny",1998,"Fantasy");
        book.setAvailability(false);

        // mock repository
        when(booksRepository.existsById(book.getId())).thenReturn(true);
        when(booksRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(usersRepository.existsById(user.getId())).thenReturn(true);
        when(usersRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(borrowRepository.save(any(Borrow.class))).thenReturn(null);
        when(usersRepository.save(any(User.class))).thenReturn(null);

        // when
        BorrowResponse response = service.borrowBook(10L,3L);

        // then
        assertEquals(HttpStatus.CONFLICT, response.getStatus());
        assertEquals("Book is not available", response.getMessage().get(0));
    }

    @Test
    public void should_return_fail_messages_when_book_and_user_are_null() {
        // given
        User user = null;

        Book book = null;

        // mock repository
        when(booksRepository.existsById(3L)).thenReturn(false);
        when(booksRepository.findById(3L)).thenReturn(Optional.ofNullable(book));
        when(usersRepository.existsById(10L)).thenReturn(false);
        when(usersRepository.findById(10L)).thenReturn(Optional.ofNullable(user));
        when(borrowRepository.save(any(Borrow.class))).thenReturn(null);
        when(usersRepository.save(any(User.class))).thenReturn(null);

        // when
        BorrowResponse response = service.borrowBook(10L,3L);

        // then
        assertEquals(HttpStatus.CONFLICT, response.getStatus());
        assertEquals("User does not exist", response.getMessage().get(0));
        assertEquals("Book does not exist", response.getMessage().get(1));
    }

    @Test
    public void should_return_fail_messages_when_borrow_id_not_exist() {
        // mock repository
        when(borrowRepository.existsById(8L)).thenReturn(false);

        // when
        BorrowResponse response = service.returnBook(8L);

        // then
        assertEquals(HttpStatus.CONFLICT, response.getStatus());
        assertEquals("There is no such borrow ID", response.getMessage().get(0));
    }

    @Test
    public void should_return_list_of_not_returned_books() {
        // given
        Book book1 = new Book(0L,"Stephen King","Lśnienie",2009,"Horror");
        Book book2 = new Book(1L,"Stephen King","To",2001,"Horror");
        Book book3 = new Book(2L,"Stephen King","Smentarz Zwierząt",2003,"Horror");
        Book book4 = new Book(3L,"J. K. Rowling","Harry Potter i Kamień Filozoficzny",1998,"Fantasy");
        Book book5 = new Book(4L,"J. K. Rowling","Harry Potter i Komnata Tajemnic",2000,"Fantasy");
        Book book6 = new Book(5L,"J. K. Rowling","Harry Potter i Więzień Askabanu",2003,"Fantasy");

        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        User user = new User(10L, "adrew123", "adrew123@gmail.com", "555777666", "andrzej", "ROLE_USER", address1, member1);

        List<Borrow> borrowList = new ArrayList<>();
        Borrow borrow1 = new Borrow(user.getLibraryMember(), book1);
        Borrow borrow2 = new Borrow(user.getLibraryMember(), book2);
        borrow2.setReturned(true);
        Borrow borrow3 = new Borrow(user.getLibraryMember(), book3);
        Borrow borrow4 = new Borrow(user.getLibraryMember(), book4);
        Borrow borrow5 = new Borrow(user.getLibraryMember(), book5);
        borrow5.setReturned(true);
        Borrow borrow6 = new Borrow(user.getLibraryMember(), book6);
        borrow6.setReturned(true);

        borrowList.add(borrow1);
        borrowList.add(borrow2);
        borrowList.add(borrow3);
        borrowList.add(borrow4);
        borrowList.add(borrow5);
        borrowList.add(borrow6);

        // mock repository
        when(borrowRepository.findAll()).thenReturn(borrowList);

        // when
        List<Borrow> borrows = service.getNotReturnedBorrowedBook();

        // then
        assertEquals(3, borrows.size());
        assertEquals(borrows.get(0).getBook(), book1);
        assertEquals(borrows.get(1).getBook(), book3);
        assertEquals(borrows.get(2).getBook(), book4);
    }
}
