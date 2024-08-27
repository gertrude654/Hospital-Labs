package com.hospital.HospitalMIS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dep_id")
    private Long depId;

    @Column(name = "dep_name", nullable = false)
    @NotBlank(message = "Department name is mandatory")
    private String depName;

    @Column(name = "building", nullable = false)
    @NotBlank(message = "Building is mandatory")
    private String building;

    @OneToOne
    @JoinColumn(name = "em_no", referencedColumnName = "em_no")
    private Doctor director; // The director of the department

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ward> wards; // A department has multiple wards
}
