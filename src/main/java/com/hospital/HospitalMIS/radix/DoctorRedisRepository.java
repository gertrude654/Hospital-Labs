package com.hospital.HospitalMIS.radix;

import com.hospital.HospitalMIS.model.Doctor;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRedisRepository extends CrudRepository<DoctorRedis, Long> {

}
