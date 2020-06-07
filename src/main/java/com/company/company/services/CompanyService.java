package com.company.company.services;
import com.company.company.entities.Company;
import com.company.company.entities.CompanyFunds;
import com.company.company.exceptions.ErrorDataException;
import com.company.company.repositories.CompanyFundsRepository;
import com.company.company.repositories.CompanyRepository;
import com.company.company.repositories.DetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
   CompanyFundsRepository companyFundsRepository;

    @Autowired
    private DetailRepository detailRepository;

    public void addCompany(Company company) throws ErrorDataException {
        this.checkCompany(company);
        if(companyRepository.count() == 1) {
            Company oldCompany = companyRepository.findAll().get(0);

            oldCompany.setName(company.getName());
            oldCompany.setAddress(company.getAddress());
            oldCompany.setBulstat(company.getBulstat());
            oldCompany.setOwner(company.getOwner());

            companyRepository.save(oldCompany);
        }
        else {
            companyRepository.save(company);
        }
    }

    public CompanyFunds setFunds() {
        if(companyRepository.count() == 0) {
            return null;
        }

        CompanyFunds companyFunds = new CompanyFunds();

        companyFunds.setProfit(detailRepository.findAll().stream().mapToDouble(detail -> detail.getSalePrice()).sum());
        companyFunds.setLoss(detailRepository.findAll().stream().mapToDouble(detail -> detail.getProductionPrice()).sum());
        companyFunds.setIncome(companyFunds.getProfit() - companyFunds.getLoss());
        companyFunds.setNetIncome(companyFunds.getIncome() - (companyFunds.getIncome() * 0.2));

        companyFundsRepository.save(companyFunds);

        return companyFunds;
    }

    public void checkCompany(Company company) throws ErrorDataException {
        if (company.getName().isBlank() || company.getOwner().isBlank() ||
                company.getBulstat().isBlank() ||
                company.getAddress().isBlank()) {
            throw new ErrorDataException("Моля, попълнете всички полета!");
        }

        if (!company.getBulstat().matches("[0-9]+")) {
            throw new ErrorDataException("Моля, въведете валиден БУЛСТАТ съдържащ само цифри!");
        }
    }
}
