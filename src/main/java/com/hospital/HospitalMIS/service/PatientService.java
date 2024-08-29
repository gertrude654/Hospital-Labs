package com.hospital.HospitalMIS.service;

import com.hospital.HospitalMIS.exception.ResourceNotFoundException;
import com.hospital.HospitalMIS.model.Patient;
import com.hospital.HospitalMIS.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository repo;

    // Create a new Patient
    public Patient createPatient(Patient patient) {
        return repo.save(patient);
    }

    // Get Patient by ID
    public Patient getPatientById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + id));
    }

    // Update an existing Patient
    public Patient updatePatient(Long id, Patient PatientDetails) {
        Patient patient = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + id));


        patient.setName(PatientDetails.getName());
        patient.setAddress(PatientDetails.getAddress());
        patient.setTelNo(PatientDetails.getTelNo());
        return repo.save(patient);
    }

    // Delete a Patient
    public void deletePatient(Long id) {
        Patient Patient = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + id));
        repo.delete(Patient);
    }

    // Custom query methods
    public List<Patient> getPatientsByLastName(String name) {
        return repo.findByName(name);
    }

    public Patient getPatientByName(String address) {
        return repo.findByAddress(address)
                .stream().findFirst().orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
    }
}
