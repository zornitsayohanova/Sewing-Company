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
public class CompanyFunds {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JoinColumn(table = "company", referencedColumnName = "id")
    private long id;

    @Column
    private double netIncome;

    @Column
    private double income;

    @Column
    private double profit;

    @Column
    private double loss;
}
