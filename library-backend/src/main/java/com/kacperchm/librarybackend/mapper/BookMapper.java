package com.kacperchm.librarybackend.mapper;

import com.kacperchm.librarybackend.model.Book;
import com.kacperchm.librarybackend.model.dto.BookDto;

public class BookMapper {

    public static Book mapToBook(BookDto dto) {
        Book book = new Book(dto.getAuthor(),
                dto.getTitle(), dto.getPublicationYear(),
                dto.getCategory());
        return book;
    }

    public static BookDto mapToBookDto(Book book) {
        BookDto dto = new BookDto(book.getAuthor(), book.getTitle(),
                book.getPublicationYear(), book.getCategory(), book.isAvailability());
        return dto;
    }
}
