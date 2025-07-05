package com.example.book_api.Exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {
        super("Could not found employee " + id);
    }

    public EmployeeNotFoundException(String firstName) {
        super("Could not found employee " + firstName);
    }
}
