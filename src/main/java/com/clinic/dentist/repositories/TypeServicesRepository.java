package com.clinic.dentist.repositories;

import com.clinic.dentist.models.TypeServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeServicesRepository extends JpaRepository<TypeServices, Long> {

}
