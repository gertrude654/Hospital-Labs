package com.hospital.HospitalMIS.radix;

import com.hospital.HospitalMIS.model.Employee;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("DoctorRedis")
public class DoctorRedis extends Employee {
    private String specialization;

    // Getters and Setters
}

