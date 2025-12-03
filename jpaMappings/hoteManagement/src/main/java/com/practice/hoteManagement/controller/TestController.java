package com.practice.hoteManagement.controller;

import com.practice.hoteManagement.entity.Appointment;
import com.practice.hoteManagement.entity.Insurance;
import com.practice.hoteManagement.entity.Patient;
import com.practice.hoteManagement.service.AppointmentService;
import com.practice.hoteManagement.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final InsuranceService insuranceService;
    private final AppointmentService appointmentService;

    @PutMapping("/assign-insurance-to-patient")
    public String assignInsuranceToPatient(@RequestBody Insurance insurance, @RequestParam Long projectId){
        return "Assigned Insurance to Patient successfully";
    }

    @PostMapping("/create-new-appointment")
    public Patient createNewAppointment(@RequestParam Long patientId, @RequestParam Long doctorId, @RequestBody Appointment appointment){
        return appointmentService.createNewAppointment(patientId, doctorId, appointment);
    }

    @DeleteMapping("/dissociate-insurance-from-patient")
    public Patient dissociateInsuranceFromPatient(@RequestParam Long patientId){
        return insuranceService.dissociateInsuranceFromPatient(patientId);
    }
}
