package com.company.company.services;
import com.company.company.entities.*;
import com.company.company.exceptions.ErrorDataException;
import com.company.company.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DetailService {

    @Autowired
    DetailTypeRepository detailTypeRepository;

    @Autowired
    DetailRepository detailRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CharacteristicsRepository characteristicsRepository;

    @Autowired
    CompanyRepository companyRepository;

    public void addDetailType(DetailType detailType) throws ErrorDataException {
        this.checkDetailType(detailType);

        double workTime = detailType.getManufactureTime() * 60;
        double materialsPrice = detailType.getMaterialsPrice();

        EmployeeStatusCharacteristics detailEmployeeStatus = characteristicsRepository
                .findByEmployeeStatus(detailType.getDetailEmployeeStatus());
        double detailEmployeeStatusSalary = detailEmployeeStatus.getStatusSalary() / 60;

        detailType.setProductionPrice(materialsPrice + (workTime * detailEmployeeStatusSalary));

        detailTypeRepository.save(detailType);
    }

    public void addNewDetail(Detail detail) throws ErrorDataException {
        this.checkDetail(detail);

        DetailType detailType = detailTypeRepository.findByName(detail.getDetail().getName());
        Employee employee = this.findAppropriateEmployee(detailType);

        this.addDetailToEmployeeList(detailType, employee);
        this.addDetail(detailType, detail, employee);
    }

    public void checkDetail(Detail detail) throws ErrorDataException {
        if (employeeRepository.count() == 0 || characteristicsRepository.count() == 0) {
            throw new ErrorDataException("Моля, уверете се, че сте въвели " +
                    "характеристика на служителите и самите служители!");
        }

        if (detailTypeRepository.findByName(detail.getDetail().getName()) == null) {
            throw new ErrorDataException("Моля, уверете се, че сте въвели предварително вида на детайла " +
                    "и неговата характеристика!");
        }
    }

    public void checkDetailType(DetailType detail) throws ErrorDataException {
        if (employeeRepository.count() == 0 || characteristicsRepository.count() == 0) {
            throw new ErrorDataException("Моля, уверете се, че сте въвели " +
                    "характеристика на служителите и самите служители!");
        }

        if (detail.getSalePrice() <= 0 || detail.getMaterialsPrice() <= 0 || detail.getManufactureTime() <= 0) {
            throw new ErrorDataException("Моля, въведете валидни стойности!");
        }

        if (detailTypeRepository.existsByName(detail.getName())) {
            throw new ErrorDataException("Този детайл вече съществува! Моля, сменете името.");
        }
    }

    public Employee findAppropriateEmployee(DetailType detailType) throws ErrorDataException {
        EmployeeStatus detailEmployeeStatus = detailType.getDetailEmployeeStatus();

        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {
            EmployeeStatus employeeStatus = employee.getStatus();

            EmployeeStatusCharacteristics characteristics = characteristicsRepository
                    .findByEmployeeStatus(employeeStatus);

            int maxDetails = characteristics.getMaxDetails();

            if (employee.getCreatedDetails().values().stream().reduce(0, Integer::sum) < maxDetails &&
                    employeeStatus == detailEmployeeStatus) {
                employeeRepository.save(employee);

                return employee;
            }
        }
        throw new ErrorDataException("Всички работници са с достигнат максимум произведени изделия. " +
                "Ако желаете, моля, въведете нов служител или" +
                " променете максималния брой произвеждани изделия при всяка категория служител.");
    }

    public void addDetail(DetailType detailType, Detail detail, Employee employee) {
        detailType.setAmount(detailType.getAmount() + 1);
        detail.setDetail(detailType);
        detail.setEmployee(employee);

        detailTypeRepository.save(detailType);
        detailRepository.save(detail);
    }

    public void addDetailToEmployeeList(DetailType detailType, Employee employee) {
        if (employee.getCreatedDetails().containsKey(detailType)) {
            int count = employee.getCreatedDetails().get(detailType);
            employee.getCreatedDetails().put(detailType, count + 1);
        } else {
            employee.getCreatedDetails().put(detailType, 1);
        }
    }

    public List<DetailType> showAllDetailTypes() {
        return detailTypeRepository.findAll();
    }
}
