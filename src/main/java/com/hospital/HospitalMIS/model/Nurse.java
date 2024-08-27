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
@Table(name = "nurse_tbl")
public class Nurse extends Employee{

    @Column(name = "rotation", nullable = false)
    @NotBlank(message = "Rotation is mandatory")
    private String rotation;

    @Column(name = "salary", nullable = false)
    @NotBlank(message = "Salary is mandatory")
    private String salary;
}