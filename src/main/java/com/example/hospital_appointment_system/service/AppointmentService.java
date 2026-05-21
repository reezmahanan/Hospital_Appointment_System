package com.example.hospital_appointment_system.service;

import com.example.hospital_appointment_system.dto.AppointmentRequestDTO;
import com.example.hospital_appointment_system.dto.AppointmentResponseDTO;
import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {

    // Book a new appointment
    AppointmentResponseDTO bookAppointment(AppointmentRequestDTO requestDTO);

    // Get all appointments
    List<AppointmentResponseDTO> getAllAppointments();

    // Get appointment by ID
    AppointmentResponseDTO getAppointmentById(Long id);

    // Update appointment
    AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO requestDTO);

    // Delete appointment
    void deleteAppointment(Long id);

    // Filter methods
    List<AppointmentResponseDTO> getAppointmentsByDoctorId(Long doctorId);

    List<AppointmentResponseDTO> getAppointmentsByDate(LocalDate date);

    List<AppointmentResponseDTO> getAppointmentsByDoctorAndDate(Long doctorId, LocalDate date);
}
