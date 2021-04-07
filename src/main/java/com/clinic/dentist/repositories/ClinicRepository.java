package com.clinic.dentist.repositories;

import com.clinic.dentist.models.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    Optional<Clinic> findByAddress(String address);
}
