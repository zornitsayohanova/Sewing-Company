package com.company.company.repositories;

import com.company.company.entities.CompanyFunds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyFundsRepository extends JpaRepository<CompanyFunds, Long> {}
