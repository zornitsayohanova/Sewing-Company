package com.company.company.services;
import com.company.company.entities.*;
import com.company.company.exceptions.ErrorDataException;
import com.company.company.repositories.CharacteristicsRepository;
import com.company.company.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CharacteristicsRepository characteristicsRepository;

    public void addEmployee(Employee employee) throws ErrorDataException {
        this.checkEmployee(employee);

        employeeRepository.save(employee);
    }

    public List<Employee> showEmployees() {
        return employeeRepository.findAll();
    }

    public void setCharacteristics(EmployeeStatusCharacteristics characteristics) throws ErrorDataException {
        this.checkCharacteristics(characteristics);

        EmployeeStatus employeeStatus = characteristics.getEmployeeStatus();

        if (characteristicsRepository.existsByEmployeeStatus(employeeStatus)) {

            EmployeeStatusCharacteristics oldCharacteristics = characteristicsRepository.findByEmployeeStatus(employeeStatus);

            oldCharacteristics.setEmployeeStatus(characteristics.getEmployeeStatus());
            oldCharacteristics.setStatusSalary(characteristics.getStatusSalary());
            oldCharacteristics.setMaxDetails(characteristics.getMaxDetails());

            characteristicsRepository.save(oldCharacteristics);
        } else {
            characteristicsRepository.save(characteristics);
        }
    }

    public void checkEmployee(Employee employee) throws ErrorDataException {
        if(characteristicsRepository.count() == 0)
            throw new ErrorDataException("Моля, уверете се, че сте въвели информация за характеристиката на служителите!");

        if(employeeRepository.findById(employee.getId()).isPresent()) {
            throw new ErrorDataException("Този служител вече съществува!");
        }

        if(employee.getName().isBlank()){
            throw new ErrorDataException("Моля, въведете валидно име!");
        }
    }

    public Map<DetailType, Integer> showEmployeeCreatedDetails(long id) {
        return employeeRepository.findById(id).get().getCreatedDetails();
    }

    public void checkCharacteristics(EmployeeStatusCharacteristics characteristics) throws ErrorDataException {
        if(characteristics.getMaxDetails() <= 0 || characteristics.getStatusSalary() <= 0)
            throw new ErrorDataException("Моля, въведете валидни стойности!");
    }
}
