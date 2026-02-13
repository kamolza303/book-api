package com.example.book_api.controller;

import com.example.book_api.entity.Author;
import com.example.book_api.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable long id) {
        return libraryService.getAuthorById(id);
    }

    @PostMapping("/init")
    public Author createSampleData() {
        return libraryService.createAuthorWithProfileAndBooks();
    }


}
