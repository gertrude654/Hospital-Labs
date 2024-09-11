package com.hospital.HospitalMIS.radix;

import com.hospital.HospitalMIS.model.Doctor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//@Repository("doctorRedisRepo")
public interface DoctorRedisRepository extends CrudRepository<DoctorRedis, Long> {

}
