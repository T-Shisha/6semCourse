package com.clinic.dentist.repositories;

import com.clinic.dentist.models.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    Optional<Maintenance> findByName(String name);
}
