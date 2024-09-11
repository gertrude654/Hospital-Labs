package com.hospital.HospitalMIS.service;

import com.hospital.HospitalMIS.exception.ResourceNotFoundException;
import com.hospital.HospitalMIS.model.Employee;
import com.hospital.HospitalMIS.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    private ConcurrentHashMap<Long, Employee> employeeCache = new ConcurrentHashMap<>();
    private CopyOnWriteArrayList<Employee> employeeList = new CopyOnWriteArrayList<>();

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CompletableFuture<Employee> createEmployeeAsync(Employee employee) {
        logger.info("Thread {} is creating an employee", Thread.currentThread().getName());
        Employee savedEmployee = employeeRepository.save(employee);
        employeeCache.put(savedEmployee.getEmNo(), savedEmployee);
        employeeList.add(savedEmployee);
        logger.info("Thread {} has successfully created employee with ID: {}", Thread.currentThread().getName(), savedEmployee.getEmNo());
        return CompletableFuture.completedFuture(savedEmployee);
    }

    @Async
    @Cacheable(value = "employees", key = "#id")
    public CompletableFuture<Employee> getEmployeeByIdAsync(Long id) {
        logger.info("Thread {} is fetching employee with ID: {}", Thread.currentThread().getName(), id);
        Employee employee = employeeCache.computeIfAbsent(id, key -> employeeRepository.findById(key)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id)));
        logger.info("Thread {} has successfully fetched employee: {}", Thread.currentThread().getName(), employee);
        return CompletableFuture.completedFuture(employee);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(value = "employees", key = "#id")
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        logger.info("Thread {} is updating employee with ID: {}", Thread.currentThread().getName(), id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setAddress(employeeDetails.getAddress());
        employee.setTelephone(employeeDetails.getTelephone());
        Employee updatedEmployee = employeeRepository.save(employee);
        logger.info("Thread {} has successfully updated employee with ID: {}", Thread.currentThread().getName(), updatedEmployee.getEmNo());
        return updatedEmployee;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @CacheEvict(value = "employees", key = "#id")
    public void deleteEmployee(Long id) {
        logger.info("Thread {} is deleting employee with ID: {}", Thread.currentThread().getName(), id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        employeeRepository.delete(employee);
        logger.info("Thread {} has successfully deleted employee with ID: {}", Thread.currentThread().getName(), id);
    }

    public List<Employee> getEmployeesByLastName(String lastName) {
        logger.info("Thread {} is fetching employees with last name: {}", Thread.currentThread().getName(), lastName);
        return employeeRepository.findByLastName(lastName);
    }

    public Employee getEmployeeByName(String firstName, String lastName) {
        logger.info("Thread {} is fetching employee with name: {} {}", Thread.currentThread().getName(), firstName, lastName);
        return employeeRepository.findByFirstNameAndLastName(firstName, lastName)
                .stream().findFirst().orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }
}
