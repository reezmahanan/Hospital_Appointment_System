package com.example.hospital_appointment_system.repository;

import com.example.hospital_appointment_system.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Find doctor by email (for duplicate check)
    Optional<Doctor> findByEmail(String email);

    // Check if email already exists
    boolean existsByEmail(String email);

    // Find doctors by specialization (Bonus feature)
    List<Doctor> findBySpecialization(String specialization);

    // Search doctors by name (Bonus feature)
    List<Doctor> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
}