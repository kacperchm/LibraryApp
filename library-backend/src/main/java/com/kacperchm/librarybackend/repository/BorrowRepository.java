package com.kacperchm.librarybackend.repository;

import com.kacperchm.librarybackend.model.Borrow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    @Query("SELECT bb FROM Borrow bb WHERE bb.member.id = :memberId")
    Page<Borrow> findByMemberId(Long memberId, Pageable pageable);

    @Query("SELECT bb FROM Borrow bb WHERE bb.member.id = :memberId")
    List<Borrow> findByMemberId(Long memberId);
}
