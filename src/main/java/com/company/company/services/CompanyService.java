package com.company.company.services;
import com.company.company.entities.Company;
import com.company.company.entities.CompanyFunds;
import com.company.company.exceptions.ErrorDataException;
import com.company.company.repositories.CompanyFundsRepository;
import com.company.company.repositories.CompanyRepository;
import com.company.company.repositories.DetailTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    CompanyFundsRepository companyFundsRepository;

    @Autowired
    private DetailTypeRepository detailTypeRepository;

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

    public CompanyFunds setFunds() throws ErrorDataException {
        if(companyRepository.count() == 0) {
            throw new ErrorDataException("Моля, въведете предварително необходимите данни за фирмата!");
        }
        CompanyFunds companyFunds = new CompanyFunds();

        companyFunds.setProfit(detailTypeRepository.findProfit());
        companyFunds.setLoss(detailTypeRepository.findLoss());
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
