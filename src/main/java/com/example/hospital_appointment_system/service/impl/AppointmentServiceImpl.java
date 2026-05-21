package com.example.hospital_appointment_system.service;

import com.example.hospital_appointment_system.dto.AppointmentRequestDTO;
import com.example.hospital_appointment_system.dto.AppointmentResponseDTO;
import com.example.hospital_appointment_system.entity.Appointment;
import com.example.hospital_appointment_system.entity.AppointmentStatus;
import com.example.hospital_appointment_system.entity.Doctor;
import com.example.hospital_appointment_system.entity.Patient;
import com.example.hospital_appointment_system.exception.AppointmentConflictException;
import com.example.hospital_appointment_system.exception.ResourceNotFoundException;
import com.example.hospital_appointment_system.repository.AppointmentRepository;
import com.example.hospital_appointment_system.repository.DoctorRepository;
import com.example.hospital_appointment_system.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public AppointmentResponseDTO bookAppointment(AppointmentRequestDTO requestDTO) {

        // 1. Check if patient exists
        Patient patient = patientRepository.findById(requestDTO.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with id: " + requestDTO.getPatientId()));

        // 2. Check if doctor exists
        Doctor doctor = doctorRepository.findById(requestDTO.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Doctor not found with id: " + requestDTO.getDoctorId()));

        // 3. Validate startTime is before endTime
        if (requestDTO.getStartTime().isAfter(requestDTO.getEndTime()) ||
                requestDTO.getStartTime().equals(requestDTO.getEndTime())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        // 4. Check for overlapping appointments (CORE BUSINESS LOGIC)
        checkForOverlap(requestDTO.getDoctorId(),
                requestDTO.getAppointmentDate(),
                requestDTO.getStartTime(),
                requestDTO.getEndTime());

        // 5. Create and save appointment
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(requestDTO.getAppointmentDate());
        appointment.setStartTime(requestDTO.getStartTime());
        appointment.setEndTime(requestDTO.getEndTime());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setNotes(requestDTO.getNotes());

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return convertToResponseDTO(savedAppointment);
    }


    private void checkForOverlap(Long doctorId, LocalDate date, LocalTime newStart, LocalTime newEnd) {

        List<Appointment> existingAppointments = appointmentRepository
                .findByDoctorIdAndAppointmentDate(doctorId, date);

        for (Appointment existing : existingAppointments) {
            LocalTime existingStart = existing.getStartTime();
            LocalTime existingEnd = existing.getEndTime();

            // Overlap condition: newStart < existingEnd AND newEnd > existingStart
            if (newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart)) {
                throw new AppointmentConflictException(
                        String.format("Appointment overlaps with existing appointment: %s to %s",
                                existingStart, existingEnd));
            }
        }
    }

    @Override
    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentResponseDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment not found with id: " + id));
        return convertToResponseDTO(appointment);
    }

    @Override
    public AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO requestDTO) {

        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment not found with id: " + id));

        Patient patient = patientRepository.findById(requestDTO.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with id: " + requestDTO.getPatientId()));

        Doctor doctor = doctorRepository.findById(requestDTO.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Doctor not found with id: " + requestDTO.getDoctorId()));

        if (requestDTO.getStartTime().isAfter(requestDTO.getEndTime()) ||
                requestDTO.getStartTime().equals(requestDTO.getEndTime())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        // Check overlap excluding current appointment
        List<Appointment> existingAppointments = appointmentRepository
                .findByDoctorIdAndAppointmentDate(requestDTO.getDoctorId(),
                        requestDTO.getAppointmentDate());

        for (Appointment existing : existingAppointments) {
            if (!existing.getId().equals(id)) {  // Exclude current appointment
                if (requestDTO.getStartTime().isBefore(existing.getEndTime()) &&
                        requestDTO.getEndTime().isAfter(existing.getStartTime())) {
                    throw new AppointmentConflictException(
                            "Updated appointment overlaps with existing appointment");
                }
            }
        }

        existingAppointment.setPatient(patient);
        existingAppointment.setDoctor(doctor);
        existingAppointment.setAppointmentDate(requestDTO.getAppointmentDate());
        existingAppointment.setStartTime(requestDTO.getStartTime());
        existingAppointment.setEndTime(requestDTO.getEndTime());
        existingAppointment.setNotes(requestDTO.getNotes());

        Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
        return convertToResponseDTO(updatedAppointment);
    }

    @Override
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment not found with id: " + id);
        }
        appointmentRepository.deleteById(id)

    @Override
    public List<AppointmentResponseDTO> getAppointmentsByDoctorId(Long doctorId) {
        // Check if doctor exists
        doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Doctor not found with id: " + doctorId));

        return appointmentRepository.findByDoctorId(doctorId)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentsByDate(LocalDate date) {
        return appointmentRepository.findByAppointmentDate(date)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentsByDoctorAndDate(Long doctorId, LocalDate date) {
        // Check if doctor exists
        doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Doctor not found with id: " + doctorId));

        return appointmentRepository.findByDoctorIdAndAppointmentDate(doctorId, date)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // ========== HELPER METHOD ==========

    private AppointmentResponseDTO convertToResponseDTO(Appointment appointment) {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setId(appointment.getId());
        dto.setPatientId(appointment.getPatient().getId());
        dto.setPatientName(appointment.getPatient().getFirstName() + " " +
                appointment.getPatient().getLastName());
        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setDoctorName(appointment.getDoctor().getFirstName() + " " +
                appointment.getDoctor().getLastName());
        dto.setDoctorSpecialization(appointment.getDoctor().getSpecialization());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setStartTime(appointment.getStartTime());
        dto.setEndTime(appointment.getEndTime());
        dto.setStatus(appointment.getStatus().toString());
        dto.setNotes(appointment.getNotes());
        return dto;
    }
}
