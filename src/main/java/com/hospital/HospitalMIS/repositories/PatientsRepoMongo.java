package com.hospital.HospitalMIS.repositories;

import com.hospital.HospitalMIS.mongoEntity.PatientMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientsRepoMongo extends MongoRepository<PatientMongo, String> {
}
