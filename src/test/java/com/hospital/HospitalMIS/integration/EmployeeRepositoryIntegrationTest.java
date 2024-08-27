package com.hospital.HospitalMIS.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.hospital.HospitalMIS.model.Employee;
import com.hospital.HospitalMIS.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @Rollback(false) // Optional: You may skip rollback for testing
    public void testSaveEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Ada");
        employee.setLastName("Rugira");
        employee.setAddress("Canada");
        employee.setTelephone("345678976543");

        Employee savedEmployee = employeeRepository.save(employee);
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getEmNo()).isGreaterThan(0);
    }

    @Test
    public void testFindAllEmployees() {
        Employee employee1 = new Employee();
        employee1.setFirstName("Kundwa");
        employee1.setLastName("Jessy");
        employee1.setAddress("Muhanga");
        employee1.setTelephone("123456789");

        Employee employee2 = new Employee();
        employee2.setFirstName("Jane");
        employee2.setLastName("Doe");
        employee2.setAddress("Kigali");
        employee2.setTelephone("098765432");

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(5);
    }

    @Test
    public void testFindEmployeeById() {
        Employee employee = new Employee();
        employee.setFirstName("Holy");
        employee.setLastName("Giana");
        employee.setAddress("USA");
        employee.setTelephone("0923232322");
        Employee savedEmployee = employeeRepository.save(employee);

        Employee foundEmployee = employeeRepository.findById(savedEmployee.getEmNo()).orElse(null);
        assertThat(foundEmployee).isNotNull();
        assertThat(foundEmployee.getFirstName()).isEqualTo("Holy");
    }
}
