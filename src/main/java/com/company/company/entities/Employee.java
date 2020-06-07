package com.company.company.entities;

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

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private String idSequence;

    @Column
    private EmployeeStatus status;

    @ElementCollection
    @MapKeyColumn(table = "detail", name = "name")
    private Map<String, Integer> details;
}