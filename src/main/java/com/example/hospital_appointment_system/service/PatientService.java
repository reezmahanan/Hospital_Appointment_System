package com.example.hospital_appointment_system.service;

import java.util.List;

import com.example.hospital_appointment_system.DTO.patient.PatientRequestDTO;
import com.example.hospital_appointment_system.DTO.patient.PatientResponseDTO;

public interface PatientService {

    PatientResponseDTO createPatient(PatientRequestDTO requestDTO);

    List<PatientResponseDTO> getAllPatients();

    PatientResponseDTO getPatientById(Long id);

    PatientResponseDTO updatePatient(Long id, PatientRequestDTO requestDTO);

    void deletePatient(Long id);
}