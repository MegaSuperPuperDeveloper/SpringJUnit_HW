package com.example.repository;

import com.example.model.Book;
import com.example.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Reader a SET a.name = a.name WHERE a.id = :id")
    void updateNameById(Long id, String name);

    @Modifying
    @Transactional
    @Query("UPDATE Reader a SET a.password = a.password WHERE a.id = :id")
    void updatePasswordById(Long id, String password);

    @Query("SELECT a FROM Reader a WHERE a.name = :name")
    Reader findByName(String name);

}