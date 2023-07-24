package com.kacperchm.librarybackend.repository;

import com.kacperchm.librarybackend.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
}
