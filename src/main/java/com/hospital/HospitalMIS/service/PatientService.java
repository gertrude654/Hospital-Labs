package com.hospital.HospitalMIS.service;

import com.hospital.HospitalMIS.exception.ResourceNotFoundException;
import com.hospital.HospitalMIS.model.Patient;
import com.hospital.HospitalMIS.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Service
public class PatientService {

    private PatientRepository repo;

    private PlatformTransactionManager transactionManager;

    @Autowired
    public PatientService(PatientRepository repo, PlatformTransactionManager transactionManager) {
        this.repo = repo;
        this.transactionManager = transactionManager;
    }

//    // Create a new Patient using programmatic transactions
////    @Cacheable(value = "patients", key = "#id")
//    @Cacheable(value = "patients")
//    public Patient createPatient(Patient patient) {
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setName("CreatePatientTransaction");
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//
//        TransactionStatus status = transactionManager.getTransaction(def);
//
//        try {
//            // Save the patient
//            Patient savedPatient = repo.save(patient);
//            // Commit the transaction
//            transactionManager.commit(status);
//            return savedPatient;
//        } catch (Exception e) {
//            // Rollback the transaction on error
//            transactionManager.rollback(status);
//            throw e; // Re-throw the exception to indicate failure
//        }
//    }

    @Autowired
    private CacheManager cacheManager;

    public Patient createPatient(Patient patient) {
        // Define the transaction
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("CreatePatientTransaction");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        // Get a new transaction
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // Save the patient
            Patient savedPatient = repo.save(patient);

            // Update cache after saving
            cacheManager.getCache("patients").put(savedPatient.getPatientNo(), savedPatient);

            // Commit the transaction
            transactionManager.commit(status);
            return savedPatient;
        } catch (Exception e) {
            // Rollback the transaction on error
            transactionManager.rollback(status);
            throw e; // Re-throw the exception to indicate failure
        }
    }


    // Get Patient by ID with different propagation and isolation
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public Patient getPatientById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + id));
    }

    // Update an existing Patient
    @Transactional
    @CacheEvict(value = "patients", key = "#id") // Evict cache on delete
    public Patient updatePatient(Long id, Patient PatientDetails) {
        Patient patient = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + id));


        patient.setName(PatientDetails.getName());
        patient.setAddress(PatientDetails.getAddress());
        patient.setTelNo(PatientDetails.getTelNo());
        return repo.save(patient);
    }

    // Delete a Patient
    @Transactional
    @CacheEvict(value = "patients", key = "#id") // Evict cache on delete
    public void deletePatient(Long id) {
        Patient patient = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + id));
        repo.delete(patient);
    }

    // Custom query methods
    @Transactional(readOnly = true)
    public List<Patient> getPatientsByName(String name) {
        return repo.findByName(name);
    }

    @Transactional(readOnly = true)
    public Patient getPatientByAddress(String address) {
        return repo.findByAddress(address)
                .stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with address: " + address));
    }
}
