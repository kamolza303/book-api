
package com.example.book_api.controller;

import com.example.book_api.dto.BookRequest;
import com.example.book_api.entity.Book;
import com.example.book_api.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/books")
@Validated
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> findByAuthor(@RequestParam String author) {
        return bookService.findByAuthor(author);
    }

    @GetMapping("/count")
    public Long getCount() {
        return bookService.getCount();
    }

    @PostMapping
    public ResponseEntity<Book> saveBook(@Valid @RequestBody BookRequest request) {
        Book book = bookService.saveBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

}
