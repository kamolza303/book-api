package com.example.book_api.service;

import com.example.book_api.Exception.EmployeeNotFoundException;
import com.example.book_api.entity.Author;
import com.example.book_api.entity.Book2;
import com.example.book_api.entity.Profile;
import com.example.book_api.repository.AuthorRepository;
import com.example.book_api.repository.Book2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class LibraryService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired private Book2Repository bookRepository;

    public Author createAuthorWithProfileAndBooks() {
        // สร้าง Profile
        Profile profile = new Profile();
        profile.setEmail("john@example.com");
        profile.setPhone("123456789");

        // สร้าง Author
        Author author = new Author();
        author.setName("John Writer");
        author.setProfile(profile);

        // สร้าง Book 2 เล่ม
        Book2 book1 = new Book2();
        book1.setTitle("Book One");
        book1.setPages(100);
        book1.setAuthor(author); // ManyToOne

        Book2 book2 = new Book2();
        book2.setTitle("Book Two");
        book2.setPages(150);
        book2.setAuthor(author);

        // ผูก Book กับ Author
        author.setBooks(Arrays.asList(book1, book2));

        // บันทึก Author จะ cascade ลง Book และ Profile
        return authorRepository.save(author);
    }

    public Author getAuthorById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            return author.get();
        }

        throw new RuntimeException("--getAuthorById not found");
    }
}

