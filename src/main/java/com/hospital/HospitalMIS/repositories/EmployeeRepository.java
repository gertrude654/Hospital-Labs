package com.hospital.HospitalMIS.repositories;

import com.hospital.HospitalMIS.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Custom query methods
    List<Employee> findByLastName(String lastName);
    //List<Employee> findByDepartmentOrderByFirstNameAsc(String department);
    List<Employee> findByFirstNameAndLastName(String firstName, String lastName);
}
