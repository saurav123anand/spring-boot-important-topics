package com.practice.hoteManagement.service.impl;

import com.practice.hoteManagement.entity.Appointment;
import com.practice.hoteManagement.entity.Doctor;
import com.practice.hoteManagement.entity.Patient;
import com.practice.hoteManagement.repository.AppointmentRepository;
import com.practice.hoteManagement.repository.DoctorRepository;
import com.practice.hoteManagement.repository.PatientRepository;
import com.practice.hoteManagement.service.AppointmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
     private final AppointmentRepository appointmentRepository;
     private final PatientRepository patientRepository;
     private final DoctorRepository doctorRepository;


     @Transactional
     @Override
     public Patient createNewAppointment(Long patientId, Long doctorId, Appointment appointment) {
         Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));
         Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));
         appointment.setPatient(patient);
         appointment.setDoctor(doctor);
         patient.getAppointments().add(appointment); // only for bidirectional mapping and in-memory storage for consistency
         appointmentRepository.save(appointment);
         return patient;
     }

     @Transactional
     public Appointment reassignAppointmentToAnotherDoctor(Long appointmentId, Long doctorId){
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new RuntimeException("Appointment not found"));
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));
        appointment.setDoctor(doctor);
        doctor.getAppointments().add(appointment);
        appointmentRepository.save(appointment);
        return appointment;
     }
}
