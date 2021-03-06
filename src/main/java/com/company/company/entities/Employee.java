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
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private EmployeeStatus status;

    @ElementCollection
    @MapKeyColumn(table = "detail_type", name = "name")
    Map<DetailType, Integer> createdDetails;
}