package com.hospital.HospitalMIS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ward")
public class Ward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ward_id")
    private Long wardId;

    @Column(name = "ward_name", nullable = false)
    private String wardName;

    @Column(name = "bed_no", nullable = false)
    private String bedNo;

    @ManyToOne
    @JoinColumn(name = "dep_id", nullable = false)
    private Department department; // The department the ward belongs to

    @ManyToOne
    @JoinColumn(name = "em_no", referencedColumnName = "em_no", nullable = false)
    private Nurse supervisor; // The supervisor of the ward

    @OneToMany(mappedBy = "ward")
    private Set<HospitalStay> hospitalStays; // Relationship with HospitalStay
}
