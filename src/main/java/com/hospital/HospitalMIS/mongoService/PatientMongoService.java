package com.hospital.HospitalMIS.mongoService;

import com.hospital.HospitalMIS.mongoEntity.PatientMongo;
import com.hospital.HospitalMIS.repositories.PatientsRepoMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PatientMongoService {

    @Autowired
    private PatientsRepoMongo patientRepository;

    @Transactional(readOnly = true)
    public List<PatientMongo> findAll() {
        return patientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PatientMongo findById(String id) {
        return patientRepository.findById(id).orElse(null);
    }

    @Transactional
    public PatientMongo save(PatientMongo patient) {
        return patientRepository.save(patient);
    }

    @Transactional
    public void deleteById(String id) {
        patientRepository.deleteById(id);
    }
}
