package com.example.exception;

public class IncorrectCountOfBooks extends RuntimeException {
    public IncorrectCountOfBooks() {
        super("Count of books must be 1 or more");
    }
}