package com.kacperchm.librarybackend.InMemoryRepo;

import com.kacperchm.librarybackend.model.User;
import com.kacperchm.librarybackend.repository.UsersRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class InMemoryUserRepository implements UsersRepository {

    List<User> userDb = new ArrayList<>();
    @Override
    public List<User> findByUsername(String username) {
        return null;
    }

    @Override
    public List<User> findByMail(String mail) {
        return null;
    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }

    @Override
    public boolean existsByMail(String mail) {
        return false;
    }

    @Override
    public boolean existsByPhoneNumber(String mail) {
        return false;
    }

    @Override
    public List<User> findUsersByUsernamePhoneNumberAndMail(String username, String phoneNumber, String mail) {
        List<User> filteredList = new ArrayList<>();
        for (User u: userDb) {
            if(u.getUsername().contains(username)
                    && u.getPhoneNumber().contains(phoneNumber)
                    && u.getMail().contains(mail)) {
                filteredList.add(u);
            }
        }
        return filteredList;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends User> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<User> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public User getOne(Long aLong) {
        return null;
    }

    @Override
    public User getById(Long aLong) {
        return null;
    }

    @Override
    public User getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends User> S save(S entity) {
        userDb.add(entity);
        return (S) findById(entity.getId()).get();
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<User> findById(Long aLong) {
        User user = null;
        for (User u: userDb) {
            if(u.getId() == aLong) {
                user = u;
            }
        }
        return Optional.of(user);
    }

    @Override
    public boolean existsById(Long aLong) {
        for (User user : userDb) {
            if(user.getId() == aLong) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<User> findAll() {
        return userDb;
    }

    @Override
    public List<User> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {
        User user = findById(aLong).get();
        userDb.remove(user);
    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<User> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }
}
