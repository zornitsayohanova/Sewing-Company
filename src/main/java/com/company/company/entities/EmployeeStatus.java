package com.company.company.entities;

public enum EmployeeStatus {
    EXPERIENCED("Опитен"), INEXPERIENCED("Неопитен");

    private final  String employeeStatus;

    private EmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public String getStatus() {
        return  employeeStatus;
    }
}