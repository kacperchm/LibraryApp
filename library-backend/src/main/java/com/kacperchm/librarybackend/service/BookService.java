package com.kacperchm.librarybackend.service;

import com.kacperchm.librarybackend.model.Book;
import com.kacperchm.librarybackend.model.filter.BookFilter;
import com.kacperchm.librarybackend.model.responses.BookResponse;
import com.kacperchm.librarybackend.repository.BooksRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class BookService {

    private BooksRepository repository;

    public BookService(BooksRepository repository) {
        this.repository = repository;
    }

    public Book addBook(Book book) {
        Book addedBook = new Book(book.getAuthor(), book.getTitle(), book.getPublicationYear(), book.getCategory());
        int sizeBeforeAdd = repository.findAll().size();
        try {
            if (areAllRequiredFieldsFilledInCorrect(addedBook)) {
                Book retBook = repository.save(addedBook);
                int sizeAfterAdd = repository.findAll().size();
                if (sizeAfterAdd > sizeBeforeAdd) {
                    return retBook;
                }

            }
        } catch (Exception e) {
            return new Book();
        }
        return new Book();
    }

    private boolean areAllRequiredFieldsFilledInCorrect(Book book) {
        if (isStringCorrect(book.getAuthor())) {
            if (isStringCorrect(book.getCategory())) {
                if (isStringCorrect(book.getTitle())) {
                    if (book.getPublicationYear() <= LocalDateTime.now().getYear()) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public BookResponse deleteBook(long id) {
        String message = "";
        if (repository.existsById(id)) {
            repository.deleteById(id);
            message = "Book removed successfully";
        } else {
            message = "Book does not exist";
        }
        return new BookResponse(message, HttpStatus.OK);
    }

    public int getQuantityOfBooks() {
        List<Book> books = repository.findAll();
        return books.size();
    }

    public List<Book> getAllBooks(int page, int limit, String sort, String order) {
        sort = sort.toUpperCase();
        Sort.Direction direction;
        if (sort.equals("ASC")) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, order));
        Page<Book> books = repository.findAll(pageable);
        return books.getContent();
    }

    public Book getBook(long id) {
        Book book = null;
        if (repository.existsById(id)) {
            book = repository.findById(id).get();
        }
        return book;
    }

    public List<String> getAllCategories() {
        Set<String> allCategories = new TreeSet<>();
        allCategories.add("All");
        List<Book> books = repository.findAll();
        for (Book b : books) {
            allCategories.add(b.getCategory());
        }
        return allCategories.stream().toList();
    }

    public List<Book> getFilteredBooks(int page, int limit, String sort, String order, BookFilter filter) {
        Page<Book> books;
        if (!isStringCorrect(filter.getTitle())) {
            filter.setTitle("");
            if (!isStringCorrect(filter.getAuthor())) {
                filter.setAuthor("");
                if (!isStringCorrect(filter.getCategory())) {
                    filter.setCategory("");
                }
            }
        }

        sort = sort.toUpperCase();
        Sort.Direction direction;
        if (sort.equals("ASC")) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, order));

        if (filter.getCategory().equals("All") && !filter.getAuthor().equals("")) {
            books = repository.findBooksByTitleAndAuthor(filter.getTitle(), filter.getAuthor(), pageable);
        } else if (!filter.getCategory().equals("All") && filter.getAuthor().equals("")) {
            books = repository.findBooksByCategory(filter.getCategory(), pageable);
        } else if (filter.getCategory().equals("All") && filter.getAuthor().equals("")) {
            books = repository.findAll(pageable);
        } else {
            books = repository.findBooksByCategoryTitleAndAuthor(filter.getCategory(), filter.getTitle(), filter.getAuthor(), pageable);
        }
        return books.getContent();
    }

    public int getQuantityOfFilteredBooks(BookFilter filter) {
        List<Book> books;
        if (filter.getCategory().equals("All") && !filter.getAuthor().equals("")) {
            books = repository.findBooksByFilterWithoutCategory(filter.getTitle(), filter.getAuthor());
        } else if (!filter.getCategory().equals("All") && filter.getAuthor().equals("")) {
            books = repository.findBooksByFilterWithoutAuthorAndTitle(filter.getCategory());
        } else if (filter.getCategory().equals("All") && filter.getAuthor().equals("")) {
            books = repository.findAll();
        } else {
            books = repository.findBooksByFilter(filter.getCategory(), filter.getTitle(), filter.getAuthor());
        }
        return books.size();
    }

    private boolean isStringCorrect(String strToVerify) {
        if (strToVerify.isBlank() || strToVerify == null) {
            return false;
        }
        return true;
    }
}
