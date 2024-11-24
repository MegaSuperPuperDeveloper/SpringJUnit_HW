package com.example.controller;

import com.example.model.Book;
import com.example.model.Reader;
import com.example.service.BookService;
import com.example.service.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/readers")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService readerService;
    private final BookService bookService;

    /**
     * Method is required for get list readers
     * @return all readers
     */
    @GetMapping
    public ResponseEntity<Iterable<Reader>> getReaders() {
        return new ResponseEntity<>(readerService.findAll(), HttpStatus.OK);
    }

    /**
     * Method is required for reader find with id
     * @param id reader id who is has to be found
     * @return reader
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReaderById(Long id) {
        if (readerService.findById(id) != null) {
            return new ResponseEntity<>(readerService.findById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Method is required for reader delete
     * @param id reader id who is has to be deleted
     * @return OK or NOT_FOUND
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReaderById(Long id) {
        if (readerService.findById(id) != null) {
            readerService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Method is required for reader create
     * @param name new reader name
     * @param password new reader password
     * @return new reader JSON
     */
    @PostMapping("/{name}/{password}")
    public ResponseEntity<Reader> createReader(@PathVariable String name, @PathVariable String password) {
        return new ResponseEntity<>(readerService.save(name, password), HttpStatus.CREATED);
    }

    /**
     * Method is required for name change
     * @param id reader id who is name has to changed
     * @param name new name
     * @return reader JSON with new name
     */
    @PatchMapping("/{id}/name/{name}")
    public ResponseEntity<Optional<Reader>> updateNameById(@PathVariable Long id, @PathVariable String name) {
        if (readerService.findById(id) != null) {
            return new ResponseEntity<>(readerService.updateNameById(id, name), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Method is required for password change
     * @param id reader id who is password has to change
     * @param password new password
     * @return reader JSON with new password
     */
    @PatchMapping("/{id}/password/{password}")
    public ResponseEntity<Optional<Reader>> updatePasswordById(@PathVariable Long id, @PathVariable String password) {
        if (readerService.findById(id) != null) {
            return new ResponseEntity<>(readerService.updatePasswordById(id, password), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{idReader}/{idBook}/get")
    public ResponseEntity<Optional<Reader>> addBooksById(@PathVariable Long idBook, @PathVariable Long idReader) {

        if (bookService.findById(idBook).isPresent()) {
            Reader reader = readerService.findById(idReader);
            List<Book> books = reader.getBooks();

            if (books == null) {
                books = new ArrayList<>();
            }
            books.add(bookService.findById(idBook).get());

            readerService.addBooksById(idReader, books);
            bookService.giveBook(idBook);
            return new ResponseEntity<>(Optional.of(reader), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{idReader}/{idBook}/give")
    public ResponseEntity<Optional<Reader>> giveBooksById(@PathVariable Long idBook, @PathVariable Long idReader) {

        if (bookService.findById(idBook).isPresent()) {
            Reader reader = readerService.findById(idReader);
            List<Book> books = reader.getBooks();

            if (books == null) {
                books = new ArrayList<>();
            }

            if (books.contains(bookService.findById(idBook).get())) {
                books.remove(bookService.findById(idBook).get());
                readerService.giveBooksById(idReader, books);
                bookService.addBook(idBook);
                return new ResponseEntity<>(Optional.of(reader), HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}