package com.practice.hoteManagement.service.impl;

import com.practice.hoteManagement.entity.Appointment;
import com.practice.hoteManagement.entity.Patient;
import com.practice.hoteManagement.repository.AppointmentRepository;
import com.practice.hoteManagement.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    public void deletePatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));

        patientRepository.delete(patient);  // triggers cascade REMOVE → deletes appointments also
    }

    @Transactional
    public void removeParticularAppointment(Long patientId, Long appointmentId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + appointmentId));

        // Ensure the appointment actually belongs to the patient
        if (appointment.getPatient().getId()!=patientId) {
            throw new RuntimeException("This appointment does not belong to the provided patient");
        }

        // Remove the appointment from the list (triggers orphanRemoval)
        patient.getAppointments().remove(appointment);

        // Break the relation on owning side (consistency)
        appointment.setPatient(null);

        // No need to call save() — @Transactional + orphanRemoval handle deletion but just of the shake
        // of understanding will be doing the db operation
        patientRepository.save(patient);
    }
}
