package com.hospital.HospitalMIS.radix;

import com.hospital.HospitalMIS.model.Employee;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("DoctorRedis")
public class DoctorRedis extends Employee {
    @Id
    private Long emNo;
    private String specialization;
}

