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
public class CompanyController {
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
            model.addAttribute("message", "Успешно въведени данни!");
        } catch (ErrorDataException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "index";
    }

    @RequestMapping("/funds")
    public String showFunds(Model model) {
        try {
            model.addAttribute("company", companyService.setFunds());
        } catch (ErrorDataException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "company-funds";
    }
}