package com.example.book_api.service;

import com.example.book_api.dto.EmployeeRequest;
import com.example.book_api.entity.Book;
import com.example.book_api.entity.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> getAllEmployee();

    Employee getEmployeeById(Long id);

    Employee createEmployee(EmployeeRequest employeeRequest);

    Employee updateEmployee(EmployeeRequest employeeRequest, Long id);

    void deleteEmployee(Long id);

    int calculateAge(LocalDate birthDate);

    List<Employee> findByFirstName(String firstName);
}
