package com.example.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "book")
@RequiredArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private int count;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.count = 0;
    }

    public Book(String title, String author, int count) {
        this.title = title;
        this.author = author;
        this.count = count;
    }

}