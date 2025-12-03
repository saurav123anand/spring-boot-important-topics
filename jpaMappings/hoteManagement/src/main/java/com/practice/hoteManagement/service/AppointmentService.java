package com.practice.hoteManagement.service;

import com.practice.hoteManagement.entity.Appointment;
import com.practice.hoteManagement.entity.Patient;

public interface AppointmentService {
    Patient createNewAppointment(Long patientId, Long doctorId, Appointment appointment);
}
