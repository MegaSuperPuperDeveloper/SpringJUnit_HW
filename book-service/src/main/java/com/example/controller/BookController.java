package com.example.controller;

import com.example.model.Book;
import com.example.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<Iterable<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Iterable<Book>> getBooksByTitle(@PathVariable String title) {
        return new ResponseEntity<>(bookService.findByTitle(title), HttpStatus.OK);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<Iterable<Book>> getBooksByAuthor(@PathVariable String author) {
        return new ResponseEntity<>(bookService.findByAuthor(author), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{title}/{author}/{count}")
    public ResponseEntity<Book> createBook(@PathVariable String title, @PathVariable String author, @PathVariable Integer count) {
        return new ResponseEntity<>(bookService.save(title, author, count), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) {
        if (bookService.findById(id).isPresent()) {
            bookService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/title/{id}/{title}")
    public ResponseEntity<Book> updateTitleById(@PathVariable Long id, @PathVariable String title) {
        if (bookService.findById(id).isPresent()) {
            return new ResponseEntity<>(bookService.updateTitleById(id, title), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/add/{idBook}")
    public ResponseEntity<Book> getBook(@PathVariable Long idBook) {
        if (bookService.findById(idBook).isPresent()) {
            bookService.addBook(idBook);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{idBook}/add")
    public ResponseEntity<Book> addReaderToBook(@PathVariable Long idBook) {
        if (bookService.findById(idBook).isPresent()) {
            bookService.addBook(idBook);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{idBook}/give")
    public ResponseEntity<Book> giveReaderToBook(@PathVariable Long idBook) {
        if (bookService.findById(idBook).isPresent()) {
            bookService.giveBook(idBook);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}