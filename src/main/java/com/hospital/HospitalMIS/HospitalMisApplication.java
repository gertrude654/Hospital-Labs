package com.hospital.HospitalMIS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HospitalMisApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalMisApplication.class, args);
	}

}
