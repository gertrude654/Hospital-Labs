package com.hospital.HospitalMIS.integration;

import com.hospital.HospitalMIS.exception.ResourceNotFoundException;
import com.hospital.HospitalMIS.model.Patient;
import com.hospital.HospitalMIS.repositories.PatientRepository;
import com.hospital.HospitalMIS.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PatientServiceIntegrationTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private CacheManager cacheManager;

    private Patient testPatient;

    @BeforeEach
    void setUp() {
        // Initialize test data
        testPatient = new Patient();
        testPatient.setName("John Doe");
        testPatient.setAddress("123 Main St");
        testPatient.setTelNo("123-456-7890");
    }

    @Test
    @Rollback // Rollback the transaction after the test
    void createPatient_ShouldSavePatient() {
        Patient createdPatient = patientService.createPatient(testPatient);

        assertThat(createdPatient).isNotNull();
        assertThat(createdPatient.getPatientNo()).isNotNull();
        assertThat(createdPatient.getName()).isEqualTo(testPatient.getName());
    }

    @Test
    @Rollback
    void getPatientById_ShouldReturnPatient() {
        Patient createdPatient = patientService.createPatient(testPatient);
        Patient fetchedPatient = patientService.getPatientById(createdPatient.getPatientNo());

        assertThat(fetchedPatient).isNotNull();
        assertThat(fetchedPatient.getPatientNo()).isEqualTo(createdPatient.getPatientNo());
    }

    @Test
    @Rollback
    void updatePatient_ShouldUpdatePatient() {
        Patient createdPatient = patientService.createPatient(testPatient);
        createdPatient.setName("Jane Doe");

        Patient updatedPatient = patientService.updatePatient(createdPatient.getPatientNo(), createdPatient);

        assertThat(updatedPatient.getName()).isEqualTo("Jane Doe");
    }

    @Test
    @Rollback
    void deletePatient_ShouldRemovePatient() {
        Patient createdPatient = patientService.createPatient(testPatient);
        patientService.deletePatient(createdPatient.getPatientNo());

        assertThrows(ResourceNotFoundException.class, () -> {
            patientService.getPatientById(createdPatient.getPatientNo());
        });
    }

    @Test
    void caching_ShouldCachePatient() {
        Patient createdPatient = patientService.createPatient(testPatient);
        // Fetch the patient to cache it
        patientService.getPatientById(createdPatient.getPatientNo());

        // Clear the cache to ensure we are fetching fresh data
        cacheManager.getCache("patients").clear();

        // Now fetch the patient again, it should hit the database again
        Patient fetchedPatient = patientService.getPatientById(createdPatient.getPatientNo());

        assertThat(fetchedPatient.getPatientNo()).isEqualTo(createdPatient.getPatientNo());
    }
}
