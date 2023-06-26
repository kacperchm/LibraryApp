package com.kacperchm.librarybackend.repository;

import com.kacperchm.librarybackend.model.Book;
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

    @Query("SELECT b FROM Book b WHERE b.category = :category AND b.title LIKE %:title% AND b.author LIKE %:author%")
    List<Book> findBooksByCategoryTitleAndAuthor(@Param("category") String category, @Param("title") String title, @Param("author") String author);

    @Query("SELECT b FROM Book b WHERE b.category = :category AND b.title LIKE %:title%")
    List<Book> findBooksByCategoryAndTitle(@Param("category") String category, @Param("title") String title);

    @Query("SELECT b FROM Book b WHERE b.category = :category AND b.author LIKE %:author%")
    List<Book> findBooksByCategoryAndAuthor(@Param("category") String category, @Param("author") String author);

    @Query("SELECT b FROM Book b WHERE b.title LIKE %:title% AND b.author LIKE %:author%")
    List<Book> findBooksByTitleAndAuthor(@Param("title") String title, @Param("author") String author);
}
