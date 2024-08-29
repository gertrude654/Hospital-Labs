package com.hospital.HospitalMIS.service;

import com.hospital.HospitalMIS.exception.ResourceNotFoundException;
import com.hospital.HospitalMIS.model.Employee;
import com.hospital.HospitalMIS.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Create a new employee in a new transaction
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Get employee by ID
    @Cacheable(value = "employees", key = "#id")
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
    }

    // Update an existing employee (participates in the current transaction)
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(value = "employees", key = "#id") // Evict cache before updating

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setAddress(employeeDetails.getAddress());
        employee.setTelephone(employeeDetails.getTelephone());
        return employeeRepository.save(employee);
    }

    // Delete an employee in a new transaction
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @CacheEvict(value = "employees", key = "#id") // Evict cache on delete
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        employeeRepository.delete(employee);
    }

    // Custom query methods
    public List<Employee> getEmployeesByLastName(String lastName) {
        return employeeRepository.findByLastName(lastName);
    }

    public Employee getEmployeeByName(String firstName, String lastName) {
        return employeeRepository.findByFirstNameAndLastName(firstName, lastName)
                .stream().findFirst().orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }
}
