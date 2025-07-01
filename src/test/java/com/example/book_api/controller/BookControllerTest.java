package com.example.book_api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        String json = """
        {
          "title": "Spring Boot",
          "author": "Kamol",
          "publishedDateBuddhist": "2567-01-01"
        }
    """;

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }

    @Test
    void testSaveBookValidationTitleError() throws Exception {
        String json = """
        {
          "title": "",
          "author": "Kamol",
          "publishedDateBuddhist": "2567-01-01"
        }
    """;

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Title must not be empty"));
    }

    @Test
    void testSaveBookValidationAuthorError() throws Exception {
        String json = """
        {
          "title": "Test",
          "author": "",
          "publishedDateBuddhist": "2567-01-01"
        }
    """;

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.author").value("author must not be empty"));
    }

    @Test
    void testSaveBookInvalidBuddhistYear() throws Exception {
        String json = """
        {
          "title": "Test",
          "author": "Kamol",
          "publishedDateBuddhist": "3000-01-01"
        }
    """;

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Invalid Buddhist year.")));
    }

    @Test
    void testMultipleBooksSameAuthor() throws Exception {
        String json1 = """
        {"title": "Book1", "author": "Kamol", "publishedDateBuddhist": "2566-01-01"}
    """;
        String json2 = """
        {"title": "Book2", "author": "Kamol", "publishedDateBuddhist": "2566-02-01"}
    """;

        mockMvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content(json1))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content(json2))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/books?author=Kamol"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(greaterThan(1)));
    }

    @Test
    void testGetBooksAuthorNotFound() throws Exception {
        mockMvc.perform(get("/books?author=Nobody"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testSaveBookSuccessFully() throws Exception {
        String json = """
            {
              "title": "Spring Boot",
              "author": "Kamol",
              "publishedDateBuddhist": "2567-01-01"
            }
        """;

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetBooksByAuthor() throws Exception {
        mockMvc.perform(get("/books?author=Kamol"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Spring Boot"));
    }
}
