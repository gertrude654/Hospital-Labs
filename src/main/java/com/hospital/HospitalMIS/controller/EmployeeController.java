package com.hospital.HospitalMIS.controller;

import com.hospital.HospitalMIS.model.Employee;
import com.hospital.HospitalMIS.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Create a new employee
    @PostMapping
    public CompletableFuture<ResponseEntity<Employee>> createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployeeAsync(employee)
                .thenApply(savedEmployee -> new ResponseEntity<>(savedEmployee, HttpStatus.CREATED));
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Employee>> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeByIdAsync(id)
                .thenApply(employee -> new ResponseEntity<>(employee, HttpStatus.OK));
    }

    // Update an existing employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    // Delete an employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    // Get employees by last name
    @GetMapping("/lastName/{lastName}")
    public ResponseEntity<List<Employee>> getEmployeesByLastName(@PathVariable String lastName) {
        List<Employee> employees = employeeService.getEmployeesByLastName(lastName);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // Get employee by name
    @GetMapping("/name")
    public ResponseEntity<Employee> getEmployeeByName(@RequestParam String firstName, @RequestParam String lastName) {
        Employee employee = employeeService.getEmployeeByName(firstName, lastName);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}

