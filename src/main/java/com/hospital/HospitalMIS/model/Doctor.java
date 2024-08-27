package com.hospital.HospitalMIS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doctor_tbl")
public class Doctor extends Employee{

    @Column(name = "specialty", nullable = false)
    @NotBlank(message = "Specialty is mandatory")
    private String specialty;
}