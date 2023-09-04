package com.kacperchm.librarybackend.repository;

import com.kacperchm.librarybackend.model.Book;
import com.kacperchm.librarybackend.model.LibraryMember;
import com.kacperchm.librarybackend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT u FROM User u WHERE u.username LIKE %:username% OR u.phoneNumber LIKE %:phoneNumber% OR u.mail LIKE %:mail%")
    Page<User> findUsersByUsernamePhoneNumberAndMail(@Param("username") String username, @Param("phoneNumber") String phoneNumber, @Param("mail") String mail, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.username LIKE %:username% OR u.phoneNumber LIKE %:phoneNumber% OR u.mail LIKE %:mail%")
    List<User> findUsersByFilter(@Param("username") String username, @Param("phoneNumber") String phoneNumber, @Param("mail") String mail);

    @Query("SELECT u FROM User u ORDER BY u.mail ASC")
    List<User> findAllUsersOrderByEmail();
}
