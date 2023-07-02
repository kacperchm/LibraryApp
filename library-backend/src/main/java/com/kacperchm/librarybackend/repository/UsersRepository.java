package com.kacperchm.librarybackend.repository;

import com.kacperchm.librarybackend.model.Book;
import com.kacperchm.librarybackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User,Long> {
    List<User> findByUsername(String username);
    List<User> findByMail(String mail);
    boolean existsByUsername(String username);
    boolean existsByMail(String mail);
    boolean existsByPhoneNumber(String mail);

    @Query("SELECT u FROM User u WHERE u.username LIKE %:username% AND u.phoneNumber LIKE %:phoneNumber% AND u.mail LIKE %:mail%")
    List<User> findUsersByUsernamePhoneNumberAndMail(@Param("username") String username, @Param("phoneNumber") String phoneNumber, @Param("mail") String mail);
}
