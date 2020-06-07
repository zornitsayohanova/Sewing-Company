package com.company.company.services;
import com.company.company.entities.*;
import com.company.company.exceptions.ErrorDataException;
import com.company.company.repositories.CharacteristicsRepository;
import com.company.company.repositories.CompanyRepository;
import com.company.company.repositories.DetailRepository;
import com.company.company.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class DetailService {

    @Autowired
    DetailRepository detailRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CharacteristicsRepository characteristicsRepository;

    @Autowired
    CompanyRepository companyRepository;

    public void addDetail(Detail detail) throws ErrorDataException {
        this.checkDetail(detail);

        double workTime = detail.getWorkTime() * 60;
        double materialsPrice = detail.getMaterialsPrice();
        double statusSalary = this.findEmployeeStatusSalary(detail) / 60;
        this.checkEmployee(statusSalary);

        detail.setProductionPrice(materialsPrice + (workTime * statusSalary));
        this.addToCompany(detail);

        detailRepository.save(detail);
    }

    public double findEmployeeStatusSalary(Detail detail) {
        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {
            EmployeeStatus employeeStatus = employee.getStatus();

            EmployeeStatusCharacteristics characteristics = characteristicsRepository.findByEmployeeStatus(employeeStatus);

            int maxDetails = characteristics.getMaxDetails();
            double statusSalary = characteristics.getStatusSalary();

            if (employee.getDetails().size() < maxDetails) {
                this.addToEmployeeList(employee, detail);

                return statusSalary;
            }
        }
        return -1;
    }

    public void addToEmployeeList(Employee employee, Detail detail) {

        if (!employee.getDetails().containsKey(detail.getName())) {
            employee.getDetails().put(detail.getName(), 1);
        } else {
            int count = employee.getDetails().get(detail.getName());
            employee.getDetails().put(detail.getName(), count + 1);
        }
    }

    public void addToCompany(Detail detail) {
        Company company = companyRepository.findAll().get(0);

        if (!company.getAllDetails().containsKey(detail.getName())) {
            company.getAllDetails().put(detail.getName(), 1);
        } else {
            int count = company.getAllDetails().get(detail.getName());
            company.getAllDetails().put(detail.getName(), count + 1);
        }
    }

    public Map<String, Integer> showDetails() {
        if(companyRepository.count() == 0)
            return null;

        Company company = companyRepository.findAll().get(0);

        return company.getAllDetails();
    }

    public int showDetailsAmount() {
        Map<String, Integer> allDetails = companyRepository.findAll().get(0).getAllDetails();
        int amount = 0;

        for (int value : allDetails.values()) {
            amount += value;
        }
        return amount;
    }

    public void checkDetail(Detail detail) throws ErrorDataException {
        if (companyRepository.count() == 0 || employeeRepository.count() == 0 || characteristicsRepository.count() == 0) {
            throw new ErrorDataException("Моля, уверете се, че сте въвели информация за фирмата, " +
                                         "характеристиката на служителите и самите служители!");
        }

        if (detail.getSalePrice() <= 0 || detail.getMaterialsPrice() <= 0 || detail.getWorkTime() <= 0) {
            throw new ErrorDataException("Моля, въведете валидни стойности!");
        }

        if (detailRepository.existsByName(detail.getName())) {
            if (detailRepository.findByName(detail.getName()).getLeatherColor().equals(detail.getLeatherColor())) {
                throw new ErrorDataException("Този детайл вече съществува! Моля, сменете името или цвета.");
            }
        }
    }

    public void checkEmployee(double statusSalary) throws ErrorDataException {
        if (statusSalary < 0) {
            throw new ErrorDataException("Достигнат максимум за изработка от текущите служители. Моля, въведете нов служител!");
        }
    }
}
