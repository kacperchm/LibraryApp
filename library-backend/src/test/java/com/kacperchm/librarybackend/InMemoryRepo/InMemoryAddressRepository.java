package com.kacperchm.librarybackend.InMemoryRepo;

import com.kacperchm.librarybackend.model.Address;
import com.kacperchm.librarybackend.model.Book;
import com.kacperchm.librarybackend.repository.AddressesRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class InMemoryAddressRepository implements AddressesRepository {

    List<Address> addressDB = new ArrayList<>();

    @Override
    public void flush() {

    }

    @Override
    public <S extends Address> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Address> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Address> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Address getOne(Long aLong) {
        return null;
    }

    @Override
    public Address getById(Long aLong) {
        return null;
    }

    @Override
    public Address getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Address> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Address> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Address> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Address> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Address> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Address> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Address, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Address> S save(S entity) {
        addressDB.add(entity);
        return entity;
    }

    @Override
    public <S extends Address> List<S> saveAll(Iterable<S> entities) {
        for (Address address : entities) {
            addressDB.add(address);
        }
        return (List<S>) entities;
    }

    @Override
    public Optional<Address> findById(Long aLong) {
        Address returnedAddress = null;
        for (Address address : addressDB) {
            if (address.getId() == aLong) {
                returnedAddress = address;
            }
        }
        return Optional.of(returnedAddress);
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Address> findAll() {
        return null;
    }

    @Override
    public List<Address> findAllById(Iterable<Long> longs) {
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
    public void delete(Address entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Address> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Address> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Address> findAll(Pageable pageable) {
        return null;
    }
}
