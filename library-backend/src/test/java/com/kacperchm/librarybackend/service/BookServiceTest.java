package com.kacperchm.librarybackend.service;

import com.kacperchm.librarybackend.InMemoryRepo.InMemoryBooksRepository;
import com.kacperchm.librarybackend.model.Book;
import com.kacperchm.librarybackend.model.dto.BookDto;
import com.kacperchm.librarybackend.model.filter.BookFilter;
import com.kacperchm.librarybackend.model.responses.BookResponse;
import com.kacperchm.librarybackend.repository.BooksRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BookServiceTest {
    BooksRepository repository = new InMemoryBooksRepository();

    @Test
    public void should_save_book_when_all_required_fields_are_correct_filled_in() {
        // given
        BookDto book = new BookDto("Stephen King","Lśnienie",2009,"Horror");
        BookService bookService = new BookService(repository);
        // when
        BookResponse response = bookService.addBook(book);
        List<Book> books = repository.findAll();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getMessage()).isEqualTo("Book added successfully");
        assertThat(books.size()).isEqualTo(1);
    }

    @Test
    public void should_return_conflict_response_when_author_and_title_fields_are_not_filled_in_correct() {
        // given
        BookDto book = new BookDto("","",2009,"Horror");
        BookService bookService = new BookService(repository);
        // when
        BookResponse response = bookService.addBook(book);
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getMessage()).isEqualTo("Fields with blank or invalid values: Author, Title.");
    }

    @Test
    public void should_return_conflict_response_when_title_field_is_not_filled_in_correct() {
        // given
        BookDto book = new BookDto("Stephen King","",2009,"Horror");
        BookService bookService = new BookService(repository);
        // when
        BookResponse response = bookService.addBook(book);
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getMessage()).isEqualTo("Field with blank or invalid value: Title.");
    }

    @Test
    public void should_delete_book_with_entered_id() {
        // given
        Book book = new Book(1L,"Stephen King","Lśnienie",2009,"Horror");
        BookService bookService = new BookService(repository);
        repository.save(book);
        // when
        BookResponse response = bookService.deleteBook(book.getId());
        List<Book> books = repository.findAll();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getMessage()).isEqualTo("Book removed successfully");
        assertThat(books.size()).isEqualTo(0);
    }

    @Test
    public void should_return_all_books() {
        // given
        List<Book> bookListToAdd = new ArrayList<>();
        bookListToAdd.add(new Book(0L,"Stephen King","Lśnienie",2009,"Horror"));
        bookListToAdd.add(new Book(1L,"Stephen King","To",2001,"Horror"));
        bookListToAdd.add(new Book(2L,"Stephen King","Smentarz Zwierząt",2003,"Horror"));
        bookListToAdd.add(new Book(3L,"J. K. Rowling","Harry Potter i Kamień Filozoficzny",1998,"Fantasy"));
        bookListToAdd.add(new Book(4L,"J. K. Rowling","Harry Potter i Komnata Tajemnic",2000,"Fantasy"));
        bookListToAdd.add(new Book(5L,"J. K. Rowling","Harry Potter i Więzień Askabanu",2003,"Fantasy"));
        BookService bookService = new BookService(repository);
        repository.saveAll(bookListToAdd);
        // when
        List<Book> books = bookService.getAllBooks();
        // then
        assertThat(books.size()).isEqualTo(6);
        assertThat(books.get(0).getTitle()).isEqualTo("Lśnienie");
        assertThat(books.get(3).getTitle()).isEqualTo("Harry Potter i Kamień Filozoficzny");
        assertThat(books.get(5).getTitle()).isEqualTo("Harry Potter i Więzień Askabanu");
    }

    @Test
    public void should_return_all_categories() {
        // given
        List<Book> bookListToAdd = new ArrayList<>();
        bookListToAdd.add(new Book(0L,"Stephen King","Lśnienie",2009,"Horror"));
        bookListToAdd.add(new Book(1L,"Stephen King","To",2001,"Horror"));
        bookListToAdd.add(new Book(2L,"Stephen King","Smentarz Zwierząt",2003,"Horror"));
        bookListToAdd.add(new Book(3L,"J. K. Rowling","Harry Potter i Kamień Filozoficzny",1998,"Fantasy"));
        bookListToAdd.add(new Book(4L,"J. K. Rowling","Harry Potter i Komnata Tajemnic",2000,"Fantasy"));
        bookListToAdd.add(new Book(5L,"J. K. Rowling","Harry Potter i Więzień Askabanu",2003,"Fantasy"));
        bookListToAdd.add(new Book(6L,"Nicholas Sparks","Najdłuższa podróż",2015,"Romans"));
        bookListToAdd.add(new Book(7L,"Nicholas Sparks","Powrót",2013,"Romans"));
        BookService bookService = new BookService(repository);
        repository.saveAll(bookListToAdd);
        // when
        List<String> categories = bookService.getAllCategories();
        // then
        assertThat(categories.size()).isEqualTo(3);
        assertThat(categories.get(0)).isEqualTo("Fantasy");
        assertThat(categories.get(1)).isEqualTo("Horror");
        assertThat(categories.get(2)).isEqualTo("Romans");
    }

    @Test
    public void should_return_list_of_books_filtered_by_title_author_and_category() {
        // given
        List<Book> bookListToAdd = new ArrayList<>();
        bookListToAdd.add(new Book(0L,"Stephen King","Lśnienie",2009,"Horror"));
        bookListToAdd.add(new Book(0L,"Stephen King","Olśnienie",2009,"Horror"));
        bookListToAdd.add(new Book(1L,"Stephen King","To",2001,"Horror"));
        bookListToAdd.add(new Book(2L,"Stephen King","Smentarz Zwierząt",2003,"Horror"));
        bookListToAdd.add(new Book(3L,"J. K. Rowling","Harry Potter i Kamień Filozoficzny",1998,"Fantasy"));
        bookListToAdd.add(new Book(4L,"J. K. Rowling","Harry Potter i Komnata Tajemnic",2000,"Fantasy"));
        bookListToAdd.add(new Book(5L,"J. K. Rowling","Harry Potter i Więzień Askabanu",2003,"Fantasy"));
        bookListToAdd.add(new Book(6L,"Nicholas Sparks","Najdłuższa podróż",2015,"Romans"));
        bookListToAdd.add(new Book(7L,"Nicholas Sparks","Powrót",2013,"Romans"));
        BookService bookService = new BookService(repository);
        repository.saveAll(bookListToAdd);
        BookFilter filter = new BookFilter("Horror", "King", "nie");
        // when
        List<Book> books = bookService.getFilteredBooks(filter);
        // then
        assertThat(books.size()).isEqualTo(2);
        assertThat(books.get(0).getCategory()).isEqualTo("Horror");
        assertThat(books.get(0).getTitle()).isEqualTo("Lśnienie");
        assertThat(books.get(0).getAuthor()).isEqualTo("Stephen King");
        assertThat(books.get(1).getCategory()).isEqualTo("Horror");
        assertThat(books.get(1).getTitle()).isEqualTo("Olśnienie");
        assertThat(books.get(1).getAuthor()).isEqualTo("Stephen King");
    }

    @Test
    public void should_return_list_of_books_filtered_by_title_and_author() {
        // given
        List<Book> bookListToAdd = new ArrayList<>();
        bookListToAdd.add(new Book(0L,"Stephen King","Lśnienie",2009,"Horror"));
        bookListToAdd.add(new Book(0L,"Stephen King","Olśnienie",2009,"Horror"));
        bookListToAdd.add(new Book(1L,"Stephen King","To",2001,"Horror"));
        bookListToAdd.add(new Book(2L,"Stephen King","Smentarz Zwierząt",2003,"Horror"));
        bookListToAdd.add(new Book(3L,"J. K. Rowling","Harry Potter i Kamień Filozoficzny",1998,"Fantasy"));
        bookListToAdd.add(new Book(4L,"J. K. Rowling","Harry Potter i Komnata Tajemnic",2000,"Fantasy"));
        bookListToAdd.add(new Book(5L,"J. K. Rowling","Harry Potter i Więzień Askabanu",2003,"Fantasy"));
        bookListToAdd.add(new Book(6L,"Nicholas Sparks","Najdłuższa podróż",2015,"Romans"));
        bookListToAdd.add(new Book(7L,"Nicholas Sparks","Powrót",2013,"Romans"));
        BookService bookService = new BookService(repository);
        repository.saveAll(bookListToAdd);
        BookFilter filter = new BookFilter("", "ling", "Potter");
        // when
        List<Book> books = bookService.getFilteredBooks(filter);
        // then
        assertThat(books.size()).isEqualTo(3);
        assertThat(books.get(0).getCategory()).isEqualTo("Fantasy");
        assertThat(books.get(0).getTitle()).isEqualTo("Harry Potter i Kamień Filozoficzny");
        assertThat(books.get(0).getAuthor()).isEqualTo("J. K. Rowling");
        assertThat(books.get(1).getCategory()).isEqualTo("Fantasy");
        assertThat(books.get(1).getTitle()).isEqualTo("Harry Potter i Komnata Tajemnic");
        assertThat(books.get(1).getAuthor()).isEqualTo("J. K. Rowling");
        assertThat(books.get(2).getCategory()).isEqualTo("Fantasy");
        assertThat(books.get(2).getTitle()).isEqualTo("Harry Potter i Więzień Askabanu");
        assertThat(books.get(2).getAuthor()).isEqualTo("J. K. Rowling");
    }

    @Test
    public void should_return_list_of_books_filtered_by_category_and_author() {
        // given
        List<Book> bookListToAdd = new ArrayList<>();
        bookListToAdd.add(new Book(0L,"Stephen King","Lśnienie",2009,"Horror"));
        bookListToAdd.add(new Book(0L,"Stephen King","Olśnienie",2009,"Horror"));
        bookListToAdd.add(new Book(1L,"Stephen King","To",2001,"Horror"));
        bookListToAdd.add(new Book(2L,"Stephen King","Smentarz Zwierząt",2003,"Horror"));
        bookListToAdd.add(new Book(3L,"J. K. Rowling","Harry Potter i Kamień Filozoficzny",1998,"Fantasy"));
        bookListToAdd.add(new Book(4L,"J. K. Rowling","Harry Potter i Komnata Tajemnic",2000,"Fantasy"));
        bookListToAdd.add(new Book(5L,"J. K. Rowling","Harry Potter i Więzień Askabanu",2003,"Fantasy"));
        bookListToAdd.add(new Book(6L,"Nicholas Sparks","Najdłuższa podróż",2015,"Romans"));
        bookListToAdd.add(new Book(7L,"Nicholas Sparks","Powrót",2013,"Romans"));
        BookService bookService = new BookService(repository);
        repository.saveAll(bookListToAdd);
        BookFilter filter = new BookFilter("Romans", "par", "");
        // when
        List<Book> books = bookService.getFilteredBooks(filter);
        // then
        assertThat(books.size()).isEqualTo(2);
        assertThat(books.get(0).getCategory()).isEqualTo("Romans");
        assertThat(books.get(0).getTitle()).isEqualTo("Najdłuższa podróż");
        assertThat(books.get(0).getAuthor()).isEqualTo("Nicholas Sparks");
        assertThat(books.get(1).getCategory()).isEqualTo("Romans");
        assertThat(books.get(1).getTitle()).isEqualTo("Powrót");
        assertThat(books.get(1).getAuthor()).isEqualTo("Nicholas Sparks");
    }

    @Test
    public void should_return_list_of_books_filtered_by_category() {
        // given
        List<Book> bookListToAdd = new ArrayList<>();
        bookListToAdd.add(new Book(0L,"Stephen King","Lśnienie",2009,"Horror"));
        bookListToAdd.add(new Book(1L,"Stephen King","To",2001,"Horror"));
        bookListToAdd.add(new Book(2L,"Stephen King","Smentarz Zwierząt",2003,"Horror"));
        bookListToAdd.add(new Book(3L,"J. K. Rowling","Harry Potter i Kamień Filozoficzny",1998,"Fantasy"));
        bookListToAdd.add(new Book(4L,"J. K. Rowling","Harry Potter i Komnata Tajemnic",2000,"Fantasy"));
        bookListToAdd.add(new Book(5L,"J. K. Rowling","Harry Potter i Więzień Askabanu",2003,"Fantasy"));
        bookListToAdd.add(new Book(6L,"Nicholas Sparks","Najdłuższa podróż",2015,"Romans"));
        bookListToAdd.add(new Book(7L,"Nicholas Sparks","Powrót",2013,"Romans"));
        BookService bookService = new BookService(repository);
        repository.saveAll(bookListToAdd);
        BookFilter filter = new BookFilter("Horror", "", "");
        // when
        List<Book> books = bookService.getFilteredBooks(filter);
        // then
        assertThat(books.size()).isEqualTo(3);
        assertThat(books.get(0).getCategory()).isEqualTo("Horror");
        assertThat(books.get(0).getTitle()).isEqualTo("Lśnienie");
        assertThat(books.get(0).getAuthor()).isEqualTo("Stephen King");
        assertThat(books.get(1).getCategory()).isEqualTo("Horror");
        assertThat(books.get(1).getTitle()).isEqualTo("To");
        assertThat(books.get(1).getAuthor()).isEqualTo("Stephen King");
        assertThat(books.get(2).getCategory()).isEqualTo("Horror");
        assertThat(books.get(2).getTitle()).isEqualTo("Smentarz Zwierząt");
        assertThat(books.get(2).getAuthor()).isEqualTo("Stephen King");
    }
}
