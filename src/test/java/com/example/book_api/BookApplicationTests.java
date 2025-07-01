package com.example.book_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookApplicationTests {

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
	public void testSaveBookValidationError() throws Exception {
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
	public void testSaveBookInvalidBuddhistYear() throws Exception {
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
	public void testMultipleBooksSameAuthor() throws Exception {
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
	public void testGetBooksAuthorNotFound() throws Exception {
		mockMvc.perform(get("/books?author=Nobody"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(0));
	}

	@Test
	public void testSaveBookSuccessFully() throws Exception {
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
	public void testGetBooksByAuthor() throws Exception {
		mockMvc.perform(get("/books?author=Kamol"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value("Spring Boot"));
	}

}
