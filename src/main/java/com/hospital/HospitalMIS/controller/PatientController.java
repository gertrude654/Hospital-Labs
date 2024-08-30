package com.hospital.HospitalMIS.controller;

import com.hospital.HospitalMIS.mongoEntity.PatientMongo;
import com.hospital.HospitalMIS.mongoService.PatientMongoService;
import com.hospital.HospitalMIS.model.Patient;
import com.hospital.HospitalMIS.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientMongoService patientMongoService;

    @Autowired
    private PatientService patientService;

    // MongoDB operations

    @GetMapping("/mongo")
    public ResponseEntity<List<PatientMongo>> getAllPatientsMongo() {
        List<PatientMongo> patients = patientMongoService.findAll();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/mongo/{id}")
    public ResponseEntity<PatientMongo> getPatientByIdMongo(@PathVariable String id) {
        PatientMongo patient = patientMongoService.findById(id);
        if (patient != null) {
            return ResponseEntity.ok(patient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/mongo")
    public ResponseEntity<PatientMongo> createPatientMongo(@RequestBody PatientMongo patient) {
        PatientMongo savedPatient = patientMongoService.save(patient);
        return ResponseEntity.ok(savedPatient);
    }

    @DeleteMapping("/mongo/{id}")
    public ResponseEntity<Void> deletePatientByIdMongo(@PathVariable String id) {
        patientMongoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // SQL operations

    @GetMapping("/sql")
    public ResponseEntity<List<Patient>> getAllPatientsSql() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/sql/{id}")
    public ResponseEntity<Patient> getPatientByIdSql(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        if (patient != null) {
            return ResponseEntity.ok(patient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/sql")
    public ResponseEntity<Patient> createPatientSql(@RequestBody Patient patient) {
        Patient savedPatient = patientService.createPatient(patient);
        return ResponseEntity.ok(savedPatient);
    }

    @DeleteMapping("/sql/{id}")
    public ResponseEntity<Void> deletePatientByIdSql(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
