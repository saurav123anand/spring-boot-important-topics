package com.practice.hoteManagement.service;

import com.practice.hoteManagement.entity.Insurance;
import com.practice.hoteManagement.entity.Patient;

public interface InsuranceService {
    Patient assignInsuranceToPatient(Insurance insurance, Long patientId);
    Patient dissociateInsuranceFromPatient(Long patientId);
}
