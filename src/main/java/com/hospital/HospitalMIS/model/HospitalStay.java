package com.hospital.HospitalMIS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HospitalStay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_no", referencedColumnName = "patient_no")
    private Patient patient; // Patient associated with the stay

    @ManyToOne
    @JoinColumn(name = "ward_id", referencedColumnName = "ward_id"
    )
    private Ward ward; // Ward associated with the stay

    private String diagnosis;

    @Column(name = "bed_no", nullable = false)
    private String bedNo;
}
