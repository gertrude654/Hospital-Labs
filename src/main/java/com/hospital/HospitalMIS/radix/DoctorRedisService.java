package com.hospital.HospitalMIS.radix;

import com.hospital.HospitalMIS.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorRedisService {

    @Autowired
    private DoctorRedisRepository doctorRepository;

    public List<DoctorRedis> findAll() {
        return (List<DoctorRedis>) doctorRepository.findAll();
    }

    public Optional<DoctorRedis> findById(Long id) {
        return doctorRepository.findById(id);
    }


    public DoctorRedis save(DoctorRedis doctor) {
        return doctorRepository.save(doctor);
    }

    public void deleteById(Long id) {
        doctorRepository.deleteById(id);
    }
}
