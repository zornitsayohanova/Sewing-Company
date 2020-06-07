package com.company.company.controllers;

import com.company.company.entities.Company;
import com.company.company.exceptions.ErrorDataException;
import com.company.company.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Autowired
    private CompanyService companyService;

    @RequestMapping("/")
    public String createCompany(Model model) {
        model.addAttribute("company", new Company());

        return "index";
    }

    @PostMapping("/")
    public String addCompany(Model model, @ModelAttribute Company company) {
        try {
            companyService.addCompany(company);
            model.addAttribute("success", "Успешно въведени данни!");
        } catch (ErrorDataException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "index";
    }

    @RequestMapping("/funds")
    public String showFunds(Model model) {
        model.addAttribute("companyFunds", companyService.setFunds());

        return "company-funds";
    }

    @PostMapping("/funds")
    public String showFunds() {

       return "company-funds";
    }
}