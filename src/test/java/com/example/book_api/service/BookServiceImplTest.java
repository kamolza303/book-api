package com.example.book_api.service;

import com.example.book_api.dto.BookRequest;
import com.example.book_api.entity.Book;
import com.example.book_api.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    @Test
    void testSaveBook_successfullyConvertsBuddhistToGregorian() {
        BookRequest request = new BookRequest();
        request.setTitle("My Book");
        request.setAuthor("Kamol");
        request.setPublishedDateBuddhist("2567-07-01"); // พ.ศ. 2567

        // Mock behavior ของ repository
        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setTitle("My Book");
        savedBook.setAuthor("Kamol");
        savedBook.setPublishedDate(LocalDate.of(2024, 7, 1));

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        Book result = bookServiceImpl.saveBook(request);

        assertEquals("Kamol", result.getAuthor());
        assertEquals(LocalDate.of(2024, 7, 1), result.getPublishedDate()); // ตรวจปี ค.ศ.

        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void testSaveBook_invalidBuddhistYear_throwsException() {
        BookRequest request = new BookRequest();
        request.setTitle("Invalid Book");
        request.setAuthor("Kamol");
        request.setPublishedDateBuddhist("0999-01-01"); // ปีพ.ศ. น้อยเกินไป

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            bookServiceImpl.saveBook(request);
        });

        assertEquals("Invalid Buddhist year.", ex.getMessage());
        verify(bookRepository, never()).save(any());
    }

    @Test
    void testGetBooksByAuthor_returnsBooks() {
        String author = "Kamol";
        List<Book> books = List.of(
                new Book(1L, "A", "Kamol", LocalDate.now()),
                new Book(2L, "B", "Kamol", LocalDate.now())
        );

        when(bookRepository.findByAuthor(author)).thenReturn(books);

        List<Book> result = bookServiceImpl.findByAuthor(author);

        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getTitle());
        verify(bookRepository).findByAuthor(author);
    }
}
