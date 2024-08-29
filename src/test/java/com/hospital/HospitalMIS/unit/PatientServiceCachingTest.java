package com.hospital.HospitalMIS.unit;

import com.hospital.HospitalMIS.model.Patient;
import com.hospital.HospitalMIS.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PatientServiceCachingTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private CacheManager cacheManager;

//    @Test
//    public void testCacheable() {
//        // Create a patient
//        Patient patient = new Patient();
//        patient.setPatientNo(1L);
//        patient.setName("Jane Doe");
//        patient.setAddress("456 Avenue");
//        patient.setTelNo("0987654321");
//
//        patientService.createPatient(patient);
//
//        // Fetch the patient to cache it
//        Patient fetchedPatient = patientService.getPatientById(1L);
//
//        // Assert that the patient is cached
//        assertThat(cacheManager.getCache("patients").get(1L)).isNotNull();
//        assertThat(cacheManager.getCache("patients").get(1L).get()).isEqualTo(fetchedPatient);
//    }

//    @Test
//    public void testCacheable() {
//        // Create a patient
//        Patient patient = new Patient();
//        patient.setPatientNo(1L);
//        patient.setName("Jane Doe");
//        patient.setAddress("456 Avenue");
//        patient.setTelNo("0987654321");
//
//        // Create the patient, which will also cache it
//        patientService.createPatient(patient);
//
//        // Fetch the patient to confirm it's cached
//        Patient fetchedPatient = patientService.getPatientById(1L);
//
//        // Assert that the patient is cached
//        assertThat(cacheManager.getCache("patients").get(1L)).isNotNull();
//        assertThat(cacheManager.getCache("patients").get(1L).get()).isEqualTo(fetchedPatient);
//    }

    @Test
    public void testCacheable() {
        // Create a patient
        Patient patient = new Patient();
        patient.setPatientNo(1L);
        patient.setName("Jane Doe");
        patient.setAddress("456 Avenue");
        patient.setTelNo("0987654321");

        // Create the patient, which will also cache it
        patientService.createPatient(patient);

        // Fetch the patient to confirm it's cached
        Patient fetchedPatient = patientService.getPatientById(1L);

        // Assert that the patient is cached
        assertThat(cacheManager.getCache("patients").get(1L)).isNotNull();
        assertThat(cacheManager.getCache("patients").get(1L).get()).isEqualTo(fetchedPatient);
    }



    @Test
    public void testCacheEvictionOnUpdate() {
        // Create a patient
        Patient patient = new Patient();
        patient.setPatientNo(1L);
        patient.setName("Jane Doe");
        patient.setAddress("456 Avenue");
        patient.setTelNo("0987654321");

        patientService.createPatient(patient);
        patientService.getPatientById(1L); // Cache the patient

        // Update the patient
        patient.setName("Jane Smith");
        patientService.updatePatient(1L, patient);

        // Assert that the cached patient is evicted and updated
        assertThat(cacheManager.getCache("patients").get(1L)).isNull(); // Cache should be evicted
    }
}
