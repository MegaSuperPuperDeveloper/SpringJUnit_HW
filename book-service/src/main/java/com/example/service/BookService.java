package com.example.service;

import com.example.exception.IncorrectCountOfBooks;
import com.example.model.Book;
import com.example.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Iterable<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Iterable<Book> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public Book save(String title, String author, int count) {
        if (count >= 1) {
            return bookRepository.save(new Book(title, author, count));
        }
        throw new IncorrectCountOfBooks();
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public Book updateTitleById(Long id, String title) {
        bookRepository.updateTitleById(id, title);
        return bookRepository.findById(id).get();
    }

    public Book addBook(Long id) {
        bookRepository.addBook(id);
        return bookRepository.findById(id).get();
    }

    public Book giveBook(Long id) {
        bookRepository.giveBook(id);
        return bookRepository.findById(id).get();
    }

}