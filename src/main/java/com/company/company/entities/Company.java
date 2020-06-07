package com.company.company.entities;

import java.util.List;
import java.util.Map;
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

public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private String owner;

    @Column
    private String bulstat;

    @Column
    private String address;

    @OneToOne
    private CompanyFunds funds;

    @ElementCollection
    private Map<String, Integer> allDetails;

    @OneToMany
    private List<Employee> allEmployees;

    @OneToOne
    private EmployeeStatusCharacteristics employeesCharacteristics;
}
