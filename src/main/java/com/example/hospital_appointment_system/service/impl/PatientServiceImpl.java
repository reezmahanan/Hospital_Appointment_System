package com.example.hospital_appointment_system.service.impl;

import com.example.hospital_appointment_system.DTO.patient.PatientRequestDTO;
import com.example.hospital_appointment_system.DTO.patient.PatientResponseDTO;
import com.example.hospital_appointment_system.entity.Patient;
import com.example.hospital_appointment_system.exception.ResourceNotFoundException;
import com.example.hospital_appointment_system.repository.PatientRepository;
import com.example.hospital_appointment_system.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public PatientResponseDTO createPatient(PatientRequestDTO requestDTO) {

        Patient patient = new Patient();

        patient.setFirstName(requestDTO.getFirstName());
        patient.setLastName(requestDTO.getLastName());
        patient.setEmail(requestDTO.getEmail());
        patient.setPhoneNumber(requestDTO.getPhoneNumber());
        patient.setDateOfBirth(requestDTO.getDateOfBirth());
        patient.setGender(requestDTO.getGender());

        Patient savedPatient = patientRepository.save(patient);

        return mapToDTO(savedPatient);
    }

    @Override
    public List<PatientResponseDTO> getAllPatients() {

        return patientRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public PatientResponseDTO getPatientById(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + id));

        return mapToDTO(patient);
    }

    @Override
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO requestDTO) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + id));

        patient.setFirstName(requestDTO.getFirstName());
        patient.setLastName(requestDTO.getLastName());
        patient.setEmail(requestDTO.getEmail());
        patient.setPhoneNumber(requestDTO.getPhoneNumber());
        patient.setDateOfBirth(requestDTO.getDateOfBirth());
        patient.setGender(requestDTO.getGender());

        Patient updatedPatient = patientRepository.save(patient);

        return mapToDTO(updatedPatient);
    }

    @Override
    public void deletePatient(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + id));

        patientRepository.delete(patient);
    }

    private PatientResponseDTO mapToDTO(Patient patient) {

        return PatientResponseDTO.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .email(patient.getEmail())
                .phoneNumber(patient.getPhoneNumber())
                .dateOfBirth(patient.getDateOfBirth())
                .gender(patient.getGender())
                .build();
    }
}