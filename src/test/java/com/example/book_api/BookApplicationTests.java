package com.example.book_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testSaveBookAndGetByAuthor() throws Exception {
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

		mockMvc.perform(get("/books?author=Kamol"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value("Spring Boot"));
	}

}
