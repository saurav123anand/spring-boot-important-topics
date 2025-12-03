package com.practice.hoteManagement.service.impl;

import com.practice.hoteManagement.entity.Insurance;
import com.practice.hoteManagement.entity.Patient;
import com.practice.hoteManagement.repository.InsuranceRepository;
import com.practice.hoteManagement.repository.PatientRepository;
import com.practice.hoteManagement.service.InsuranceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceServiceImpl implements InsuranceService {
    private final PatientRepository patientRepository;
    private final InsuranceRepository insuranceRepository;

    @Transactional
    public Patient assignInsuranceToPatient(Insurance insurance,Long patientId){
        Patient patient = patientRepository.findById(patientId).
                orElseThrow(() -> new RuntimeException("patient not found with patientId: " + patientId));
        patient.setInsurance(insurance);
        insurance.setPatient(patient); // Bi-Directional consistency is maintained
        patientRepository.save(patient); // Here on top of method wwe are using @Transactional so we
        // don't even need to write this save line, it will be done automatically but I'm just saving
        // manually here, but it's not required
        return patient;
    }

    @Transactional
    public Patient dissociateInsuranceFromPatient(Long patientId){
         Patient patient = patientRepository.findById(patientId).
                orElseThrow(() -> new RuntimeException("patient not found with patientId: " + patientId));
        patient.setInsurance(null);
        patientRepository.save(patient);
        return patient;
    }
}
