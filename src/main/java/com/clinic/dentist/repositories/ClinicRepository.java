package com.clinic.dentist.repositories;

import com.clinic.dentist.models.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  ClinicRepository extends JpaRepository<Clinic, Long> {

}
