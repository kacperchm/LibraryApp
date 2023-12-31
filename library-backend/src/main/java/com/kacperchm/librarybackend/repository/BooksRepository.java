package com.kacperchm.librarybackend.repository;

import com.kacperchm.librarybackend.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByAuthorContains(String author);
    List<Book> findAllByTitleContains(String title);
    List<Book> findAllByCategory(String category);

    @Query("SELECT b FROM Book b WHERE b.category = :category AND (b.title LIKE %:title% OR b.author LIKE %:author%)")
    Page<Book> findBooksByCategoryTitleAndAuthor(@Param("category") String category, @Param("title") String title, @Param("author") String author, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.title LIKE %:title% OR b.author LIKE %:author%")
    Page<Book> findBooksByTitleAndAuthor(@Param("title") String title, @Param("author") String author, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.category = :category")
    Page<Book> findBooksByCategory(@Param("category") String category, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.category = :category AND (b.title LIKE %:title% OR b.author LIKE %:author%)")
    List<Book> findBooksByFilter(@Param("category") String category, @Param("title") String title, @Param("author") String author);

    @Query("SELECT b FROM Book b WHERE b.title LIKE %:title% OR b.author LIKE %:author%")
    List<Book> findBooksByFilterWithoutCategory(@Param("title") String title, @Param("author") String author);

    @Query("SELECT b FROM Book b WHERE b.category = :category")
    List<Book> findBooksByFilterWithoutAuthorAndTitle(@Param("category") String category);
}
