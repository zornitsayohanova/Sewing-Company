package com.company.company.repositories;

import com.company.company.entities.EmployeeStatusCharacteristics;
import com.company.company.entities.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacteristicsRepository extends JpaRepository<EmployeeStatusCharacteristics, Long> {
    boolean existsByEmployeeStatus(EmployeeStatus employeeStatus);

    EmployeeStatusCharacteristics findByEmployeeStatus(EmployeeStatus employeeStatus);
}