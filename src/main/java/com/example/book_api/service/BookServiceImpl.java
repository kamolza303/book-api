
package com.example.book_api.service;

import com.example.book_api.dto.BookRequest;
import com.example.book_api.entity.Book;
import com.example.book_api.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;

    BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
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

    @Override
    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public long getCount() {
        return bookRepository.count();
    }


}
