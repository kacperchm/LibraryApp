package com.kacperchm.librarybackend.service;

import com.kacperchm.librarybackend.mapper.BookMapper;
import com.kacperchm.librarybackend.model.Book;
import com.kacperchm.librarybackend.model.dto.BookDto;
import com.kacperchm.librarybackend.model.filter.BookFilter;
import com.kacperchm.librarybackend.model.responses.BookResponse;
import com.kacperchm.librarybackend.repository.BooksRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class BookService {

    private BooksRepository repository;

    public BookService(BooksRepository repository) {
        this.repository = repository;
    }

    public BookResponse addBook(BookDto bookDto) {
        Book book = BookMapper.mapToBook(bookDto);
        int sizeBeforeAdd = repository.findAll().size();
        try {
            if (areAllRequiredFieldsFilledInCorrect(book)) {
                repository.save(book);
                int sizeAfterAdd = repository.findAll().size();
                if (sizeAfterAdd > sizeBeforeAdd) {
                    return new BookResponse("Book added successfully", HttpStatus.CREATED);
                }

            }
        } catch (Exception e) {
            return new BookResponse("Failed to add book. Server-side error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new BookResponse(getRequiredFieldsErrorMessage(book), HttpStatus.CONFLICT);
    }

    private String getRequiredFieldsErrorMessage(Book book) {
        List<String> fieldsList = new ArrayList<>();
        String message = "";
        if (!isStringCorrect(book.getAuthor())) {
            fieldsList.add("Author");
        }
        if (!isStringCorrect(book.getCategory())) {
            fieldsList.add("Category");
        }
        if (!isStringCorrect(book.getTitle())) {
                    fieldsList.add("Title");
        }
        if ((book.getPublicationYear() / 1900 > 1 && book.getPublicationYear() <= LocalDateTime.now().getYear())) {
            fieldsList.add("Publication Year");
        }
        if (fieldsList.size() > 1) {
            message = "Fields with blank or invalid values: ";
            for (String field : fieldsList) {
                message = message + field + ", ";
            }
        } else {
            message = "Field with blank or invalid value: " + fieldsList.get(0);
        }
        message = message.trim();
        if (message.charAt((message.length() - 1)) == ',') {
            message = message.substring(0, message.length() - 1);
        }
        message = message + ".";
        return message;
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
        if(repository.existsById(id)) {
            repository.deleteById(id);
            message = "Book removed successfully";
        } else {
            message = "Book does not exist";
        }
            return new BookResponse(message, HttpStatus.OK);
    }

    public List<BookDto> getAllBooks() {
        List<BookDto> dtoList = new ArrayList<>();
        List<Book> books = repository.findAll();
        books.forEach(b -> dtoList.add(BookMapper.mapToBookDto(b)));
        return dtoList;
    }

    public List<String> getAllCategories() {
        Set<String> allCategories = new TreeSet<>();
        List<Book> books = repository.findAll();
        for (Book b: books) {
            allCategories.add(b.getCategory());
        }
        return allCategories.stream().toList();
    }

    public List<BookDto> getFilteredBooks(BookFilter filter) {
        List<Book> books;
        List<BookDto> dtoList = new ArrayList<>();
            if(!isStringCorrect(filter.getTitle())) {
                filter.setTitle("");
                if(!isStringCorrect(filter.getAuthor())) {
                    filter.setAuthor("");
                    if (!isStringCorrect(filter.getCategory())) {
                        filter.setCategory("");
                    }
                }
            }
            books = repository.findBooksByCategoryTitleAndAuthor(filter.getCategory(),filter.getTitle(),filter.getAuthor());
            books.forEach(b -> dtoList.add(BookMapper.mapToBookDto(b)));
            return dtoList;
    }

    private boolean isStringCorrect(String strToVerify) {
        if(strToVerify.isBlank() || strToVerify == null) {
            return false;
        }
        return true;
    }
}
