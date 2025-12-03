package com.practice.hoteManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String specialization;
    private String email;
    @ManyToMany(mappedBy = "doctors")
    private Set<Department> departments=new HashSet<>();
    @OneToMany(mappedBy = "doctor")
    private Set<Appointment> appointments=new HashSet<>();
}
