package com.hospital.HospitalMIS.repositories;

import com.hospital.HospitalMIS.model.Employee;
import com.hospital.HospitalMIS.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Custom query methods
    List<Patient> findByName(String name);
    List<Patient> findByAddress(String address);
}
