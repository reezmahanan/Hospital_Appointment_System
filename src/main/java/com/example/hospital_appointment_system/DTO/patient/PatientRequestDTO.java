package com.example.hospital_appointment_system.DTO.patient;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PatientRequestDTO {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private LocalDate dateOfBirth;

    private String gender;
}