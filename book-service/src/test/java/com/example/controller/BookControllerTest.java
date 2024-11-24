package com.example.controller;

import com.example.config.JUnitSpringBootBase;
import com.example.model.Book;
import com.example.repository.BookRepository;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Objects;

public class BookControllerTest extends JUnitSpringBootBase {

    @Autowired
    private BookRepository repository;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Data
    static class JUnitBookResponse {
        private Long id;
        private String title;
        private String author;
    }

    @Test
    void testFindAll() {
        repository.saveAll(List.of(
                new Book("title1", "author1"),
                new Book("title2", "author2")
        ));

        List<Book> books = repository.findAll();

        List<JUnitBookResponse> expectedBooks = webTestClient.get()
                .uri("/library")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<JUnitBookResponse>>() {})
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(expectedBooks.size(), books.size());
        for (JUnitBookResponse book : expectedBooks) {
            boolean found = expectedBooks.stream()
                    .filter(b -> Objects.equals(book.getAuthor(), b.getAuthor()))
                    .filter(b -> Objects.equals(book.getTitle(), b.getTitle()))
                    .anyMatch(b -> Objects.equals(book.getId(), b.getId()));
            Assertions.assertTrue(found);
        }

    }

    @Test
    void testFindByIdSuccess() {
        Book book1 = new Book("title", "author");

        repository.save(book1);

        JUnitBookResponse responseBody = webTestClient.get()
                .uri("/library/id/" + book1.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitBookResponse.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(book1.getId(), responseBody.getId());
        Assertions.assertEquals(book1.getAuthor(), responseBody.getAuthor());
        Assertions.assertEquals(book1.getTitle(), responseBody.getTitle());

    }

    @Test
    void testFindByIdError() {
        Long maxId = jdbcTemplate.queryForObject("select max(id) from Book", Long.class);
        long nonExistingId = (maxId != null) ? maxId + 1 : 1L;

        webTestClient.get()
                .uri("/library/id/" + nonExistingId + 1)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteByIdError() {
        Long maxId = jdbcTemplate.queryForObject("select max(id) from Book", Long.class);
        long nonExistingId = (maxId != null) ? maxId + 1 : 1L;

        webTestClient.delete()
                .uri("/library/" + nonExistingId + 1)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteByIdSuccess() {
        Book book1 = new Book("title", "author");
        repository.save(book1);

        Book book2 = repository.findById(book1.getId()).get();
        repository.save(book2);

        webTestClient.delete()
                .uri("/library/" + book1.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitBookResponse.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(repository.findById(book2.getId()));
        Assertions.assertEquals(book2.getId(), book1.getId());
        Assertions.assertEquals(book2.getTitle(), book1.getTitle());
        Assertions.assertEquals(book2.getAuthor(), book1.getAuthor());
    }

}