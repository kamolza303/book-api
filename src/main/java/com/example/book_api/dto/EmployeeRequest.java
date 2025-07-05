package com.example.book_api.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeRequest {

    @NotEmpty(message = "is empty")
    @Size(min = 2, max = 10)
    private String firstName;
    private LocalDate dateOfBirthday;
    private String lastName;
    private String position;
}
