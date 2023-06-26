package com.kacperchm.librarybackend.repository;

import com.kacperchm.librarybackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User,Long> {
    List<User> findByUsername(String username);
    List<User> findByMail(String mail);
    boolean existsByUsername(String username);
    boolean existsByMail(String mail);
    boolean existsByPhoneNumber(String mail);

}
