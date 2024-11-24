package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "reader")
@AllArgsConstructor
@NoArgsConstructor
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @OneToMany
    @Column(nullable = false)
    private List<Book> books;


    public Reader(String name, String password) {
        this.name = name;
        this.password = password;
        this.books = new ArrayList<>();
    }
}