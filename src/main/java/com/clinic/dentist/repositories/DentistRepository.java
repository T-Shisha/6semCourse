package com.clinic.dentist.repositories;

import com.clinic.dentist.models.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DentistRepository extends JpaRepository<Dentist, Long> {
}
