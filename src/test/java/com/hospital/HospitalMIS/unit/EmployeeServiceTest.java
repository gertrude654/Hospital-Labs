package com.hospital.HospitalMIS.unit;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.hospital.HospitalMIS.model.Employee;
import com.hospital.HospitalMIS.repositories.EmployeeRepository;
import com.hospital.HospitalMIS.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee();
        employee.setEmNo(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setAddress("Kicukiro");
        employee.setTelephone("0788221133");
    }

    @Test
    public void testCreateEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee createdEmployee = employeeService.createEmployee(employee);

        assertThat(createdEmployee.getEmNo()).isEqualTo(employee.getEmNo());
        assertThat(createdEmployee.getFirstName()).isEqualTo(employee.getFirstName());
    }

    @Test
    public void testGetEmployeeById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee foundEmployee = employeeService.getEmployeeById(1L);

        assertThat(foundEmployee.getEmNo()).isEqualTo(employee.getEmNo());
    }

    @Test
    public void testUpdateEmployee() {
        Employee updatedEmployee = new Employee();
        updatedEmployee.setFirstName("Jane");
        updatedEmployee.setLastName("Doe");
        updatedEmployee.setAddress("USA");
        updatedEmployee.setTelephone("0788221144");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        Employee result = employeeService.updateEmployee(1L, updatedEmployee);

        assertThat(result.getFirstName()).isEqualTo("Jane");
    }

    @Test
    public void testDeleteEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(employee);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).delete(employee);
    }
}

