
package com.example.book_api.service;

import com.example.book_api.dto.BookRequest;
import com.example.book_api.entity.Book;
import com.example.book_api.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(BookRequest request) {
        LocalDate buddhistDate = LocalDate.parse(request.getPublishedDateBuddhist());
        int buddhistYear = buddhistDate.getYear();
        if (buddhistYear <= 1000 || buddhistYear > Year.now().getValue() + 543) {
            throw new IllegalArgumentException("Invalid Buddhist year.");
        }

        LocalDate gregorianDate = buddhistDate.withYear(buddhistYear - 543);

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPublishedDate(gregorianDate);

        return bookRepository.save(book);
    }

    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }
}
