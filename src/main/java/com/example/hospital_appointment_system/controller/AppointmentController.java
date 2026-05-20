package com.example.hospital_appointment_system.controller;

import com.example.hospital_appointment_system.dto.AppointmentRequestDTO;
import com.example.hospital_appointment_system.dto.AppointmentResponseDTO;
import com.example.hospital_appointment_system.payload.ApiResponse;
import com.example.hospital_appointment_system.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final AppointmentService appointmentService;

    // ========== CRUD OPERATIONS ==========

    // Book a new appointment - POST /api/appointments
    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponseDTO>> bookAppointment(
            @Valid @RequestBody AppointmentRequestDTO requestDTO) {

        AppointmentResponseDTO createdAppointment = appointmentService.bookAppointment(requestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created("Appointment booked successfully", createdAppointment));
    }

    // Get all appointments - GET /api/appointments
    @GetMapping
    public ResponseEntity<ApiResponse<List<AppointmentResponseDTO>>> getAllAppointments() {

        List<AppointmentResponseDTO> appointments = appointmentService.getAllAppointments();

        return ResponseEntity.ok(
                ApiResponse.success("Appointments retrieved successfully", appointments)
        );
    }

    // Get appointment by ID - GET /api/appointments/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponseDTO>> getAppointmentById(@PathVariable Long id) {

        AppointmentResponseDTO appointment = appointmentService.getAppointmentById(id);

        return ResponseEntity.ok(
                ApiResponse.success("Appointment retrieved successfully", appointment)
        );
    }

    // Update appointment - PUT /api/appointments/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponseDTO>> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentRequestDTO requestDTO) {

        AppointmentResponseDTO updatedAppointment = appointmentService.updateAppointment(id, requestDTO);

        return ResponseEntity.ok(
                ApiResponse.success("Appointment updated successfully", updatedAppointment)
        );
    }

    // Delete appointment - DELETE /api/appointments/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAppointment(@PathVariable Long id) {

        appointmentService.deleteAppointment(id);

        return ResponseEntity.ok(
                ApiResponse.success("Appointment deleted successfully")
        );
    }

    // ========== FILTER METHODS (BONUS) ==========

    // Filter by Doctor - GET /api/appointments/doctor/{doctorId}
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDTO>>> getAppointmentsByDoctor(
            @PathVariable Long doctorId) {

        List<AppointmentResponseDTO> appointments = appointmentService.getAppointmentsByDoctorId(doctorId);

        return ResponseEntity.ok(
                ApiResponse.success("Appointments retrieved for doctor ID: " + doctorId, appointments)
        );
    }

    // Filter by Date - GET /api/appointments/date/{date}
    @GetMapping("/date/{date}")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDTO>>> getAppointmentsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<AppointmentResponseDTO> appointments = appointmentService.getAppointmentsByDate(date);

        return ResponseEntity.ok(
                ApiResponse.success("Appointments retrieved for date: " + date, appointments)
        );
    }

    // Filter by Doctor AND Date (Bonus) - GET /api/appointments/doctor/{doctorId}/date/{date}
    @GetMapping("/doctor/{doctorId}/date/{date}")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDTO>>> getAppointmentsByDoctorAndDate(
            @PathVariable Long doctorId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<AppointmentResponseDTO> appointments = appointmentService.getAppointmentsByDoctorAndDate(doctorId, date);

        return ResponseEntity.ok(
                ApiResponse.success("Appointments retrieved for doctor ID: " + doctorId + " on date: " + date, appointments)
        );
    }

    // ========== EXTRA: Update Appointment Status ==========

    // Update appointment status - PATCH /api/appointments/{id}/status
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<AppointmentResponseDTO>> updateAppointmentStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        // Note: You may need to add this method to your service
        AppointmentResponseDTO updatedAppointment = appointmentService.updateAppointmentStatus(id, status);

        return ResponseEntity.ok(
                ApiResponse.success("Appointment status updated to: " + status, updatedAppointment)
        );
    }
}