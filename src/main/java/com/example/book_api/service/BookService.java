package com.example.book_api.service;

import com.example.book_api.dto.BookRequest;
import com.example.book_api.entity.Book;

import java.util.List;

public interface BookService {
    Book saveBook(BookRequest request);

    List<Book> findByAuthor(String author);

    long getCount();

}
