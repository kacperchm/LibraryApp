package com.kacperchm.librarybackend.repository;

import com.kacperchm.librarybackend.model.LibraryMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersRepository extends JpaRepository<LibraryMember,Long> {
}
