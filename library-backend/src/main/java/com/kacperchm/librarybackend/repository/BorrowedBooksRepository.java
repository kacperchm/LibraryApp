package com.kacperchm.librarybackend.repository;

import com.kacperchm.librarybackend.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowedBooksRepository extends JpaRepository<Borrow, Long> {
}
