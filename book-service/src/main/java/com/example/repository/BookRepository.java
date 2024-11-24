package com.example.repository;

import com.example.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT a FROM Book a WHERE a.title = :title")
    Iterable<Book> findByTitle(String title);

    @Query("SELECT a FROM Book a WHERE a.author = :author")
    Iterable<Book> findByAuthor(String author);

    @Modifying
    @Transactional
    @Query("UPDATE Book a SET a.title = :title WHERE a.id = :id")
    void updateTitleById(Long id, String title);

    @Modifying
    @Transactional
    @Query("UPDATE Book a SET a.count = a.count + 1 WHERE a.id = :id")
    void addBook(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Book a SET a.count = a.count - 1 WHERE a.id = :id")
    void giveBook(Long id);

}