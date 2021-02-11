package com.company.company.repositories;

import com.company.company.entities.DetailType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailTypeRepository extends JpaRepository<DetailType, Long> {
    boolean existsByName(String name);

    DetailType findByName(String name);

    @Query("SELECT SUM(m.salePrice) FROM DetailType m")
    double findProfit();

    @Query("SELECT SUM(m.productionPrice) FROM DetailType m")
    double findLoss();
}