package com.example.book_api.controller;

import com.example.book_api.dto.EmployeeRequest;
import com.example.book_api.entity.Employee;
import com.example.book_api.service.EmployeeService;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public List<Employee> getEmployees() {
        return employeeService.getAllEmployee();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping(path = "/search", params = {"firstName"})
    public List<Employee> findByFirstName(@RequestParam String firstName) {
        return employeeService.findByFirstName(firstName);
    }

    @PostMapping()
    public Employee addEmployee(@RequestBody @Validated EmployeeRequest employeeRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().stream()
                    .forEach(fieldError -> {
                        throw new ValidationException(fieldError.getField() + ":" + fieldError.getDefaultMessage());
                    });
        }

        return employeeService.createEmployee(employeeRequest);
    }

    @PutMapping("/{id}")
    public Employee editEmployee(@RequestBody @Validated EmployeeRequest employeeRequest, BindingResult bindingResult, @PathVariable long id) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().stream()
                    .forEach(fieldError -> {
                        throw new ValidationException(fieldError.getField() + ":" + fieldError.getDefaultMessage());
                    });
        }

        return employeeService.updateEmployee(employeeRequest, id);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployee(id);
    }

    @PostMapping("/calculateAge")
    public int calculateAge(@RequestBody EmployeeRequest employeeRequest) {
        return employeeService.calculateAge(employeeRequest.getDateOfBirthday());
    }

}
