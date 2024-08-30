package com.hospital.HospitalMIS.mongoEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "patients")
public class PatientMongo {
    @Id
    private String id;
    private String name;
    private int age;
    private String diagnosis;
}

