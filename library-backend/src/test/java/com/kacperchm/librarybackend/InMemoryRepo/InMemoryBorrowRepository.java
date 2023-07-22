package com.kacperchm.librarybackend.InMemoryRepo;

import com.kacperchm.librarybackend.model.Book;
import com.kacperchm.librarybackend.model.Borrow;
import com.kacperchm.librarybackend.repository.BorrowedBooksRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class InMemoryBorrowRepository implements BorrowedBooksRepository {

    List<Borrow> borrowDB = new ArrayList<>();

    @Override
    public void flush() {

    }

    @Override
    public <S extends Borrow> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Borrow> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Borrow> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Borrow getOne(Long aLong) {
        return null;
    }

    @Override
    public Borrow getById(Long aLong) {
        return null;
    }

    @Override
    public Borrow getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Borrow> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Borrow> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Borrow> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Borrow> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Borrow> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Borrow> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Borrow, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Borrow> S save(S entity) {
        borrowDB.add(entity);
        return entity;
    }

    @Override
    public <S extends Borrow> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Borrow> findById(Long aLong) {
        for (Borrow b: borrowDB) {
            if(b.getId() == aLong) {
                return Optional.of(b);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        for (Borrow b: borrowDB) {
            if(b.getId() == aLong) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Borrow> findAll() {
        return borrowDB;
    }

    @Override
    public List<Borrow> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Borrow entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Borrow> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Borrow> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Borrow> findAll(Pageable pageable) {
        return null;
    }
}
