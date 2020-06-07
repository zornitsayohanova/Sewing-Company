package com.company.company.repositories;

import com.company.company.entities.Detail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailRepository extends JpaRepository<Detail, Long> {
    boolean existsByName(String name);

    Detail findByName(String name);
}