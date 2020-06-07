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

public class Detail {

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
    private String leatherColor;

    @Column
    private double materialsPrice;

    @Column
    private double workTime;
}

