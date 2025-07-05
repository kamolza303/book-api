
package com.example.book_api.controller;

import com.example.book_api.dto.BookRequest;
import com.example.book_api.entity.Book;
import com.example.book_api.service.BookServiceImpl;
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

    private final BookServiceImpl bookServiceImpl;

    public BookController(BookServiceImpl bookServiceImpl) {
        this.bookServiceImpl = bookServiceImpl;
    }

    @GetMapping
    public List<Book> findByAuthor(@RequestParam String author) {
        return bookServiceImpl.findByAuthor(author);
    }

    @GetMapping("/count")
    public Long getCount() {
        return bookServiceImpl.getCount();
    }

    @PostMapping
    public ResponseEntity<Book> saveBook(@Valid @RequestBody BookRequest request) {
        Book book = bookServiceImpl.saveBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

}
