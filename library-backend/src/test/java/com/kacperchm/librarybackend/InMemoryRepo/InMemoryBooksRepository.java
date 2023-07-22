package com.kacperchm.librarybackend.InMemoryRepo;

import com.kacperchm.librarybackend.model.Book;
import com.kacperchm.librarybackend.repository.BooksRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class InMemoryBooksRepository implements BooksRepository {

    List<Book> bookDb = new ArrayList<>();

    @Override
    public List<Book> findAllByAuthorContains(String author) {
        List<Book> bookList = new ArrayList<>();
        for (Book b : bookDb) {

            if (b.getAuthor().contains(author)) {
                bookList.add(b);
            }
        }
        return bookList;
    }

    @Override
    public List<Book> findAllByTitleContains(String title) {
        List<Book> bookList = new ArrayList<>();
        for (Book b : bookDb) {
            if (b.getTitle().contains(title)) {
                bookList.add(b);
            }
        }
        return bookList;
    }

    @Override
    public List<Book> findAllByCategory(String category) {
        List<Book> bookList = new ArrayList<>();
        for (Book b : bookDb) {
            if (b.getCategory().equals(category)) {
                bookList.add(b);
            }
        }
        return bookList;
    }

    @Override
    public List<Book> findBooksByCategoryTitleAndAuthor(String category, String title, String author) {
        List<Book> bookList = new ArrayList<>();
        for (Book b : bookDb) {
            if (category.isEmpty() || b.getCategory().equals(category)) {
                if (title.isEmpty() || b.getTitle().contains(title)) {
                    if (author.isEmpty() || b.getAuthor().contains(author)) {
                        bookList.add(b);
                    }
                }
            }
        }
        return bookList;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Book> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Book> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Book> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Book getOne(Long aLong) {
        return null;
    }

    @Override
    public Book getById(Long aLong) {
        return null;
    }

    @Override
    public Book getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Book> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Book> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Book> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Book> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Book> long count(Example<S> example) {
        return bookDb.size();
    }

    @Override
    public <S extends Book> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Book, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Book> S save(S entity) {
        bookDb.add(entity);
        return entity;
    }

    @Override
    public <S extends Book> List<S> saveAll(Iterable<S> entities) {
        for (Book b : entities) {
            bookDb.add(b);
        }
        return (List<S>) entities;
    }

    @Override
    public Optional<Book> findById(Long aLong) {
        Book returnedBook = null;
        for (Book book : bookDb) {
            if (book.getId() == aLong) {
                returnedBook = book;
            }
        }
        return Optional.of(returnedBook);
    }

    @Override
    public boolean existsById(Long aLong) {
        for (Book b: bookDb) {
            if(b.getId() == aLong) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Book> findAll() {
        return bookDb;
    }

    @Override
    public List<Book> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {
        Book bookToRemove = null;
        for (Book book : bookDb) {
            if (book.getId() == aLong) {
                bookToRemove = book;
            }
        }
        bookDb.remove(bookToRemove);
    }

    @Override
    public void delete(Book entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Book> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Book> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return null;
    }
}
