package com.company.company.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DetailType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private double productionPrice;

    @Column
    private double salePrice;

    @Column
    private int amount;

    @Column
    private double materialsPrice;

    @Column
    private double manufactureTime;

    @Column
    private EmployeeStatus detailEmployeeStatus;
}

