package com.example.book_api.service;

import com.example.book_api.Exception.EmployeeNotFoundException;
import com.example.book_api.dto.EmployeeRequest;
import com.example.book_api.entity.Employee;
import com.example.book_api.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return employee.get();
        }

        throw new EmployeeNotFoundException(id);
    }

    @Override
    public Employee createEmployee(EmployeeRequest employeeRequest) {
        Employee data = new Employee();
        data.setFirstName(employeeRequest.getFirstName())
                .setLastName(employeeRequest.getLastName())
                .setPosition(employeeRequest.getPosition())
                .setDateOfBirthday(employeeRequest.getDateOfBirthday());

        return employeeRepository.save(data);
    }

    @Override
    public Employee updateEmployee(EmployeeRequest employeeRequest, Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            Employee existingEmployee = employee.get();
            existingEmployee.setFirstName(employeeRequest.getFirstName())
                    .setLastName(employeeRequest.getLastName())
                    .setPosition(employeeRequest.getPosition())
                    .setDateOfBirthday(employeeRequest.getDateOfBirthday());

            return employeeRepository.save(existingEmployee);
        }

        throw new EmployeeNotFoundException(id);
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    @Override
    public List<Employee> findByFirstName(String firstName) {
        List<Employee> employees = employeeRepository.findByFirstName(firstName);
        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException(firstName);
        }
        return employees;
    }
}
