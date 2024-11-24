package com.example.service;

import com.example.model.Book;
import com.example.model.Reader;
import com.example.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository readerRepository;
    private final BookService bookService;

    public Iterable<Reader> findAll() {
        return readerRepository.findAll();
    }

    public Reader findById(Long id) {
        return readerRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        readerRepository.deleteById(id);
    }

    public Reader save(String name, String password) {
        return readerRepository.save(new Reader(name, password));
    }

    public Optional<Reader> updateNameById(Long id, String name) {
        readerRepository.updateNameById(id, name);
        return readerRepository.findById(id);
    }

    public Optional<Reader> updatePasswordById(Long id, String password) {
        readerRepository.updatePasswordById(id, password);
        return readerRepository.findById(id);
    }

    public void addBooksById(Long idReader, List<Book> books) {
        Reader reader = readerRepository.findById(idReader).orElseThrow(() -> new RuntimeException("Читатель не найден"));
        reader.setBooks(books);
        readerRepository.save(reader);
    }

    public void giveBooksById(Long idReader, List<Book> books) {
        Reader reader = readerRepository.findById(idReader).orElseThrow(() -> new RuntimeException("Читатель не найден"));
        reader.setBooks(books);
        readerRepository.save(reader);
    }

}