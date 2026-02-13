package com.example.book_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@NoArgsConstructor
public class ProductRequest {

	@NotNull(message = "name is not null")
	@Size(min = 2, max = 100)
	private String name;

	private MultipartFile image;

	private int price;

	private int stock;
}
