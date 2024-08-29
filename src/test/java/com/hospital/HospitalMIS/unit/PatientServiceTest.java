package com.hospital.HospitalMIS.unit;

import com.hospital.HospitalMIS.exception.ResourceNotFoundException;
import com.hospital.HospitalMIS.model.Patient;
import com.hospital.HospitalMIS.repositories.PatientRepository;
import com.hospital.HospitalMIS.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    private Patient patient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        patient = new Patient();
        patient.setPatientNo(1L);
        patient.setName("John Doe");
        patient.setAddress("123 Street");
        patient.setTelNo("1234567890");
    }

    @Test
    public void testCreatePatient() {
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);
        Patient createdPatient = patientService.createPatient(patient);
        assertNotNull(createdPatient);
        assertEquals("John Doe", createdPatient.getName());
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    public void testUpdatePatient() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient updatedPatient = patientService.updatePatient(1L, patient);
        assertNotNull(updatedPatient);
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    public void testDeletePatient() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        doNothing().when(patientRepository).delete(any(Patient.class));

        assertDoesNotThrow(() -> patientService.deletePatient(1L));
        verify(patientRepository, times(1)).delete(patient);
    }

    @Test
    public void testGetPatientByIdNotFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> patientService.getPatientById(1L));
    }
}
