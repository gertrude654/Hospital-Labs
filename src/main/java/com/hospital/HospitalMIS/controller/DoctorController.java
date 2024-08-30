package com.hospital.HospitalMIS.controller;

import com.hospital.HospitalMIS.radix.DoctorRedis;
import com.hospital.HospitalMIS.radix.DoctorRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorRedisService doctorRedisService;

    // Redis operations for Doctor entity
    @PostMapping("/redis")
    public ResponseEntity<DoctorRedis> createDoctorRedis(@RequestBody DoctorRedis doctor) {
        return ResponseEntity.ok(doctorRedisService.save(doctor));
    }

    @GetMapping("/redis/{id}")
    public ResponseEntity<DoctorRedis> getDoctorRedisById(@PathVariable Long id) {
        return doctorRedisService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/redis/{id}")
    public ResponseEntity<Void> deleteDoctorRedisById(@PathVariable Long id) {
        doctorRedisService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

